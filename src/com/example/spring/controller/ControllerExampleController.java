package com.example.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.spring.service.interfaces.IExampleService;
import com.example.spring.service.interfaces.IMcflyService;

@Controller
@RequestMapping(value = "/controllerExample")
// 类级别
public class ControllerExampleController {
	@Autowired
	@Qualifier("com.example.spring.service.impls.ExampleServiceImpl")
	private IExampleService exampleService;

	@Autowired
	@Qualifier("com.example.spring.service.impls.McflyServiceImpl")
	private IMcflyService mcflyService;

	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	// 方法级别
	public void test1(@ModelAttribute(value = "mcfly") String test3) {
		exampleService.x();
		System.out.println(test3);
	}

	@InitBinder
	public void test2() {

	}

	@ModelAttribute(value = "mcfly")
	public String test3() {
		return "test3";
	}
}
