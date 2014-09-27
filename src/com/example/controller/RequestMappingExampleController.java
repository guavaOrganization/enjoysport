package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/requestMappingExample")
public class RequestMappingExampleController {
	@RequestMapping(value = "/value")
	public void value() {
		System.out.println("RequestMappingExampleController>>>>value");
	}

	@RequestMapping(value = "/method", method = RequestMethod.GET)
	public void method() {
		System.out.println("RequestMappingExampleController>>>>method>>>>only accpte method get");
	}
}
