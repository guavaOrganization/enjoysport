package com.example.lily.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.lily.javabeans.QueryDBResultHolder;
import com.example.lily.service.interfaces.IEnterpriseDataService;
import com.guava.util.GuavaExcelUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EnterpriseDataTesting {
	private transient static Log log = LogFactory.getLog(EnterpriseDataTesting.class);

	@Autowired
	@Qualifier("com.example.lily.service.impls.EnterpriseDataServiceImpl")
	IEnterpriseDataService enterpriseDataService;

	// @Test
	public void testRepairListData() {
		try {
			List<Integer> matchIndexs = new ArrayList<Integer>();
			matchIndexs.add(10);
			matchIndexs.add(11);
			matchIndexs.add(12);
			
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("法人单位");
			matchColumns.add("法人代表");
	
//			List<List<String>> excelDatas = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊续集.xlsx");
			List<List<String>> excelDatas = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊啊.xlsx");
			List<List<String>> repairedList = enterpriseDataService.repairListData(excelDatas, 4993, 10000, 10, 1, matchIndexs, "t_enterprise_data_2007", matchColumns);
		
			OutputStream os = new FileOutputStream("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊修复后的数据.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(repairedList, "修补结果数据", wb, os);
			
			wb.write(os);
			os.close();// 关闭输出流  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryByLegalPersonCode() {
		try {
			long now = System.currentTimeMillis();
			List<Integer> matchIndexs = new ArrayList<Integer>();
//			matchIndexs.add(4);
			matchIndexs.add(5);
//			matchIndexs.add(6);
			
			List<String> matchColumns = new ArrayList<String>();
//			matchColumns.add("法人代码");
			matchColumns.add("法人单位");
//			matchColumns.add("法人代表");
			
			String year = "2008";
			String seqFlag = "_续集";
			enterpriseDataService.matchingEnterpriseDataAndreateResultFileToExcel(
							"E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\" + year + "\\" + year + "年企业财务数据整理结果" + seqFlag + ".xlsx",
							"E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊续集_修复后的数据.xlsx",
							matchIndexs, "t_enterprise_data_" + year, matchColumns, true, 10);
			if(log.isInfoEnabled())
				log.info("耗时............." + (System.currentTimeMillis() - now));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// @Test
	public void test() {
		try {
			k();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public void k() throws Exception { // 找出投资行业数据
		String yyyy = "1998";
		String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\非对外投资企业数据匹配\\" + yyyy + ".xlsx";
		// 初始化相关数据 start
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat thirdFormat = new SimpleDateFormat("yyyy");
		Map<String, String> map = new HashMap<String, String>();
		map.put("1998", "行业类别");
		map.put("1999", "行业类别");
		map.put("2000", "行业类别");
		map.put("2001", "行业类别");
		map.put("2002", "行业类别");
		map.put("2003", "行业类别");
		map.put("2004", "行业代码");
		map.put("2005", "行业代码");
		map.put("2006", "行业代码");
		map.put("2007", "行业代码");
		map.put("2008", "行业代码");
		map.put("2009", "行业代码");
		
		List<Integer> excelMatchList = new ArrayList<Integer>();
		excelMatchList.add(9);
		excelMatchList.add(10);
		excelMatchList.add(11);

		List<Integer> dbMatchList = new ArrayList<Integer>();
		dbMatchList.add(0);
		dbMatchList.add(1);
		dbMatchList.add(3);
		// 初始化相关数据 end 
		
		List<String> matchedResultsHead = new ArrayList<String>(); // 头部
		List<List<String>> matchedResults = new ArrayList<List<String>>();
		
		List<String> unmatchedResultsHead = new ArrayList<String>();// 头部
		List<List<String>> unmatchedResults = new ArrayList<List<String>>();

		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊啊.xlsx");
		matchedResultsHead.addAll(list.get(0));
		matchedResultsHead.add("------------左边是对外投资企业原始数据，右边是随机匹配到的同一行业非对外投资企业--------");
		
		unmatchedResultsHead.addAll(list.get(0));
		unmatchedResultsHead.add("--------备注--------");
		unmatchedResults.add(unmatchedResultsHead);
		
		for (int i = 0; null != list && i < list.size(); i++) {
			if (i == 0) {
				continue;
			}
			List<String> row = list.get(i);
			if (null == row.get(7) || "".equals(row.get(7).trim())) { // approvalDate格式不符合要求
				continue;
			}
			Date date = null;
			try {
				date = format.parse(row.get(7));
			} catch (ParseException e) {
				try {
					date = secondFormat.parse(row.get(7));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			if(null == date)
				continue;
			String dateStr = thirdFormat.format(date);
			if (!yyyy.equals(dateStr)) { // 分年份匹配
				continue;
			}
			String code = row.get(30);// 第30列，行业代码
			if (null == code || "".equals(code.trim())) {
				List<String> unMatchedRow = new ArrayList<String>();
				unMatchedRow.addAll(row);
				unMatchedRow.add("行业代码为空");
				unmatchedResults.add(unMatchedRow);
				continue;
			}
			QueryDBResultHolder holder = enterpriseDataService.queryEnterpriseData("t_enterprise_data_" + dateStr, map.get(dateStr), code);
			if (null == holder.getResultDatas() || holder.getResultDatas().size() == 0) {// 为匹配到数据
				List<String> unMatchedRow = new ArrayList<String>();
				unMatchedRow.addAll(row);
				unMatchedRow.add("根据条件匹配MDB中的列[" + map.get(dateStr) + "]=[" + code + "]，未匹配到数据");
				unmatchedResults.add(unMatchedRow);
				continue;
			}
			
			if (matchedResultsHead.size() == list.get(0).size() + 1) { // 把MDB中的列名加入到结果列表中
				matchedResultsHead.addAll(holder.getResultMetaDatas());
			}
			
			Random random = new Random();
			int index = random.nextInt(holder.getResultDatas().size());
			List<String> randomMatchedRow = holder.getResultDatas().get(index); // 随机匹配到的MDB行
			boolean matched = false;
			for (int j = 0; null != list && j < list.size(); j++) {
				List<String> excelRow = list.get(j);
				if (excelRow.get(excelMatchList.get(0)).equals(randomMatchedRow.get(0))) { // 匹配到了数据，那么代表MDB的数据是对外投资企业
					matched = true;
					List<String> unMatchedRow = new ArrayList<String>();
					unMatchedRow.addAll(row);
					unMatchedRow.add("随机匹配到一条企业数据，但是改企业是对外投资企业。条件是：" + excelMatchList.get(0));
					unmatchedResults.add(unMatchedRow);
				}
				if(matched)
					break;
				
				if (excelRow.get(excelMatchList.get(1)).equals(randomMatchedRow.get(1))) { // 匹配到了数据，那么代表MDB的数据是对外投资企业
					matched = true;
					List<String> unMatchedRow = new ArrayList<String>();
					unMatchedRow.addAll(row);
					unMatchedRow.add("随机匹配到一条企业数据，但是改企业是对外投资企业。条件是：" + excelMatchList.get(1));
					unmatchedResults.add(unMatchedRow);
				}
				if(matched)
					break;
				if (excelRow.get(excelMatchList.get(2)).equals(randomMatchedRow.get(2))) { // 匹配到了数据，那么代表MDB的数据是对外投资企业
					matched = true;
					List<String> unMatchedRow = new ArrayList<String>();
					unMatchedRow.addAll(row);
					unMatchedRow.add("随机匹配到一条企业数据，但是改企业是对外投资企业。条件是：" + excelMatchList.get(2));
					unmatchedResults.add(unMatchedRow);
				}
				if(matched)
					break;
			}
			if (!matched) {
				List<String> matchedRow = new ArrayList<String>();
				matchedRow.addAll(row);
				matchedRow.add("------------左边是对外投资企业原始数据，右边是随机匹配到的同一行业非对外投资企业--------");
				matchedRow.addAll(randomMatchedRow);
				matchedResults.add(matchedRow);
			}
		}
		
		matchedResults.add(0, matchedResultsHead);
		
		OutputStream os = new FileOutputStream(targetAbsoluteFilePath);
		XSSFWorkbook wb = new XSSFWorkbook();
		GuavaExcelUtil.writeDataToExcel(matchedResults, "匹配到结果的数据列表", wb, os);
		if(matchedResults != null)
			matchedResults = null;// Help GC
		GuavaExcelUtil.writeDataToExcel(unmatchedResults, "未匹配到结果的数据列表", wb, os);
		if(unmatchedResults != null)
			unmatchedResults = null;// Help GC
		wb.write(os);
		os.close();
	}
	
	// @Test
	public void lookRepetitiveData() {
		try {
			Set<String> set = new HashSet<String>();
			List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊啊.xlsx");
			List<List<String>> no = new ArrayList<List<String>>();
			no.add(list.get(0));
			List<List<String>> yes = new ArrayList<List<String>>();
			
			for (int i = 0; i < list.size(); i++) {
				if (i == 0)
					continue;
				List<String> row = list.get(i);
				String key = row.get(9);
				key = null == key || "".equals(key.trim()) ? "" : key.trim();
				if ("".equals(key)) {
					no.add(row);
					continue;
				}
				if (set.contains(key)) { // set用于记录已经比较过的数据
					yes.add(row);
					continue;
				}
				boolean isFind = false;
				for (int j = 0; j < list.size(); j++) {
					if (j == 0 || i == j) // 第一行和本身不进行比较
						continue;
					List<String> tempRow = list.get(j);
					String tempKey = tempRow.get(9);
					tempKey = null == tempKey || "".equals(tempKey.trim()) ? "" : tempKey.trim();
					if ("".equals(tempKey))
						continue;
					if(key.equals(tempKey)){
						isFind = true;
						break;
					}
				}
				if (isFind)
					yes.add(row);
				else
					no.add(row);
				set.add(key);
			}
			
			Collections.sort(yes, new Comparator<List<String>>() {
				@Override
				public int compare(List<String> paramT1, List<String> paramT2) {
					String key1 = paramT1.get(9);
					key1 = null == key1 || "".equals(key1.trim()) ? "" : key1.trim();
					String key2 = paramT2.get(9);
					key2 = null == key2 || "".equals(key2.trim()) ? "" : key2.trim();
					int int1 = 0;
					int int2 = 0;
					try {
						if (key1.endsWith("X") || key1.endsWith("x"))
							int1 = Integer.parseInt(key1.substring(0, key1.length() - 1));
						else if (key1.startsWith("X") || key1.startsWith("x"))
							int1 = Integer.parseInt(key1.substring(1, key1.length()));
						else
							int1 = Integer.parseInt(key1);
					} catch (Exception e) {
						int1 = 0;
					}
					try {
						if (key2.endsWith("X") || key2.endsWith("x"))
							int2 = Integer.parseInt(key2.substring(0, key2.length() - 1));
						else if (key2.startsWith("X") || key2.startsWith("x"))
							int2 = Integer.parseInt(key2.substring(1, key2.length()));
						else
							int2 = Integer.parseInt(key2);
					} catch (Exception e) {
						int2 = 0;
					}
					return int1 - int2;
				}
			});
			yes.add(0, list.get(0));
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\非对外投资企业数据匹配\\多国投资数据比对.xlsx";
			OutputStream os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(yes, "多国投资", wb, os);
			if(yes != null)
				yes = null;// Help GC
			GuavaExcelUtil.writeDataToExcel(no, "单国投资", wb, os);
			if(no != null)
				no = null;// Help GC
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void comp() {
		// 根据excel表格中的"Country"和"Acquiror name"去重。
		// 统计去重后的列表数据中"Acquiror name"的个数
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\非对外投资企业数据匹配\\多国投资数据比对.xlsx");
		Set<String> set = new HashSet<String>();
		Set<String> reSet = new HashSet<String>();

		for (int i = 0; null != list && i < list.size(); i++) {
			if (i == 0)
				continue;
			List<String> row = list.get(i);
			if (!set.add(row.get(2).trim() + "," + row.get(3).trim())) { // 已经在set中存在的数据表示重复数据
				reSet.add(row.get(2).trim() + "," + row.get(3).trim());
			}
		}
		
		Set<String> compSet = new HashSet<String>();
		Set<String> sigleCompSet = new HashSet<String>();

		List<List<String>> reslut = new ArrayList<List<String>>();
		
		List<List<String>> sigleReslut = new ArrayList<List<String>>();

		for (int i = 0; null != list && i < list.size(); i++) {
			if (i == 0){
				reslut.add(list.get(i));
				sigleReslut.add(list.get(i));
				continue;
			}
			List<String> row = list.get(i);
			
			if (reSet.contains(row.get(2).trim() + "," + row.get(3).trim())) { // 去重
				sigleReslut.add(row);
				sigleCompSet.add(row.get(3).trim());
			} else {
				reslut.add(row);
				compSet.add(row.get(3).trim());
			}
		}
		
		System.out.println("多国投资公司总共：" + compSet.size() + "个");
		System.out.println("单国N连投资公司总共：" + sigleCompSet.size()+ "个");
		
		try {
			OutputStream os;
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\非对外投资企业数据匹配\\多国投资数据比对最终结果final.xlsx";
			os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(reslut, "多国投资", wb, os);
			if(reslut != null)
				reslut = null;// Help GC
			
			GuavaExcelUtil.writeDataToExcel(sigleReslut, "单国N连投资", wb, os);
			if(sigleReslut != null)
				sigleReslut = null;// Help GC
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
