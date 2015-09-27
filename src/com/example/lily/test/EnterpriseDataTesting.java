package com.example.lily.test;

import java.io.File;
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
import java.util.Iterator;
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
import com.example.lily.service.impls.EnterpriseDataServiceImpl;
import com.example.lily.service.interfaces.IEnterpriseDataService;
import com.guava.codeautogenerator.core.support.StringUtils;
import com.guava.util.GuavaExcelUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EnterpriseDataTesting {
	private transient static Log log = LogFactory.getLog(EnterpriseDataTesting.class);

	@Autowired
	@Qualifier("com.example.lily.service.impls.EnterpriseDataServiceImpl")
	IEnterpriseDataService enterpriseDataService;
	
	@Test
	public void lily_20150925() {
		String dirPath = "/Users/mcfly/lily_mcfly/";
		String sourceDriName = "第四批次数据/";
		String desDriName = "第五批次数据/";
		
		String[] prefixs = new String[] { "对外直接投资2002年", "对外直接投资2007年", "对外直接投资其他年份企业" };
		String[] middles = new String[] { "单国N连投", "投资单个国家的", "投资多个国家的企业", "1998年", "2000年", "2001年" };
		String[] years = new String[] { "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009" };
		Map<String,String> dbIndexMapping =new HashMap<String, String>(); 
		dbIndexMapping.put("1998", "行业类别");
		dbIndexMapping.put("1999", "行业类别");
		dbIndexMapping.put("2000", "行业类别");
		dbIndexMapping.put("2001", "行业类别");
		dbIndexMapping.put("2002", "行业类别");
		dbIndexMapping.put("2003", "行业类别");
		dbIndexMapping.put("2004", "行业代码");
		dbIndexMapping.put("2005", "行业代码");
		dbIndexMapping.put("2006", "行业代码");
		dbIndexMapping.put("2007", "行业代码");
		dbIndexMapping.put("2009", "行业代码");
		
		String surfix = ".xlsx";
		for (int i = 0; i < prefixs.length; i++) {
			int excelMatchIndex = 0;
			if (i == 0) {
				excelMatchIndex = 27;
			} else if (i == 1) {
				excelMatchIndex = 33;
			}
			for (int j = 0; j < middles.length; j++) {
				if (i == 2 && j <= 2)
					continue;
				if (i == 2 && j == 3)
					excelMatchIndex = 33;
				if (i == 2 && j == 4)
					excelMatchIndex = 31;
				if (i == 2 && j == 5)
					excelMatchIndex = 27;
				for (int k = 0; k < years.length; k++) {
					String sourceFileFullName = dirPath + sourceDriName + prefixs[i] + "_" + middles[j] + "_" + years[k] + surfix;
					List<List<String>> excelDatas = null;
					try {
						excelDatas = GuavaExcelUtil.loadExcelDataToList(sourceFileFullName);
					} catch (Exception e) {
						// e.printStackTrace();
						excelDatas = null;
					}
					if (null == excelDatas || excelDatas.size() == 0)
						continue;
					try {
						if(null == dbIndexMapping.get(years[k]))
							continue;
						System.out.println("sourceFileFullName >>>>> " + sourceFileFullName);
						System.out.println("years[k] >>>>>>>>>>>>" + years[k] + " >>>>>>>>>>>>>> value >>>>>>>>>>>>> " + dbIndexMapping.get(years[k]));
						List<List<String>> resultList = enterpriseDataService.matchingEnterpriseData(excelDatas, excelMatchIndex, years[k], dbIndexMapping.get(years[k]));
						if (null == resultList || resultList.size() == 0)
							continue;
						OutputStream os = null;
						try {
							String desFileFullName = dirPath + desDriName + prefixs[i] + "_" + middles[j] + "_" + years[k] + surfix;
							os = new FileOutputStream(desFileFullName);
							XSSFWorkbook wb = new XSSFWorkbook();
							GuavaExcelUtil.writeDataToExcel(resultList, "结果数据", wb, os);
							wb.write(os);
							os.close();// 关闭输出流
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (os != null) {
								try {
									os.close();
								} catch (IOException e) {
									// e.printStackTrace();
								}
							}
						}
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}
		}
	}
	
	// @Test
	public void lily_20150924() {
		String dirPath = "/Users/mcfly/lily_mcfly/";
		String surfix = ".xlsx";
		String fileName = "对外直接投资其他年份企业";
		String sourceFileFullName = dirPath + fileName + surfix;
		String desDriName = "第四批次数据/";
		int index = 9;
		
		Map<String,List<List<String>>> excelDatas = GuavaExcelUtil.loadExcelDataToMap(sourceFileFullName, 3);
		Iterator<String> keyIte = excelDatas.keySet().iterator();
		while (keyIte.hasNext()) {
			String sheetName = keyIte.next();
			List<List<String>> sheetData = excelDatas.get(sheetName);
			Map<String, List<List<String>>> result = new HashMap<String, List<List<String>>>();
			
			if(null == sheetData || sheetData.size() == 1)
				continue;
			for (int i = 0; i < sheetData.size(); i++) {
				if (i == 0)
					continue;
				List<String> row = sheetData.get(i);
				if (null == row || row.size() < 10)
					continue;
				String approvalDate = row.get(index);
				if (StringUtils.isBlank(approvalDate) || approvalDate.length() < 4)
					continue;
				String year = approvalDate.substring(0, 4);
				List<List<String>> data = null;
				if (!result.containsKey(year)) {
					data = new ArrayList<List<String>>();
					data.add(sheetData.get(0));
				} else {
					data = result.get(year);
				}
				data.add(row);
				result.put(year, data);
			}
			
			Iterator<String> resultKeyIte = result.keySet().iterator();
			while (resultKeyIte.hasNext()) {
				String year = resultKeyIte.next();
				String desFileFullName = dirPath + desDriName + fileName + "_" + sheetName + "_" + year + surfix;
				OutputStream os = null;
				try {
					os = new FileOutputStream(desFileFullName);
					XSSFWorkbook wb = new XSSFWorkbook();
					GuavaExcelUtil.writeDataToExcel(result.get(year), "结果数据",
							wb, os);
					wb.write(os);
					os.close();// 关闭输出流
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	// @Test
	public void lily_20150917() {
		String dirPath = "/Users/mcfly/lily_mcfly/";
		String sourceDirName = "第二批次数据/";
		String desDriName = "第三批次数据/";
		String[] sheetNames = new String[] { "1998年", "2000年", "2001年", "2002年", "2003年", "2004年", "2005年", "2007年", "2008年", "2009年" };
		String[] years = new String[] { "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009" };
		String prefix = "对外直接投资";
		String surfix = ".xlsx";
		for (int i = 0; i < sheetNames.length; i++) {
			if(i < 7)
				continue;
			int splitIndex = getSplitIndex(i);
			for (int j = 0; j < years.length - 1; j++) {
				if (i == 7 && j <= 5)
					continue;
				String fileName1 = dirPath + sourceDirName + prefix + sheetNames[i] + "_" + years[j] + surfix;
				String fileName2 = dirPath + sourceDirName + prefix + sheetNames[i] + "_" + years[j + 1] + surfix;
				System.out.println("正在处理..........." + sheetNames[i] + "----" + years[j] + "----" + years[j + 1]);
				boolean isFileNotFound = false;
				List<List<String>> excelData1s = null;
				try {
					excelData1s = GuavaExcelUtil.loadExcelDataToList(fileName1);
				} catch (Exception e) {
					if (e instanceof FileNotFoundException)
						isFileNotFound = true;
				}
				if (isFileNotFound || null == excelData1s || excelData1s.size() == 0)
					continue;
				
				List<List<String>> excelData2s = null;
				try {
					excelData2s = GuavaExcelUtil.loadExcelDataToList(fileName2);
				} catch (Exception e) {
					if (e instanceof FileNotFoundException)
						isFileNotFound = true;
				}
				if (isFileNotFound || null == excelData2s || excelData2s.size() == 0)
					continue;
				if (splitIndex <= 0)
					continue;
				String desFileFullName = dirPath + desDriName + prefix + sheetNames[i] + "_" + years[j] + "_" + years[j + 1];
				List<List<String>> results = excelMatch(excelData1s, excelData2s, splitIndex, years[j], years[j + 1]);
				excelData1s = null;
				excelData2s = null;
				if(null != results && results.size() > 0){
					System.out.println(desFileFullName);
					createFile(results, desFileFullName);
				}
			}
		}
	}
	
	private int getSplitIndex(int i) {
		int splitIndex = 0;
		switch (i) {
			case 0:
				splitIndex = 123;
				break;
			case 1:
				splitIndex = 114;
				break;
			case 2:
				splitIndex = 174;
				break;
			case 3:
				splitIndex = 105;
				break;
			case 4:
				splitIndex = 92;
				break;
			case 5:
				splitIndex = 170;
				break;
			case 6:
				splitIndex = 147;
				break;
			case 7:
				splitIndex = 154;
				break;
			case 8:
				splitIndex = 76;
				break;
			case 9:
				splitIndex = 76;
				break;
			default:
				break;
		}
		return splitIndex;
	}

	private void createFile(List<List<String>> results, String desFileFullName) {
		if(null == results || results.size() == 0)
			return;
		int fileNumbers = 1;
		if (results.size() > 10000 && results.size() <= 20000) {
			fileNumbers = 2;
		}
		if (results.size() > 20000 && results.size() <= 30000) {
			fileNumbers = 3;
		}
		if (results.size() > 30000 && results.size() <= 40000) {
			fileNumbers = 4;
		}
		if (results.size() > 40000 && results.size() <= 50000) {
			fileNumbers = 5;
		} else if (results.size() > 50000) {
			fileNumbers = 15;
		}
		for (int i = 0; i < fileNumbers; i++) {
			OutputStream os = null;
			int start = i * (results.size() / fileNumbers);// 开始下标
			int end = 0;
			if (i == fileNumbers - 1) {
				end = (i + 1) * (results.size() / fileNumbers) + results.size() % fileNumbers;
			} else {
				end = (i + 1) * (results.size() / fileNumbers);
			}
			try {
				os = new FileOutputStream(desFileFullName + "_" + i + ".xlsx");
				XSSFWorkbook wb = new XSSFWorkbook();
				GuavaExcelUtil.writeDataToExcel(results, "匹配到结果的数据列表", wb, os, start, end);
				wb.write(os);
				os.close();// 关闭输出流
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	private List<List<String>> excelMatch(List<List<String>> excelData1s, List<List<String>> excelData2s, int splitIndex, String year1,String year2) {
		List<List<String>> results = new ArrayList<List<String>>();
		List<String> head = new ArrayList<String>();

		List<String> head1 = excelData1s.get(0);
		List<String> head2 = excelData2s.get(0);
		if (head1 == null || head1.size() == 0 || head2 == null || head2.size() == 0)
			return null;
		List<String> subList = head2.subList(splitIndex, head2.size());
		
		String tip = "------------左边是：" + year1 + "年的数据，右边是：" + year2 + "年的数据------------";
		
		head.addAll(head1);
		head.add(tip);
		head.addAll(subList);
		
		results.add(head);
		int[] macthIndexs1 = getMatchIndexs(year1);
		int[] macthIndexs2 = getMatchIndexs(year2);
		for (int i = 0; i < excelData1s.size(); i++) {
			if(i == 0)
				continue;
			List<String> row1 = excelData1s.get(i);
			if(null == row1 || row1.size() == 0)
				continue;
			List<String> subList1 = null;
			try {
				subList1 = row1.subList(splitIndex, row1.size());
			} catch (Exception e) {
				System.out.println("第一个表格，出现问题的行数--" + (i + 1));
			}
			if(null == subList1)
				continue;
			for (int j = 0; j < excelData2s.size(); j++) {
				if (j == 0)
					continue;
				List<String> row2 = excelData2s.get(j);	
				if(null == row2 || row2.size() == 0)
					continue;
				List<String> subList2 = null;
				try {
					subList2 = row2.subList(splitIndex, row2.size());
				} catch (Exception e) {
					System.out.println("出现异常的行为：" + (j + 1));
				}
				if(null == subList2)
					continue;
				
				if (isMatch(subList1, subList2, macthIndexs1, macthIndexs2)) {
					List<String> result = new ArrayList<String>();
					result.addAll(row1);
					result.add(tip);
					result.addAll(subList2);
					results.add(result);
				}
			}
		}
		return results;
	}
	
	private boolean isMatch(List<String> subList1, List<String> subList2, int[] macthIndexs1, int[] macthIndexs2) {
		for (int i = 0; i < 3; i++) {
			if (macthIndexs1[i] < 0 || macthIndexs2[i] < 0) {
				continue;
			}
			String col1 = subList1.get(macthIndexs1[i]);
			String col2 = subList2.get(macthIndexs2[i]);
			if (StringUtils.isBlank(col1) || StringUtils.isBlank(col2))
				continue;
			if (col1.trim().equals(col2.trim()))
				return true;
		}
		return false;
	}
	
	// 年份     法人代码		法人代表名称			企业名称
	// 1998       0				5					3
	// 1999		  0				3					1
	// 2000		  0				2					1
	// 2001		  0				2					1
	// 2002		  0				2					1
	// 2003		  0				2					1
	// 2004		  0				2					1
	// 2005		  0				2					1
	// 2006		  0				2					1
	// 2007		  0				2					1
	// 2008		 -1				-1					0
	// 2009		  0				-1					1
	private int[] getMatchIndexs(String year) {
		if ("1998".equals(year)) {
			return new int[] { 0, 5, 3 };
		} else if ("1999".equals(year)) {
			return new int[] { 0, 3, 1 };
		} else if ("2008".equals(year)) {
			return new int[] { -1, -1, 0 };
		} else if ("2009".equals(year)) {
			return new int[] { 0, -1, 1 };
		} else {
			return new int[] { 0, 2, 1 };
		}
	}
	
	
	// 对外直接投资其他年份企业_1998_1998年.xlsx
	// 将上面的文件重命名
	// @Test
	public void lily_20150916_1() {
		String dirPath = "/Users/mcfly/lily_mcfly/";
		String sourceDirName = "第一批次数据/";
		String disDirName = "第二批次数据/";
		String[] sheetNames = new String[] { "1998年", "2000年", "2001年", "2003年", "2004年", "2005年", "2008年", "2009年" };
		String[] years = new String[] { "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009" };
		String suffix = ".xlsx";
		for (int i = 0; i < sheetNames.length; i++) {
			for (int j = 0; j < years.length; j++) {
				String fileName = dirPath + sourceDirName + "对外直接投资其他年份企业" + "_" + years[j] + "_" + sheetNames[i] + suffix;
				File file = new File(fileName);
				file.renameTo(new File (dirPath + disDirName + "对外直接投资" + sheetNames[i] + "_" + years[j] + suffix));
			}
		}
	}
	
	// @Test
	// 对外直接投资2002年_1998_单国N连投.xlsx
	// 对外直接投资2002年_1998_投资单个国家的.xlsx
	// 对外直接投资2002年_1998_投资多个国家的企业.xlsx
	// 将这三个文件中的“匹配到结果的数据列表”的sheet页合并
	public void lily_20150916() throws Exception {
		try {
			String[] strings = new String[]{"对外直接投资2007年"};
			String[] years = new String[] { "2008", "2009" };
			String[] sheetNames = new String[] { "单国N连投", "投资单个国家的", "投资多个国家的" };
			String dirPath = "/Users/mcfly/lily_mcfly/";
			String sourceDirName = "第一批次数据/";
			String disDirName = "第二批次数据/";
			String suffix = ".xlsx";
			for (int i = 0; i < strings.length; i++) {
				for (int j = 0; j < years.length; j++) {
					List<List<String>> rows = new ArrayList<List<String>>();
					for (int k = 0; k < sheetNames.length; k++) {
						String fileFullName = dirPath + sourceDirName + strings[i] + "_" + years[j] + "_" + sheetNames[k] + suffix;
						List<List<String>> excelDatas = GuavaExcelUtil.loadExcelDataToList(fileFullName);
						if (null == excelDatas || excelDatas.size() < 1) {
							continue;
						}
						if (k != 0) {
							excelDatas.remove(0);// 把标题一处
						}
						if (excelDatas.size() == 0)
							continue;
						rows.addAll(excelDatas);
					}
					if (rows.size() == 0)
						continue;
					String desFileFullName = dirPath + disDirName + strings[i] + "_" + years[j] + suffix;
					OutputStream os = new FileOutputStream(desFileFullName);
					XSSFWorkbook wb = new XSSFWorkbook();
					GuavaExcelUtil.writeDataToExcel(rows, "匹配到结果的数据列表", wb, os);
					wb.write(os);
					os.close();// 关闭输出流
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

//	@Test
	public void lily_20150529() {// 匹配工业企业数据库数据
		try {
			List<Integer> matchIndexs = new ArrayList<Integer>();
			matchIndexs.add(12);
			matchIndexs.add(13);
			matchIndexs.add(14);
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("法人单位");
			matchColumns.add("法人代表");
			String year = "2001";
			long now = System.currentTimeMillis();
			enterpriseDataService.lily_20150529(
					"/Users/mcfly/lily_mcfly/工业企业数据/对外直接投资2007年_" + year,
					"/Users/mcfly/lily_mcfly/工业企业数据/对外直接投资2007年.xlsx", matchIndexs,
					"t_enterprise_data_" + year, matchColumns, true, 4, 3);
			System.out.println("共耗时～～～～" + (System.currentTimeMillis() - now));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
			List<List<String>> excelDatas = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充.xlsx");
			List<List<String>> repairedList = enterpriseDataService.repairListData(excelDatas, 4993, 10000, 10, 1, matchIndexs, "t_enterprise_data_2007", matchColumns);
		
			OutputStream os = new FileOutputStream("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充_企业数据修复.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(repairedList, "修补结果数据", wb, os);
			
			wb.write(os);
			os.close();// 关闭输出流  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testQueryByLegalPersonCode() {
		try {
			long now = System.currentTimeMillis();
			List<Integer> matchIndexs = new ArrayList<Integer>();
			matchIndexs.add(12);
			matchIndexs.add(13);
			matchIndexs.add(14);
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("企业名称");
			matchColumns.add("法人代表姓名");
			
			String year = "1998";
			enterpriseDataService.matchingEnterpriseDataAndreateResultFileToExcel(
							"/Users/mcfly/lily_mcfly/对外直接投资2007年_" + year + ".xlsx",
							"/Users/mcfly/lily_mcfly/对外直接投资2007年.xlsx",
							matchIndexs, "t_enterprise_data_" + year, matchColumns, false, 10);
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
	
//	@Test
	public void lily20150422_2() { // 根据《境外直接投资企业》或《境外直接投资企业_补充》得出《****多国投资数据比对》数据,后置方法是lily20150422_3
		try {
			Set<String> set = new HashSet<String>();
			String execlFullFileName = "/Users/mcfly/lily_mcfly/地区分类_东部地区";
			List<List<String>> list = GuavaExcelUtil.loadExcelDataToList(execlFullFileName + ".xlsx");

			List<List<String>> no = new ArrayList<List<String>>();
			no.add(list.get(0));

			List<List<String>> yes = new ArrayList<List<String>>();

			for (int i = 0; i < list.size(); i++) {
				if (i == 0)
					continue;
				List<String> row = list.get(i);
				String key = row.get(5);
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
					String tempKey = tempRow.get(5);
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
					String key1 = paramT1.get(5);
					key1 = null == key1 || "".equals(key1.trim()) ? "" : key1.trim();
					String key2 = paramT2.get(5);
					key2 = null == key2 || "".equals(key2.trim()) ? "" : key2.trim();
					if (key1.equals(key2)) {
						return 0;
					} else {
						return -1;
					}
				}
			});
			
			yes.add(0, list.get(0));
			String targetAbsoluteFilePath = execlFullFileName + "_单国_多国.xlsx";
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
	public void lily20150422_3() {// 根据《****多国投资数据比对》文件获取 "多国投资"和"单国N连投资",前置方法是${@link lily20150422_2())
		// 根据excel表格中的"Country"和"Acquiror name"去重。
		// 统计去重后的列表数据中"Acquiror name"的个数
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充_多国投资数据比对.xlsx");
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
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充_多国投资数据比对_最终结果final.xlsx";
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
	
//	@Test
	public void lily20150422() { // 根据"境外投资企业不匹配" 修复  "啊啊啊啊数据遗漏部分终极版"的数据
		List<List<String>> listA = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊数据遗漏部分终极版.xlsx");
		List<List<String>> listB = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外投资企业不匹配.xlsx");

		List<List<String>> result = new ArrayList<List<String>>();
		List<List<String>> noResult = new ArrayList<List<String>>();
		result.add(listA.get(0));
		result.get(0).addAll(1, listB.get(0));
		noResult.add(listA.get(0));
		
		for (int i = 0; i < listA.size(); i++) {
			List<String> rowA = listA.get(i);
			if (i == 0)
				continue;
			List<List<String>> matched = new ArrayList<List<String>>();
			for (int j = 0; j < listB.size(); j++) {
				if (j == 0)
					continue;
				List<String> rowB = listB.get(j);
				if (rowA.get(1).trim().equals(rowB.get(2).trim())) {
					matched.add(rowB);
				}
			}
			
			if (matched.size() == 0) {
				noResult.add(rowA);
			} else {
				for (int k = 0; k < matched.size(); k++) {
					List<String> newRow = new ArrayList<String>();
					newRow.addAll(rowA);
					newRow.addAll(1, matched.get(k));
					result.add(newRow);
				}
			}
			matched = null; //help GC
		}
		listA = null;
		listB = null;
		
		
		try {
			OutputStream os;
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\啊啊啊啊数据遗漏部分终极版final.xlsx";
			os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(result, "匹配到结果数据", wb, os);
			if(result != null)
				result = null;// Help GC
			
			GuavaExcelUtil.writeDataToExcel(noResult, "未匹配到结果数据", wb, os);
			if(noResult != null)
				noResult = null;// Help GC
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//	@Test
	// 《境外直接投资企业》和《境外直接投资企业-补充》里分下将数据中的国家列按照《UNCATED发达国家》中的国家分类，将国家（第C列）分为发达国家（1）非发达国家（0）、亚洲国家（1）非亚洲国家（0）、将省（第P列）分为东部地区（1）
	public void lily20150422_1() {
		List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充.xlsx");
		for (int i = 0; i < list.size(); i++) {
			List<String> row = list.get(i);
			if (i == 0) {
				row.add(3, "是否为亚洲国家（地区）(1:亚洲国家,0:非亚洲国家)");
				row.add(4, "是否为高收入国家(1:高收入国家;0:非高收入其他国家)");
				row.add(16, "是否为我国东部地区(1:东部地区;0:非东部地区)");
				continue;
			}
			
			String isAsiaCountry = "0";
			if (EnterpriseDataServiceImpl.ASIA_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为亚洲国家
				isAsiaCountry = "1";
			} else {
				isAsiaCountry = "0";
			}
			String isDeveloped = "0";
			if (EnterpriseDataServiceImpl.DEVELOPED_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为高收入国家
				isDeveloped = "1";
			} else {
				isDeveloped = "0";
			}
			String isEastArea = "0";
			if (EnterpriseDataServiceImpl.EAST_AREA.indexOf(row.get(13)) >= 0) { // 是否为我国东部地区
				isEastArea = "1";
			} else {
				isEastArea = "0";
			}
			row.add(3, isAsiaCountry);
			row.add(4, isDeveloped);
			row.add(16, isEastArea);
		}
		
		try {
			OutputStream os;
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\境外直接投资企业_补充_国家分类.xlsx";
			os = new FileOutputStream(targetAbsoluteFilePath);
			XSSFWorkbook wb = new XSSFWorkbook();
			GuavaExcelUtil.writeDataToExcel(list, "国家分类", wb, os);
			if(list != null)
				list = null;// Help GC
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	@Test
	// 根据lily20150422_3()方法得出的结果文件"境外直接投资企业_补充_多国投资数据比对_最终结果final"或"境外直接投资企业_多国投资数据比对_最终结果final"进行国家分类
	public void lily20150422_4() {
		String excelFileName = "境外直接投资企业_多国投资数据比对_最终结果final";
		
		List<List<List<String>>> allList = GuavaExcelUtil.loadExcelDataToList( "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\" + excelFileName + ".xlsx", 3);

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
				
				String isAsiaCountry = "0";
				if (EnterpriseDataServiceImpl.ASIA_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为亚洲国家
					isAsiaCountry = "1";
				} else {
					isAsiaCountry = "0";
				}
				String isDeveloped = "0";
				if (EnterpriseDataServiceImpl.DEVELOPED_COUNTRY.indexOf(row.get(2)) >= 0) { // 是否为高收入国家
					isDeveloped = "1";
				} else {
					isDeveloped = "0";
				}
				String isEastArea = "0";
				if (EnterpriseDataServiceImpl.EAST_AREA.indexOf(row.get(13)) >= 0) { // 是否为我国东部地区
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
			String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\企业财务数据整理\\" + excelFileName + "_国家分类.xlsx";
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
	 * 根据Acquiror name到2007年的工业企业数据库中匹配，如果工业企业数据库的企业名字和Acquiror
	 * name相同，则为同一家企业，则匹配成功
	 * ，然后把法人代码及其之后的列补充好如果2007年中找不到那个企业，则去2002年工业企业数据中进行匹配，规则相同。
	 * @since
	 * @throws
	 */
//	@Test
	public void lily20150510_1() {
		try {
			enterpriseDataService.lily20150510_1();
//			enterpriseDataService.lily20150510_2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void odan_20150510() {
		for (int year = 2002; year < 2003; year++) {
			String sourceTable = "odan_transformed_" + year;
			for (int index = 11; index <= 12; index++) {
				String yearMonth = year + "" + (index < 10 ? "0" + index : index);
				String destTable = "odan_customs_" + yearMonth;
				try {
					for (int i = 33; i <= 40; i++) {
						if (index == 11 && i <= 35)
							continue;
						for (int j = 84; j <= 92; j++) {
							if(index == 11 && i == 36 && j < 85)
								continue;
							enterpriseDataService.odan_20150510(true, sourceTable, destTable, yearMonth, false, i, j, 85);
						}
					}
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}
		}
	}
	
//	@Test
	public void step8() throws Exception {
		GovernmentData.step8(enterpriseDataService);
	}
	
	// @Test
	public void test_odan_20150511(){
		for (int i = 1; i <= 12; i++) {
			String month = i >= 10 ? "" + i : "0" + i;
			odan_20150511("odan_customs_2001" + month, "2001" + month);
		}
	}
	
	public void odan_20150511(String tableName,String yearMonth) {
		for (int index = 1; index <= 5; index++) {
			List<List<String>> allList = new ArrayList<List<String>>(); 
			try {
				boolean addHead = false;
				for (int i = 33; i <= 40; i++) {
					if (!((index == 1 && (33 <= i && i <= 34))
					   || (index == 2 && (35 <= i && i <= 36)) 
					   || (index == 3 && (37 <= i && i <= 38))
					   || (index == 4 && (i == 39))
					   || (index == 5 && (i == 40))
						 ))
						continue;
					System.out.println("zzzzzzzzzzzzzzzzz");
					for (int j = 84; j <= 92; j++) {
						try {
							List<List<String>> list = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\odan\\" + yearMonth + "\\" + tableName + "_" + i + "_" + j + ".xlsx");
							if(null == list || list.size() ==0 || list.size() == 1)
								continue;
							if (addHead) {
								list.remove(0);
							}
							allList.addAll(list);
							addHead = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				OutputStream os;
				String targetAbsoluteFilePath = "E:\\lily_mcfly\\odan\\" + yearMonth + "\\" + tableName + "_" + index + ".xlsx";
				os = new FileOutputStream(targetAbsoluteFilePath);
				XSSFWorkbook wb = new XSSFWorkbook();
				GuavaExcelUtil.writeDataToExcel(allList, "匹配到结果的数据", wb, os);
				if(allList != null)
					allList = null;// Help GC
				wb.write(os);
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 现有A和B(见附录一)两个Excel原始文件。需要对A文件中的"多国投资(多国投资公司总共108个)"Sheet页(以下简称ASheet)的数据进行去重，去重的条件如下：
		根据ASheet的单行数据的Country、Acquiror name、approvalDate三列的值与B文件中的"投资多个国家的"Sheet页(以下简称BSheet)
		的Country、Acquiror name、approvalDate逐行对比，如果存在一致数据，则将ASheet也对应的数据删除；否则保留。
		
		附录一：
		A : 境外直接投资企业_补充_多国投资数据比对_最终结果final_国家分类.xlsx
		B : 对外直接投资2007年.xlsx
	 * @since
	 * @throws
	 */
//	@Test
	public void lily_20150513() {
		try {
			List<List<List<String>>> aLists = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\原始数据\\境外直接投资企业_补充_多国投资数据比对_最终结果final_国家分类.xlsx",3);
			List<List<List<String>>> bLists = GuavaExcelUtil.loadExcelDataToList("E:\\lily_mcfly\\丽丽--企业财务数据\\原始数据\\对外直接投资2007年.xlsx",3);
			
			for (int index = 0; index < 3; index++) {
				List<List<String>> needDelResultList = new ArrayList<List<String>>();
				List<List<String>> aList = aLists.get(index);
				List<List<String>> bList = bLists.get(index);
				
				List<String> head = new ArrayList<String>();
				head.addAll(aList.get(0));
				head.add("-------左边是A文件数据，右边是B文件数据-------出现此种情况代表A文件的某行数据根据条件出现在B文件中，先把该行数据删除，并记录在此Sheet页中-------");
				head.addAll(bList.get(0));
				needDelResultList.add(head);
				
				List<List<String>> needDelList = new ArrayList<List<String>>();
				
				for (int i = 0; i < aList.size(); i++) {
					if (i == 0)
						continue;
					List<String> aRow = aList.get(i);
					if (StringUtils.isBlank(aRow.get(9)))
						continue;
					for (int j = 0; j < bList.size(); j++) {
						if (j == 0)
							continue;
						List<String> bRow = bList.get(j);
						if (StringUtils.isBlank(bRow.get(9)))
							continue;
						if ((aRow.get(2) + "," + aRow.get(5) + "," + aRow.get(9).replace("-", "/"))
						.equals(bRow.get(2) + "," + bRow.get(5) + "," + bRow.get(9).replace("-", "/"))) {
							needDelList.add(aRow);
							
							List<String> needDelResultRow = new ArrayList<String>();
							needDelResultRow.addAll(aRow);
							needDelResultRow.add("-------左边是A文件数据，右边是B文件数据-------出现此种情况代表A文件的某行数据根据条件出现在B文件中，先把该行数据删除，并记录在此Sheet页中-------");
							needDelResultRow.addAll(bRow);
							needDelResultList.add(needDelResultRow);
							break;
						}
					}
				}
				aList.removeAll(needDelList);
				try {
					OutputStream os;
					String fileName = "多国投资_修复重复数据结果.xlsx";
					if (index == 1) {
						fileName = "单国投资_修复重复数据结果.xlsx";
					} else if (index == 2) {
						fileName = "单国N连投资_修复重复数据结果.xlsx";
					}
					String targetAbsoluteFilePath = "E:\\lily_mcfly\\丽丽--企业财务数据\\修复数据\\" + fileName;
					
					os = new FileOutputStream(targetAbsoluteFilePath);
					XSSFWorkbook wb = new XSSFWorkbook();
					GuavaExcelUtil.writeDataToExcel(aList, "保留下来的数据", wb, os);
					if(aList != null)
						aList = null;// Help GC
					GuavaExcelUtil.writeDataToExcel(needDelResultList, "被删除的数据", wb, os);
					if(needDelResultList != null)
						needDelResultList = null;// Help GC
					wb.write(os);
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
