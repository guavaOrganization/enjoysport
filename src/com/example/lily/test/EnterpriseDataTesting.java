package com.example.lily.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.lily.service.interfaces.IEnterpriseDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EnterpriseDataTesting {
	private transient static Log log = LogFactory.getLog(EnterpriseDataTesting.class);

	@Autowired
	@Qualifier("com.example.lily.service.impls.EnterpriseDataServiceImpl")
	IEnterpriseDataService enterpriseDataService;

	@Test
	public void testQueryByLegalPersonCode() {
		try {
			long now = System.currentTimeMillis();
			List<Integer> matchIndexs = new ArrayList<Integer>();
			matchIndexs.add(9);
			matchIndexs.add(10);
			
			List<String> matchColumns = new ArrayList<String>();
			matchColumns.add("法人代码");
			matchColumns.add("企业名称");
			
			enterpriseDataService.matchingEnterpriseDataAndreateResultFileToExcel(
							"E:\\丽丽\\企业财务数据整理\\1998\\1998年企业财务数据整理结果.xlsx",
							"E:\\丽丽\\企业财务数据整理\\lily_enterprise_company.xlsx", 
							matchIndexs, "t_enterprise_data_1998", matchColumns, false, 10);
			
			if(log.isInfoEnabled())
				log.info("耗时............." + (System.currentTimeMillis() - now));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
