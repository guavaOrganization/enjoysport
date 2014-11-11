package com.example.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/retrieveUserNameById")
	public String retrieveUserNameById() throws Exception {
		declarativeTransactionManagementService.retrieveUserNameById(1);
		return "success";
	}

	@RequestMapping(value = "/retrieveUserById")
	public String retrieveUserById() throws Exception {
		declarativeTransactionManagementService.retrieveUserById(2);
		return "success";
	}
	
	@RequestMapping(value = "/retrieveAllUser")
	public String retrieveAllUser() throws Exception {
		declarativeTransactionManagementService.retrieveAllUser();
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
	
	@RequestMapping(value = "/retrieveUserNameByIdWithNamedParameterJdbcTemplate")
	public String retrieveUserNameByIdWithNamedParameterJdbcTemplate() throws Exception {
		declarativeTransactionManagementService.retrieveUserNameByIdWithNamedParameterJdbcTemplate(1);
		return "success";
	}
	
	@RequestMapping(value = "/retrieveUserNameByIdWithMap")
	public String retrieveUserNameByIdWithMap() throws Exception {
		declarativeTransactionManagementService.retrieveUserNameByIdWithMap(2);
		return "success";
	}
	
	@RequestMapping(value = "/retrieveUserNameByIdWithBeanProperty")
	public String retrieveUserNameByIdWithBeanProperty() throws Exception {
		declarativeTransactionManagementService.retrieveUserNameByIdWithBeanProperty(1);
		return "success";
	}
}