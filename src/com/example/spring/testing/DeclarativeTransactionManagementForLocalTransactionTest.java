package com.example.spring.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.spring.service.interfaces.IDeclarativeTransactionManagementService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:DeclarativeTransactionManagementForLocalTransactionContext.xml")
public class DeclarativeTransactionManagementForLocalTransactionTest {
	@Autowired
	@Qualifier("com.example.spring.service.impls.DeclarativeTransactionManagementServiceImpl")
	private IDeclarativeTransactionManagementService service;

	@Test
	public void createUser() throws Exception {
		service.createUser();
	}
}
