package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/requestMappingExample")
public class RequestMappingExampleController {
	// 此例旨在说明value属性的使用方法
	// 指定请求的实际地址，指定的地址可以是URI Template 模式
	@RequestMapping(value = "/value")
	public void value() {
		System.out.println("RequestMappingExampleController>>>>value");
	}

	// 此例旨在说明method属性的使用方法
	// 指定请求的method类型， GET、POST、PUT、DELETE等
	@RequestMapping(value = "/method", method = RequestMethod.GET)
	public void method() {
		System.out.println("RequestMappingExampleController>>>>method>>>>only accpte method get");
	}

	// 请求参数中必须包含name和jobnumber参数
	@RequestMapping(value = "/params", method = RequestMethod.GET, params = { "name", "jobnumber" })
	public void params(){
		System.out.println("RequestMappingExampleController>>>>params");
	}
	
	// 请求头中必须包含Content-Type=application/json
	// 会继承类级别的对应配置
	@RequestMapping(value = "/headers", method = RequestMethod.GET, headers = {"Content-Type=application/json" })
	public void headers() { // 请求头中必须包含Content-Type和Accept
		System.out.println("RequestMappingExampleController>>>>headers");
	}
	
	// 请求头中的Content-Type可以是application/xml，也可以是application/json。
	// 此时方法级别会覆盖类级别的对应配置
	@RequestMapping(value = "/consumes", method = RequestMethod.GET, consumes = {"application/xml", "application/json" })
	public void consumes() {
		System.out.println("RequestMappingExampleController>>>>consumes");
	}
	
	// 请求头中的Accept可以是application/json，也可以是application/json。
	// 此时方法级别会覆盖类级别的对应配置
	// 当你有如下Accept头：
	// 1、Accept：text/html,application/xml,application/json
	// 将按照如下顺序进行produces的匹配  text/html application/xml  application/json
	// 2、Accept：application/xml;q=0.5,application/json;q=0.9,text/html
	// 将按照如下顺序进行produces的匹配  text/html application/json  application/xml
	// q参数为媒体类型的质量因子，越大则优先权越高(从0到1)
	// 3、Accept：*/*,text/*,text/html
	// 将按照如下顺序进行produces的匹配  text/html  text/*  */*
	@RequestMapping(value = "/produces", method = RequestMethod.GET, produces = { "application/json" })
	public void produces(){
		System.out.println("RequestMappingExampleController>>>>produces");
	}
}
