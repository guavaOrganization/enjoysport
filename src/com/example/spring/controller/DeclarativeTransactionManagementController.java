package com.example.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring.javabeans.TestBitUser;
import com.example.spring.service.interfaces.IDeclarativeTransactionManagementService;

@Controller
@RequestMapping(value = "/DeclarativeTransactionManagement")
public class DeclarativeTransactionManagementController {
	@Autowired
	@Qualifier("com.example.spring.service.impls.DeclarativeTransactionManagementServiceImpl")
	private IDeclarativeTransactionManagementService declarativeTransactionManagementService;

	@RequestMapping(value = "/analyzeProxy")
	public String analyzeDeclarativeTransactionManagementBaseProxy() throws Exception {
		declarativeTransactionManagementService.analyzeDeclarativeTransactionManagementBaseProxy();
		return "success";
	}

	@RequestMapping(value = "/createProxy")
	public String createDeclarativeTransactionManagementBaseProxy() throws Exception {
		declarativeTransactionManagementService.createDeclarativeTransactionManagementBaseProxy();
		return "success";

	}
	
	@RequestMapping(value = "/countTestBitTotal")
	public String countTestBitTotal() throws Exception {
		declarativeTransactionManagementService.countTestBitTotal();
		return "success";
	}
	
	@RequestMapping(value = "/createUser")
	public String createUser() throws Exception {
		declarativeTransactionManagementService.createUser();
		return "success";
	}
	
	@RequestMapping(value = "/readUserNameById")
	public String readUserNameById() throws Exception {
		declarativeTransactionManagementService.readUserNameById(1);
		return "success";
	}

	@RequestMapping(value = "/readUserById")
	public String readUserById() throws Exception {
		declarativeTransactionManagementService.readUserById(2);
		return "success";
	}
	
	@RequestMapping(value = "/readAllUser")
	public String readAllUser() throws Exception {
		declarativeTransactionManagementService.readAllUser();
		return "success";
	}
	
	@RequestMapping(value = "/updateUser")
	public String updateUser() throws Exception {
		declarativeTransactionManagementService.updateUser(3, "忍者神龟");
		return "success";
	}
	
	@RequestMapping(value = "/deleteUserById")
	public String deleteUserById() throws Exception {
		declarativeTransactionManagementService.deleteUserById(3);
		return "success";
	}
	
	@RequestMapping(value = "/readUserNameByIdWithNamedParameterJdbcTemplate")
	public String readUserNameByIdWithNamedParameterJdbcTemplate() throws Exception {
		declarativeTransactionManagementService.readUserNameByIdWithNamedParameterJdbcTemplate(1);
		return "success";
	}
	
	@RequestMapping(value = "/readUserNameByIdWithMap")
	public String readUserNameByIdWithMap() throws Exception {
		declarativeTransactionManagementService.readUserNameByIdWithMap(2);
		return "success";
	}
	
	@RequestMapping(value = "/readUserNameByIdWithBeanProperty")
	public String readUserNameByIdWithBeanProperty() throws Exception {
		declarativeTransactionManagementService.readUserNameByIdWithBeanProperty(1);
		return "success";
	}
	
	@RequestMapping(value = "/updateUserByBatch")
	public String updateUserByBatch() throws Exception {
		TestBitUser bitUser = new TestBitUser();
		bitUser.setId(1);
		bitUser.setName("八两俊");
		
		TestBitUser user = new TestBitUser();
		user.setId(2);
		user.setName("猪古丽");
		List<TestBitUser> list = new ArrayList<TestBitUser>();
		list.add(bitUser);
		list.add(user);
		declarativeTransactionManagementService.updateUser(list);
		return "success";
	}
	
	@RequestMapping(value = "/updateUserByBatchWithNamedParameterJdbcTemplate")
	public String updateUserByBatchWithNamedParameterJdbcTemplate() throws Exception {
		TestBitUser bitUser = new TestBitUser();
		bitUser.setId(1);
		bitUser.setName("陈俊");
		
		TestBitUser user = new TestBitUser();
		user.setId(2);
		user.setName("吴丽");
		List<TestBitUser> list = new ArrayList<TestBitUser>();
		list.add(bitUser);
		list.add(user);
		declarativeTransactionManagementService.updateUserWithNamedParameterJdbcTemplate(list);
		return "success";
	}
	
	
	@RequestMapping(value = "/createUserWithSimpleJdbcInsert")
	public String createUserWithSimpleJdbcInsert() throws Exception {
		declarativeTransactionManagementService.createUserWithSimpleJdbcInsert();
		return "success";
	}
}