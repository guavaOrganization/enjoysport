package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/controllerExample")
// 类级别
public class ControllerExampleController {
	@RequestMapping(value = "/test1", method = RequestMethod.GET)//方法级别
	public void test1() {
		System.out.println("test11");
	}
}
