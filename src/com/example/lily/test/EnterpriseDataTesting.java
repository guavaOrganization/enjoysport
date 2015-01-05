package com.example.lily.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.lily.service.interfaces.IEnterpriseDataService;
import com.guava.util.GuavaExcelUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EnterpriseDataTesting {
	private transient static Log log = LogFactory.getLog(EnterpriseDataTesting.class);

	@Autowired
	@Qualifier("com.example.lily.service.impls.EnterpriseDataServiceImpl")
	IEnterpriseDataService enterpriseDataService;

//	@Test
	public void testRepairListData(){
		try {
			List<Integer> matchIndexs = new ArrayList<Integer>();
			matchIndexs.add(10);
			matchIndexs.add(11);
			matchIndexs.add(12);
			
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("法人单位");
			matchColumns.add("法人代表");
	
			List<List<String>> excelDatas = GuavaExcelUtil.loadExcelDataToList("E:\\丽丽\\企业财务数据整理\\lily_enterprise_company.xlsx");
			// 4993 
			List<List<String>> repairedList = enterpriseDataService.repairListData(excelDatas, 4993, 10000, 10, 1, matchIndexs, "t_enterprise_data_2007", matchColumns);
		
			OutputStream os = new FileOutputStream("E:\\丽丽\\企业财务数据整理\\lily_enterprise_company_repaired_data.xlsx");
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
			matchIndexs.add(10);
			matchIndexs.add(11);
			matchIndexs.add(12);
			
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("企业名称");
			matchColumns.add("法人代表姓名");
			
			String year = "1998";
			
			enterpriseDataService.matchingEnterpriseDataAndreateResultFileToExcel(
							"E:\\丽丽\\企业财务数据整理\\" + year + "\\" + year + "年企业财务数据整理结果.xlsx",
							"E:\\丽丽\\企业财务数据整理\\lily_enterprise_company_bak.xlsx", 
							matchIndexs, "t_enterprise_data_" + year, matchColumns, true, 10);
			if(log.isInfoEnabled())
				log.info("耗时............." + (System.currentTimeMillis() - now));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
