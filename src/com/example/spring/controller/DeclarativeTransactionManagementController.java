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
	public String analyzeDeclarativeTransactionManagementBaseProxy() throws Exception{
		declarativeTransactionManagementService.analyzeDeclarativeTransactionManagementBaseProxy();
		return "success";
	}
	
	@RequestMapping(value = "/createProxy")
	public String createDeclarativeTransactionManagementBaseProxy() throws Exception{
		declarativeTransactionManagementService.createDeclarativeTransactionManagementBaseProxy();
		return "success";

	}
}
