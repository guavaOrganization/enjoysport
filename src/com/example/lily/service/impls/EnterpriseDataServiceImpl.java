package com.example.lily.service.impls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
	public final static String ASIA_COUNTRY = "乌兹别克斯坦,中华人民共和国,中国,中国香港,台湾省,中国台湾,中国澳门,阿富汗,约旦,科威特,伊朗,阿拉伯联合酋长国,伊拉克,柬埔寨,缅甸,巴基斯坦,孟加拉,日本,韩国,泰国,马来西亚,印度尼西亚,越南,老挝,印度,斯里兰卡,沙特阿拉伯,尼泊尔,锡金,阿曼,黎巴嫩,以色列,巴林,东帝汶,朝鲜,蒙古,新加坡,菲律宾,文莱";
	public final static String DEVELOPED_COUNTRY = "澳大利亚,比利时,保加利亚,塞浦路斯,捷克共和国,丹麦,爱沙尼亚,芬兰,法国,德国,希腊,匈牙利,爱尔兰,意大利,拉脱维亚,立陶宛,卢森堡,马耳他,荷兰,波兰,葡萄牙,罗马尼亚,斯洛伐克,斯洛文尼亚,西班牙,瑞典,英国,直布罗陀,冰岛,挪威,瑞士,加拿大,美国,澳大利亚,百慕大,以色列,日本,新西兰";
	public final static String EAST_AREA = "北京市,天津市,上海市,山东省,江苏省,浙江省,福建省,广东省,海南省,辽宁省";
	
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
	
	public List<List<String>> matchingEnterpriseData(List<List<String>> excelDatas, int excelMatchIndex, String year, String dbMatchColName) throws Exception {
		if(excelDatas == null || excelDatas.size() <= 1)
			return null;
		String tableName = "t_enterprise_data_" + year;
		String splitTipe = "-----------------左边是Excel数据，右边是MDB数据-----------------";
		List<String> resultMetaDatas = null;
		List<List<String>> resultList = new ArrayList<List<String>>();
		Random random = new Random();
		for (int i = 1; i < excelDatas.size(); i++) {
			String excelMatchValue = excelDatas.get(i).get(excelMatchIndex);
			if(StringUtils.isBlank(excelMatchValue))
				continue;
			QueryDBResultHolder dbResultHolder = enterpriseDataDAO.queryEnterpriseData(tableName, dbMatchColName, excelMatchValue);
			if (null == dbResultHolder || dbResultHolder.getResultDatas() == null || dbResultHolder.getResultDatas().size() == 0) {
				if (excelMatchValue.length() >= 3) {
					dbResultHolder = enterpriseDataDAO.queryLikeEnterpriseData(tableName, dbMatchColName, excelMatchValue.substring(0, 3) + "%");
				}
			}
			if (null != dbResultHolder && dbResultHolder.getResultDatas() != null && dbResultHolder.getResultDatas().size() > 0) {
				if(null == resultMetaDatas)
					resultMetaDatas = dbResultHolder.getResultMetaDatas();
				List<List<String>> resultDatas = dbResultHolder.getResultDatas();
				int randomIndex = random.nextInt(resultDatas.size());
				if (randomIndex >= resultDatas.size())
					randomIndex = resultDatas.size() - 1;
				List<String> resultRow = new ArrayList<String>();
				resultRow.addAll(excelDatas.get(i));
				resultRow.add(splitTipe);
				resultRow.addAll(resultDatas.get(randomIndex));
				resultList.add(resultRow);
			}
		}
		List<String> head = new ArrayList<String>();
		head.addAll(excelDatas.get(0));
		head.add(splitTipe);
		head.addAll(resultMetaDatas);
		resultList.add(0, head);
		return resultList;
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
	
	public void lily_20150529(String targetAbsoluteFilePath, String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads, int sheetNum) throws Exception {
		Map<String,List<List<String>>> sheetMap = GuavaExcelUtil.loadExcelDataToMap(sourceAbsoluteFilePath, sheetNum);
		Iterator<String> sheetNameIte = sheetMap.keySet().iterator();
		while (sheetNameIte.hasNext()) {
			MatchingResultHolder matchingResultHolder = null;
			String sheetName = sheetNameIte.next();
			if ("t_enterprise_data_2001".equals(sourceTableName) && !"投资单个国家的".equals(sheetName))
				continue;
			System.out.println("正在处理{" + sheetName + "}页的数据");
			List<List<String>> list = sheetMap.get(sheetName);
			if (null != list) {
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
				
				List<List<String>> matchedResultList = matchingResultHolder.getMatchedResultList();
				List<List<String>> unmatchResultList = matchingResultHolder.getUnmatchResultList();
				if(null == matchedResultList || null == unmatchResultList)
					return;
				if (null != matchingResultHolder.getMatchedHead())
					matchedResultList.add(0, matchingResultHolder.getMatchedHead());
				if (null != matchingResultHolder.getUnmatchHead())
					unmatchResultList.add(0, matchingResultHolder.getUnmatchHead());
				OutputStream os = new FileOutputStream(targetAbsoluteFilePath + "_" + sheetName + ".xlsx");
				XSSFWorkbook wb = new XSSFWorkbook();
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
	}
	
	public MatchingResultHolder matchingEnterpriseDataAndCreateResultToHolder(String sourceAbsoluteFilePath, List<Integer> matchIndexs, String sourceTableName, List<String> matchColumns, boolean isConcurrent, int nThreads) throws Exception {
		MatchingResultHolder matchingResultHolder = null;
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(sourceAbsoluteFilePath);// 从Excel中加载数据
		if (null == list || list.size() == 0) {
			if (log.isInfoEnabled())
				log.info("sourceAbsoluteFilePath中没有数据,无需匹配");
			return null;
		}
		boolean need = false;
		if (need) {
			// 需要翻译的列
			List<Integer> needToBeTranslatedIndexs = new ArrayList<Integer>();
			needToBeTranslatedIndexs.add(3);
			needToBeTranslatedIndexs.add(3);
			needToBeTranslatedIndexs.add(14);
			
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
					if (i == 0 && j == 0) {
						translateResults.add("是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
						continue;
					}
					if (i == 0 && j == 1) {
						translateResults.add("是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
						continue;
					}
					if (i == 0 && j == 2) {
						translateResults.add("是否为我国东部地区(1:东部地区;0:非东部地区)");
						continue;
					}
					
					String tempData = data.get(needToBeTranslatedIndex) == null ? "" : data.get(needToBeTranslatedIndex).trim();
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
						if (EAST_AREA.indexOf(tempData) >= 0) { // 是否为我国东部地区
							translateResults.add("1");
						} else {
							translateResults.add("0");
						}
						continue;
						// 翻译字段 End
					}
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
	
	public void lily20150510_2() throws Exception {
		List<List<List<String>>> allList = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据匹配错误的.xlsx", 3);
		
		for (int i = 0; i < allList.size(); i++) {
			List<List<String>> sheet = allList.get(i);
			for (int j = 0; j < sheet.size(); j++) {
				List<String> row = sheet.get(j);
				if(j == 0)
					continue;
				QueryDBResultHolder hodler = enterpriseDataDAO.queryEnterpriseData("t_enterprise_data_2007", "法人单位", row.get(3));
				if (null == hodler || hodler.getResultDatas() == null || hodler.getResultDatas().size() == 0) {
					continue;
				}
				List<String> result = hodler.getResultDatas().get(0);
				for (int index = 0; index < result.size(); index++) {
					if (index + 9 < row.size()) {
						row.set(index + 9, result.get(index));
					} else {
						row.add(result.get(index));
					}
				}
			}
		}
		
		for(int index = 0 ; index < allList.size();index ++ ){
			List<List<String>> list = allList.get(index);
			for (int i = 0; i < list.size(); i++) {
				List<String> row = list.get(i);
				if (i == 0) {
					row.add(3, "是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
					row.add(4, "是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
					row.add(16, "是否为我国东部地区(1:东部地区;0:非东部地区)");
					continue;
				}
				if(row.size() < 150)
					continue;
				String isAsiaCountry = "0";
				if (ASIA_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为亚洲国家
					isAsiaCountry = "1";
				} else {
					isAsiaCountry = "0";
				}
				String isDeveloped = "0";
				if (DEVELOPED_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为高收入国家
					isDeveloped = "1";
				} else {
					isDeveloped = "0";
				}
				String isEastArea = "0";
				if (EAST_AREA.indexOf(row.get(13)) >= 0) { // 是否为我国东部地区
					isEastArea = "1";
				} else {
					isEastArea = "0";
				}
				row.add(3, isAsiaCountry);
				row.add(4, isDeveloped);
				row.add(16, isEastArea);
			}
		}
		
		try {
			OutputStream os;
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据匹配错误的_根据2007年的MDB匹配后的结果.xlsx";
			os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			for (int index = 0; index < allList.size(); index++) {
				List<List<String>> list = allList.get(index);
				GuavaExcelUtil.writeDataToExcel(list, index + "", wb, os);
				if (list != null)
					list = null;// Help GC
			}
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lily20150510_1() throws Exception {
//		List<List<List<String>>> allList = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据补齐企业.xlsx", 2);
		List<List<List<String>>> allList = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据匹配错误的.xlsx", 3);

		for (int i = 0; i < allList.size(); i++) {
			List<List<String>> sheet = allList.get(i);
			boolean setedHead = false;
			for (int j = 0; j < sheet.size(); j++) {
				List<String> row = sheet.get(j);
				if(j == 0)
					continue;
				QueryDBResultHolder hodler = enterpriseDataDAO.queryEnterpriseData("t_enterprise_data_2002", "法人单位", row.get(3));
				if (null == hodler || hodler.getResultDatas() == null || hodler.getResultDatas().size() == 0) {
					continue;
				}
				
				if (!setedHead) {
					List<String> head = sheet.get(0);
					List<String> result = hodler.getResultMetaDatas();
					for (int index = 0; index < result.size(); index++) {
						head.set(index + 9, result.get(index));
					}
					setedHead = true;
				}
				
				List<String> result = hodler.getResultDatas().get(0);
				for (int index = 0; index < result.size(); index++) {
					if (index + 9 < row.size()) {
						row.set(index + 9, result.get(index));
					} else {
						row.add(result.get(index));
					}
				}
			}
		}
		
		for(int index = 0 ; index < allList.size();index ++ ){
			List<List<String>> list = allList.get(index);
			for (int i = 0; i < list.size(); i++) {
				List<String> row = list.get(i);
				if (i == 0) {
					row.add(3, "是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
					row.add(4, "是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
					// row.add(16, "是否为我国东部地区(1:东部地区;0:非东部地区)");
					continue;
				}
				
				String isAsiaCountry = "0";
				if (ASIA_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为亚洲国家
					isAsiaCountry = "1";
				} else {
					isAsiaCountry = "0";
				}
				String isDeveloped = "0";
				if (DEVELOPED_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为高收入国家
					isDeveloped = "1";
				} else {
					isDeveloped = "0";
				}
//				String isEastArea = "0";
//				if (EAST_AREA.indexOf(row.get(13)) >= 0) { // 是否为我国东部地区
//					isEastArea = "1";
//				} else {
//					isEastArea = "0";
//				}
				row.add(3, isAsiaCountry);
				row.add(4, isDeveloped);
//				row.add(16, isEastArea);
			}
		}
		
		try {
			OutputStream os;
//			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据补齐企业_修复后的数据.xlsx";
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\数据匹配错误的_根据2007年的MDB匹配后的结果.xlsx";
			
			os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			for (int index = 0; index < allList.size(); index++) {
				List<List<String>> list = allList.get(index);
				GuavaExcelUtil.writeDataToExcel(list, index + "", wb, os);
				if (list != null)
					list = null;// Help GC
			}
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	工业企业数据库即2000transformed1文件；海关数据库1月份数据即2000-01文件。
		匹配步骤：
		Step 1、筛选装备制造业企业
		筛选标准:在工业企业数据库中，筛选“行业类别”前两位数为33、34、35、36、37、38、39、40的企业。
		         在海关数据库中，筛选“海关代码”为71-93的企业。
		         
		Step 2、匹配企业。
		匹配标准一：匹配企业名称。
		企业名称匹配程度达到85%以上即认为是同一家企业。
		将匹配好的企业在两大数据库的信息合并（不做整合，只是简单排列，就像上次你帮妹妹做的那样，有个分割线就行）。
		
		匹配标准二：匹配电话号码后7位+邮编
	 * @since
	 * @throws
	 */
	public void odan_20150510(boolean isConcurrent, String sourceTable, String tableName, String yearMonth, boolean isBatch, int index, int prodCode, int threshold) throws Exception {
		// 没时间想优化的方案，怎么简单怎么来
		String sql = "select * from " + sourceTable + " where 行业类别  like :codition" + index;
		MapSqlParameterSource namedParameters = new MapSqlParameterSource("codition" + index, index + "%");
		
		QueryDBResultHolder holder = enterpriseDataDAO.query(sql, namedParameters);
		List<List<String>> sourceList = holder.getResultDatas();
		if (null == sourceList || sourceList.size() == 0)
			return;
		String customsSQL = "select * from " + tableName + " where 税号编码 like :codition" + prodCode;
		MapSqlParameterSource params = new MapSqlParameterSource("codition" + prodCode, prodCode + "%");
		QueryDBResultHolder customsHolder = enterpriseDataDAO.query(customsSQL, params);
		List<List<String>> destList = customsHolder.getResultDatas();
		if(destList == null || destList.size() == 0)
			return;

		
		// 开始匹配
		List<String> head = new ArrayList<String>();
		head.addAll(holder.getResultMetaDatas());
		head.add("--------左边是工业企业数据库数据，右边是海关数据-----------------");
		head.add("--------成功条件--------");
		head.addAll(customsHolder.getResultMetaDatas());
		
		int nThread = 1;
		if (isConcurrent) { // 计算线程数
			if (sourceList.size() <= 100)
				nThread = 2;
			else if (sourceList.size() <= 1000)
				nThread = 10;
			else 
				nThread = 10;
		}
		nThread = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(nThread);
		List<Future<List<List<String>>>> futures = new ArrayList<Future<List<List<String>>>>(nThread);
		int count = sourceList.size();
		for (int matchIndex = 0; matchIndex < nThread; matchIndex++) {
			int start = matchIndex * (count / nThread);// 开始下标
			int end = 0;
			if (matchIndex == nThread - 1) {
				end = (matchIndex + 1) * (count / nThread) + count % nThread;
			} else {
				end = (matchIndex + 1) * (count / nThread);
			}
			futures.add(executorService.submit(new OdanCallable(tableName, index, prodCode, start, end, sourceList, destList)));
		}
		
		try {
			List<List<String>> allMatchedResultList = new ArrayList<List<String>>(); 
			for (int i = 0; i < futures.size(); i++) {
				List<List<String>> matchedResultList = futures.get(i).get();
				if(matchedResultList.size() > 0)
					allMatchedResultList.addAll(matchedResultList);
			}
			if (allMatchedResultList.size() == 0)
				return;
			System.out.println("========================数据匹配结束，生成Excel结果===========================");
			System.out.println("allMatchedResultList   长度 ---->>>>" + allMatchedResultList.size());
			int resultSize = allMatchedResultList.size();
			int fileCount = resultSize / 4000;
			if (resultSize % 4000 != 0)
				fileCount++;
			System.out.println("==================需要将结果分解为【" + fileCount + "】个文件======================");

			String targetAbsoluteFileDir = "E:\\lily_mcfly\\odan\\" + yearMonth;
			File file = new File(targetAbsoluteFileDir);
			if(!file.exists())
				file.mkdir();
			
			for (int i = 0; i < fileCount; i++) {
				OutputStream os;
				int start = i * (resultSize / fileCount);// 开始下标
				int end = 0;
				if (i == fileCount - 1) {
					end = (i + 1) * (resultSize / fileCount) + resultSize % fileCount;
				} else {
					end = (i + 1) * (resultSize / fileCount);
				}
				System.out.println("生成文件，文件开始下标为：【" + start + "】,结束下标为【" + end + "】");
				String targetAbsoluteFilePath = targetAbsoluteFileDir + "\\" + tableName + "_" + index + "_" + prodCode + "_" + i + ".xlsx";
				os = new FileOutputStream(targetAbsoluteFilePath);
				XSSFWorkbook wb = new XSSFWorkbook();
				GuavaExcelUtil.writeDataToExcel(start, end, head, allMatchedResultList, "匹配到结果的数据", wb, os);
				wb.write(os);
				os.close();
			}
			allMatchedResultList = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		executorService.shutdownNow();
	}
	
	public static class OdanCallable implements Callable<List<List<String>>>{
		private int startIndex;
		private int endIndex;
		private List<List<String>> sourceList;
		private List<List<String>> destList;
		private String tableName;
		private int index;
		private int prodCode;

		public OdanCallable(String tableName, int index, int prodCode,
				int startIndex, int endIndex, List<List<String>> sourceList,
				List<List<String>> destList) {
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.sourceList = sourceList;
			this.destList = destList;
			this.tableName = tableName;
			this.index = index;
			this.prodCode = prodCode;
		}
		
		@Override
		public List<List<String>> call() throws Exception {
			List<List<String>> matchedResultList = new ArrayList<List<String>>();
			int destIndex1 = 15;
			int destIndex2 = 17;
			int destIndex3 = 19;
			
			int sourceIndex1 = 1;
			int sourceIndex2 = 3;
			int sourceIndex3 = 4;
			destIndex1 = 11;
			destIndex2 = 13;
			destIndex3 = 15;
			for (int i = startIndex; i < endIndex; i++) {
				System.out.println("正在处理：tableName为[" + tableName
						+ "], index 为 [" + index + "], prodCode 为 [" + prodCode
						+ "] 中 开始下标[" + startIndex + "]，结束下标[" + endIndex
						+ "],第：[" + i + "]条数据");
				List<String> sourceRow = sourceList.get(i);
				for (int j = 0; j < destList.size(); j++) {
					List<String> destRow = destList.get(j);
					boolean isMatch = false;
					int matchResult = odanMatching(sourceRow.get(sourceIndex1), destRow.get(destIndex1), tableName, index, prodCode, 85);
					if (1 == matchResult) { // 精确匹配
						isMatch = true;
					} else if (0 == matchResult || 2 == matchResult) {// 模糊匹配，需要根据"电话号码后7位+邮编"在匹配
						String sourcePhoneNumber = sourceRow.get(sourceIndex2);// 工业企业数据库中的电话号码
						if(StringUtils.isBlank(sourcePhoneNumber) || sourcePhoneNumber.length() < 7)
							isMatch = false;
						else {
							String destPhoneNumber = destRow.get(destIndex2);// 海关数据中的电话号码
							if (StringUtils.isBlank(destPhoneNumber) || destPhoneNumber.length() < 7)
								isMatch = false;
							else if ((sourcePhoneNumber.substring(sourcePhoneNumber.length() - 7, sourcePhoneNumber.length()) + sourceRow.get(sourceIndex3)).equals(destPhoneNumber.substring(destPhoneNumber.length() - 7, destPhoneNumber.length()) + destRow.get(destIndex3))) {
								isMatch = true;
								if (0 == matchResult) {
									matchResult = 3;
								}
							}
						}
					}
					
					if (isMatch) {
						List<String> matchResultRow = new ArrayList<String>();
						matchResultRow.addAll(sourceRow);
						matchResultRow.add("--------左边是工业企业数据库数据，右边是海关数据-----------------");
						if (matchResult == 1) {// 精确匹配
							matchResultRow.add("精确匹配--[企业名称一致]");
						} else if (matchResult == 2) {
							matchResultRow.add("模糊匹配--[企业名称的模糊匹配 ] + [电话号码后7位+邮编]双重匹配");
						} else if (matchResult == 3) {
							matchResultRow.add("末尾匹配--[电话号码后7位+邮编]");
						} else {
							matchResultRow.add("");
						}
						matchResultRow.addAll(destRow);
						matchedResultList.add(matchResultRow);
					}
				}
			}
			return matchedResultList;
		}
	}
	
	public static int odanMatching(String source, String dest, String tableName, int index, int prodCode, int threshold) {
		if(StringUtils.isBlank(source) || StringUtils.isBlank(dest))
			return 0;
		if (source.equals(dest))
			return 1;
		return calculatePercentage(source, dest) - threshold >= 0 ? 2 : 0;
	}
	
	public static int findLastIndex(String str) {
		int lastIndex = str.lastIndexOf("有限责任公司");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("股份有限公司");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("工业有限公司");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("集团有限公司");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("有限公司");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("集团");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		lastIndex = str.lastIndexOf("厂");
		if (lastIndex >= 0) {
			return lastIndex;
		}
		return str.length();
	}
	
	public static double calculatePercentage(String source, String dest) {
		char[] chars = source.toCharArray();
		int ignoreIndex = findLastIndex(source); // ignoreIndex 不在计算之内
		int ignoreCount = 0; // 特殊字符不考虑在内
		int count = 0;// 匹配到的个数
		for (int i = 0; i < ignoreIndex; i++) {
			if (chars[i] == '-' || chars[i] == '(' || chars[i] == ')'
					|| chars[i] == '（' || chars[i] == '）') {
				ignoreCount++;
				continue;
			}
			if(dest.indexOf(chars[i]) >= 0){
				count++;
			}
		}
		return ((double) count/ (ignoreIndex - ignoreCount)) * 100;
	}
	
	
	public QueryDBResultHolder step8(String sql,MapSqlParameterSource namedParameters) throws Exception {
		QueryDBResultHolder holder = enterpriseDataDAO.query(sql, namedParameters);
		return holder;
	}
}
