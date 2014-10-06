package com.example.beetl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/beetlExample")
public class BeetlExampleController {
	@RequestMapping(value = "/helloWorld")
	public String helloWorld(Model model) {
		model.addAttribute("name", "陈俊");
		model.addAttribute("title", "Beetl Spring 融合");
		return "beetl/helloWorld";
	}
}
