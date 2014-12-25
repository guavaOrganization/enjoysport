package com.example.lily.test;

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
	@Autowired
	@Qualifier("com.example.lily.service.impls.EnterpriseDataServiceImpl")
	IEnterpriseDataService enterpriseDataService;

	@Test
	public void testQueryByLegalPersonCode() {
		try {
			enterpriseDataService
					.matchingEnterpriseDataAndCreateResultWithExcel(
							"E:\\丽丽\\lily_enterprise_data_1998\\lily_enterprise_data_1998.xlsx",
							9, "t_enterprise_data_1998", "法人代表", true, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
