package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/pathVariableExample")
public class PathVariableExampleController {
	// 参数ownerId一定需要与@RequestMapping中的value中的{ownerId}保持一致，不然会出现"400 Bad Request"的返回码
	@RequestMapping(value = "/findOwner/{ownerId}/like", method = RequestMethod.GET)
	public String findOwner(@PathVariable String ownerId, Model model) {
		System.out.println("PathVariableExampleController>>>>findOwner>>>>ownerId:  " + ownerId);
		return "findOwner";
	}
	
	// 指定@PathVariable变量名名字
	@RequestMapping(value = "/specifyName/{ownerId}", method = RequestMethod.GET)
	public void specifyName(@PathVariable("ownerId") String specifyName, Model model) {
		System.out.println("PathVariableExampleController>>>>specifyName>>>>ownerId:  " + specifyName);
	}

	@RequestMapping(value="/anyNumberOf/{ownerId}/{petId}", method = RequestMethod.GET)
	public void anyNumberOf(@PathVariable("ownerId") String specifyName, @PathVariable String petId, Model model) {
		System.out.println("PathVariableExampleController>>>>anyNumberOf>>>>ownerId:  " + specifyName + "  petId：" + petId);
	}
	
	
//	@RequestMapping(value="/anyNumberOf/{ownerId}/{petId}", method = RequestMethod.GET)
//	public void pathVariableMap(@PathVariable("ownerId,petId") Map<String, String> map) {// 此例没有调通
//		System.out.println("pathVariableMap");
//		System.out.println(map);
//	}
	
	// 支持正则表达式
	@RequestMapping("/regularSupport/{version:\\d\\d}-{extension:[a-z]}")
	public void regularSupport(@PathVariable String version,@PathVariable String extension) {
		System.out.println("version is : " + version);
		System.out.println("extension is : " + extension);
	}
}
