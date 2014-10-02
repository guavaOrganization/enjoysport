package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/requestBodyExample")
public class RequestBodyExampleController {
	@RequestMapping(value = "/handleGet", method = RequestMethod.GET)
	public void handleGet(@RequestBody String body) {
		System.out.println(body);//如果前台发过来得请求方法是GET类型，此方法可以拦截，但是因为get类型的请求body部分没有，所有此部分为null
								 //如果发送过来的请求是Post，那么将返回 HTTP/1.1 405 Method Not Allowed
	}
	
	@RequestMapping(value = "/handlePost", method = RequestMethod.POST)
	public void handlePost(@RequestBody String body) {
		System.out.println(body);
	}
}
