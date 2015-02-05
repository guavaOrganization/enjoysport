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
	// 土耳其和哈萨克斯坦是横跨亚欧两洲的国家
	// 乌兹别克斯坦、叙利亚、也门、吉尔吉斯斯坦 是亚洲国家
	private final static String ASIA_COUNTRY = "乌兹别克斯坦,中华人民共和国,中国,中国香港,台湾省,中国台湾,中国澳门,阿富汗,约旦,科威特,伊朗,阿拉伯联合酋长国,伊拉克,柬埔寨,缅甸,巴基斯坦,孟加拉,日本,韩国,泰国,马来西亚,印度尼西亚,越南,老挝,印度,斯里兰卡,沙特阿拉伯,尼泊尔,锡金,阿曼,黎巴嫩,以色列,巴林,东帝汶,朝鲜,蒙古,新加坡,菲律宾,文莱";
	private final static String DEVELOPED_COUNTRY = "澳大利亚,比利时,保加利亚,塞浦路斯,捷克共和国,丹麦,爱沙尼亚,芬兰,法国,德国,希腊,匈牙利,爱尔兰,意大利,拉脱维亚,立陶宛,卢森堡,马耳他,荷兰,波兰,葡萄牙,罗马尼亚,斯洛伐克,斯洛文尼亚,西班牙,瑞典,英国,直布罗陀,冰岛,挪威,瑞士,加拿大,美国,澳大利亚,百慕大,以色列,日本,新西兰";
	private final static String EAST_AREA = "北京市,天津市,上海市,山东省,江苏省,浙江省,福建省,广东省,海南省,辽宁省";
	
	@Autowired
	@Qualifier("com.example.lily.dao.impls.EnterpriseDataDAOImpl")
	private IEnterpriseDataDAO enterpriseDataDAO;

	public QueryDBResultHolder queryEnterpriseData(String tableName, String matchColumn, String matchValue) throws Exception {
		return enterpriseDataDAO.queryEnterpriseData(tableName, matchColumn, matchValue);
	}
	
	public List<List<String>> repairListData(List<List<String>> sourceDatas, int startIndex, int endIndex, int repairStartIndex, int columnStartIndex, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns) throws Exception {
		if (null == sourceDatas || sourceDatas.size() <= 1) {
			throw new IllegalArgumentException("经检查，sourceDatas列表无需修复");
		}
		if (startIndex >= endIndex) {
			throw new IllegalArgumentException("经检查，startIndex大于等于endIndex，不允许受理修复工作");
		}
		if (startIndex <= 2) {
			startIndex = 2;
		}
		if (endIndex > sourceDatas.size()) {
			endIndex = sourceDatas.size();
		}
		if(null == matchIndexs || matchIndexs.size() == 0){
			throw new IllegalArgumentException("参数[matchIndex]不能为空");
		}
		if(null == matchColumns || matchColumns.size() == 0){
			throw new IllegalArgumentException("参数[matchColumns]不能为空");
		}
		if (matchIndexs.size() != matchColumns.size()) {
			throw new IllegalArgumentException("参数[matchIndexs]与参数[matchColumns]长度不等");
		}
		List<List<String>> repairedDataList = new ArrayList<List<String>>();
		for (int i = (startIndex - 1); i < endIndex; i++) {
			if(log.isInfoEnabled())
				log.error("开始修复第[" + (i + 1) + "]条数据...");
			List<String> rowData = sourceDatas.get(i);
			
			QueryDBResultHolder queryDBResultHolder = null;
			int matchIndex = -1;
			do {
				matchIndex++;
				queryDBResultHolder = queryEnterpriseData(sourceTableName, matchColumns.get(matchIndex), rowData.get((matchIndexs.get(matchIndex) - 1)).trim());
			} while ((matchIndex + 1 < matchColumns.size())
					&& (queryDBResultHolder == null || queryDBResultHolder.getResultDatas() == null || queryDBResultHolder.getResultDatas().size() == 0));
			
			// 未在制定表中找到数据
			if (null == queryDBResultHolder || queryDBResultHolder.getResultDatas() == null || queryDBResultHolder.getResultDatas().size() == 0) {
				repairedDataList.add(rowData);
				log.error("在MDB中未匹配到数据，结束修复工作..........");
				continue;
			}
			
			List<String> resutRow = queryDBResultHolder.getResultDatas().get(0);
			
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < rowData.size(); j++) {// repairStartIndex = 13
				if (j < (repairStartIndex - 1)) {// 0-11
					list.add(rowData.get(j));
				} else {
					break;
				}
			}
			
			for (int j = (columnStartIndex - 1); j < resutRow.size(); j++) {
				list.add(resutRow.get(j));
			}
			
			repairedDataList.add(list);
			log.error("在MDB中匹配到数据，结束修复工作..........");
		}
		return repairedDataList;
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
				queryDBResultHolder = queryEnterpriseData(sourceTableName, matchColumns.get(matchIndex), sourceData.get(matchIndexs.get(matchIndex) - 1).trim());
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
		boolean need = true;
		if(need){
			// 需要翻译的列
			List<Integer> needToBeTranslatedIndexs = new ArrayList<Integer>();
			needToBeTranslatedIndexs.add(8);
//			needToBeTranslatedIndexs.add(3);
//			needToBeTranslatedIndexs.add(14);
			
			for (int i = 0; i < matchIndexs.size(); i++) {
				int matchIndex = matchIndexs.get(i);
				for (int j = 0; j < needToBeTranslatedIndexs.size(); j++) {
					int needToBeTranslatedIndex = needToBeTranslatedIndexs.get(j);
					if(needToBeTranslatedIndex < matchIndex){
						++matchIndex;
					}
				}
				matchIndexs.set(i, matchIndex);
			}
			
			// 需要翻译的列
			for (int i = 0; null != list && i < list.size(); i++) {
				List<String> data = list.get(i);
				List<String> translateResults = new ArrayList<String>();
				
				// 迭代 需要翻译的列，对字段进行翻译
				for (int j = 0; j < needToBeTranslatedIndexs.size(); j++) {
					// 翻译字段 Start
					int needToBeTranslatedIndex = needToBeTranslatedIndexs.get(j) - 1;
					/**
					if (i == 0 && j == 0) {
						translateResults.add("是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
						continue;
					}
					if (i == 0 && j == 1) {
						translateResults.add("是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
						continue;
					}
					// if (i == 0 && j == 2) {
					*/
					if (i == 0 && j == 0) {
						translateResults.add("是否为我国东部地区(1:东部地区;0:非东部地区)");
						continue;
					}
					
					String tempData = data.get(needToBeTranslatedIndex) == null ? "" : data.get(needToBeTranslatedIndex).trim();
					/**
					if (j == 0) {
						if (ASIA_COUNTRY.indexOf(tempData) >= 0) { // 是否为亚洲国家
							translateResults.add("1");
						} else {
							translateResults.add("0");
						}
						continue;
					} 
					if (j == 1) {
						if (DEVELOPED_COUNTRY.indexOf(tempData) >= 0) { // 是否为高收入国家
							translateResults.add("1");
						} else {
							translateResults.add("0");
						}
						continue;
					} 
					if (j == 2) {
					*/
					if (j == 0) {
						if (EAST_AREA.indexOf(tempData) >= 0) { // 是否为我国东部地区
							translateResults.add("1");
						} else {
							translateResults.add("0");
						}
						continue;
					} 
					// 翻译字段 End
				}
				
				// 添加加到data行中
				for (int j = 0; j < needToBeTranslatedIndexs.size(); j++) {
					data.add((needToBeTranslatedIndexs.get(j) + j), translateResults.get(j));
				}
			}
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
		if(log.isInfoEnabled()){
			log.info("匹配到结果的数据列表~~~~~共有~~~~~~" + matchedResultList.size() + "~~~~~行数据");
			log.info("未匹配到结果的数据列表~~~~~共有~~~~~~" + unmatchResultList.size() + "~~~~~行数据");
		}
		GuavaExcelUtil.writeDataToExcel(matchedResultList, "匹配到结果的数据列表", wb, os);
		if(matchedResultList != null)
			matchedResultList = null;// Help GC
		GuavaExcelUtil.writeDataToExcel(unmatchResultList, "未匹配到结果的数据列表", wb, os);
		if(unmatchResultList != null)
			unmatchResultList = null;// Help GC
		wb.write(os);
		os.close();
	}
}
