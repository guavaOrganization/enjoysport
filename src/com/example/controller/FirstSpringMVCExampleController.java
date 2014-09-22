package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.javabeans.BindParamBean;

@Controller
@RequestMapping(value="/first/")
public class FirstSpringMVCExampleController {
	@RequestMapping(value = "/hello.guava")
	public String hello(HttpServletRequest request, ModelMap model) {
        model.addAttribute("message", "hello");  
		return "hello";
	}
	
	@RequestMapping(value="/bindParam.guava")
	public void bindParam(BindParamBean param){
		System.out.println(param);
	}
}
