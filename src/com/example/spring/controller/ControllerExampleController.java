package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/controllerExample")
// 类级别
public class ControllerExampleController {
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	// 方法级别
	public void test1(@ModelAttribute(value = "mcfly") String test3) {
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
