package com.example.beetl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/beetlExampleController")
public class BeetlExampleController {

	@RequestMapping(value = "/testBeetlTag")
	public String testBeetlTag(ModelMap modelMap) {
		modelMap.put("name", "陈俊");
		modelMap.put("title", "beetl自定义标签");
		return "beetl/helloWorld";
	}
}
