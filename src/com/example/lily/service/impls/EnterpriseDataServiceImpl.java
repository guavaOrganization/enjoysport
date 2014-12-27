package com.example.lily.service.impls;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.lily.dao.interfaces.IEnterpriseDataDAO;
import com.example.lily.javabeans.MatchingEnterpriseDataCallable;
import com.example.lily.javabeans.MatchingResultHolder;
import com.example.lily.javabeans.QueryDBResultHolder;
import com.example.lily.service.interfaces.IEnterpriseDataService;
import com.guava.codeautogenerator.core.support.StringUtils;
import com.guava.util.GuavaExcelUtil;

@Service("com.example.lily.service.impls.EnterpriseDataServiceImpl")
public class EnterpriseDataServiceImpl implements IEnterpriseDataService {
	private transient static Log log = LogFactory.getLog(EnterpriseDataServiceImpl.class);
	private final static String SPLIT_TIP = "----左边是Excel的数据----我是分割线----右边是MDB中的数据----";// 分隔提示 
	private final static String UNMATCH_TIP = "未匹配到结果数据的原因";// 未匹配到数据的原因
	@Autowired
	@Qualifier("com.example.lily.dao.impls.EnterpriseDataDAOImpl")
	private IEnterpriseDataDAO enterpriseDataDAO;

	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception {
		return enterpriseDataDAO.queryEnterpriseData(tableName, matchColumn, matchValue);
	}
	
	public MatchingResultHolder matchingEnterpriseData(List<List<String>> sourceDatas, int startIndex, int endIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception {
		if(null == matchIndexs || matchIndexs.size() == 0){
			throw new IllegalArgumentException("参数[matchIndex]不能为空");
		}
		if(null == matchColumns || matchColumns.size() == 0){
			throw new IllegalArgumentException("参数[matchColumns]不能为空");
		}
		if (matchIndexs.size() != matchColumns.size()) {
			throw new IllegalArgumentException("参数[matchIndexs]与参数[matchColumns]长度不等");
		}
		List<String> matchedHead = new ArrayList<String>();// 正确匹配结果的头部
		List<List<String>> matchedResultList = new ArrayList<List<String>>();// 正确匹配的结果列表
		List<String> unmatchHead = new ArrayList<String>();// 未正确匹配结果的头部
		List<List<String>> unmatchResultList = new ArrayList<List<String>>();// 未正确匹配结果列表
		for (int i = startIndex; i < endIndex; i++) {
			if(log.isInfoEnabled())
				log.info("正在处理下标为[" + i + "]数据");
			List<String> sourceData = sourceDatas.get(i); // 获取excel中单行数据
			if (i == 0) { // Excel头行不进行比较
				unmatchHead.addAll(sourceData);
				unmatchHead.add(UNMATCH_TIP);
				continue;
			}
			
			QueryDBResultHolder queryDBResultHolder = null;
			int matchIndex = -1;
			String matchedColumnNameStr = StringUtils.EMPTY; 
			do {
				matchIndex++;
				// 根据Excel中的单行单列数据，匹配源表对应的列
				if (matchIndex + 1 != matchIndexs.size()) {
					matchedColumnNameStr += matchColumns.get(matchIndex) + ",";
				} else {
					matchedColumnNameStr += matchColumns.get(matchIndex);
				}
				queryDBResultHolder = queryEnterpriseData(sourceTableName, matchColumns.get(matchIndex), sourceData.get(matchIndexs.get(matchIndex)).trim());
			} while ((matchIndex + 1 < matchColumns.size())
					&& (queryDBResultHolder == null || queryDBResultHolder.getResultDatas() == null || queryDBResultHolder.getResultDatas().size() == 0));
			
			// 没有匹配到的结果数据
			if (null == queryDBResultHolder || queryDBResultHolder.getResultDatas() == null || queryDBResultHolder.getResultDatas().size() == 0) {
				List<String> tempList = new ArrayList<String>();
				tempList.addAll(sourceData);
				tempList.add("尝试匹配MDB文件中的[" + matchedColumnNameStr + "]，都未能匹配到数据");
				unmatchResultList.add(tempList);// 记录未匹配的行
				continue;
			}
			
			// 匹配到了数据
			List<List<String>> resultDatas = queryDBResultHolder.getResultDatas();// 查询结果数据
			for (List<String> resultData : resultDatas) {
				List<String> tempSourceData = new ArrayList<String>();
				tempSourceData.addAll(sourceData);
				tempSourceData.add(SPLIT_TIP);
				tempSourceData.addAll(resultData);
				matchedResultList.add(tempSourceData);
			}
			if (matchedHead.size() == 0) {
				matchedHead.addAll(sourceDatas.get(0)); // 匹配结果数据对应的Excel头部
				matchedHead.add(SPLIT_TIP);// 分隔符
				matchedHead.addAll(queryDBResultHolder.getResultMetaDatas());// 匹配结果数据对应的MDB对应的列头部
			}
		}
		MatchingResultHolder resultHolder = new MatchingResultHolder(matchedHead, matchedResultList, unmatchHead, unmatchResultList);
		return resultHolder;
	}
	
	public MatchingResultHolder matchingEnterpriseDataAndCreateResultToHolder(String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception {
		MatchingResultHolder matchingResultHolder = null;
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(sourceAbsoluteFilePath);// 从Excel中加载数据
		if (null == list || list.size() == 0) {
			if (log.isInfoEnabled())
				log.info("sourceAbsoluteFilePath中没有数据,无需匹配");
			return null;
		}
		if (isConcurrent) {// 并行匹配
			if (nThreads <= 0 || nThreads > 10) { // 默认是个线程
				nThreads = 10;
			}
			List<String> matchedHead = new ArrayList<String>();// 正确匹配结果的头部
			List<List<String>> matchedResultList = new ArrayList<List<String>>();// 正确匹配的结果列表
			List<String> unmatchHead = new ArrayList<String>();// 未正确匹配结果的头部
			List<List<String>> unmatchResultList = new ArrayList<List<String>>();// 未正确匹配结果列表
			
			ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
			CompletionService<MatchingResultHolder> completionService = new ExecutorCompletionService<MatchingResultHolder>(executorService);
			int count = list.size();
			List<Future<MatchingResultHolder>> resultHolders = new ArrayList<Future<MatchingResultHolder>>();
			for (int index = 0; index < nThreads; index++) {
				int start = index * (count / nThreads);// 开始下标
				int end = 0;
				if (index == nThreads - 1) {
					end = (index + 1) * (count / nThreads) + count % nThreads;
				} else {
					end = (index + 1) * (count / nThreads);
				}
				if(log.isInfoEnabled()){
					log.info("比对的开始下标为："+start + ", 结束下标下标为：" + end);
				}
				resultHolders.add(completionService.submit(new MatchingEnterpriseDataCallable(list, start, end, matchIndexs, sourceTableName, matchColumns, this)));
			}
			for (Future<MatchingResultHolder> future : resultHolders) {
				MatchingResultHolder holder = future.get();
				if(null == holder)
					continue;
				if (null != holder.getMatchedHead() && matchedHead.size() == 0) {
					matchedHead.addAll(holder.getMatchedHead());
				}
				if (null != holder.getUnmatchHead() && unmatchHead.size() == 0) {
					unmatchHead.addAll(holder.getUnmatchHead());
				}
				if (null != holder.getMatchedResultList() && holder.getMatchedResultList().size() > 0) {
					matchedResultList.addAll(holder.getMatchedResultList());
				}
				if (null != holder.getUnmatchResultList() && holder.getUnmatchResultList().size() > 0) {
					unmatchResultList.addAll(holder.getUnmatchResultList());
				}
			}
			executorService.shutdownNow();
			matchingResultHolder = new MatchingResultHolder(matchedHead, matchedResultList, unmatchHead, unmatchResultList);
		} else {// 单线程匹配
			matchingResultHolder = matchingEnterpriseData(list, 0, list.size(), matchIndexs, sourceTableName, matchColumns);
		}
		if (null != list)
			list = null;// help GC
		return matchingResultHolder;
	}
	
	public void matchingEnterpriseDataAndreateResultFileToExcel(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception {
		MatchingResultHolder holder = matchingEnterpriseDataAndCreateResultToHolder(sourceAbsoluteFilePath, matchIndexs, sourceTableName, matchColumns, isConcurrent, nThreads);
		List<List<String>> matchedResultList = holder.getMatchedResultList();
		List<List<String>> unmatchResultList = holder.getUnmatchResultList();
		if(null == matchedResultList || null == unmatchResultList)
			return;
		if (null != holder.getMatchedHead())
			matchedResultList.add(0, holder.getMatchedHead());
		if (null != holder.getUnmatchHead())
			unmatchResultList.add(0, holder.getUnmatchHead());
		OutputStream os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		GuavaExcelUtil.writeDataToExcel(matchedResultList, "匹配到结果的数据列表", wb, os);
		GuavaExcelUtil.writeDataToExcel(unmatchResultList, "未匹配到结果的数据列表", wb, os);
		wb.write(os);
		os.close();
	}
}
