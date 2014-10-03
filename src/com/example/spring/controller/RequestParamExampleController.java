package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/requestParamExample")
public class RequestParamExampleController {
	@RequestMapping(value = "/setupForm", method = RequestMethod.GET)
	public void setupForm(@RequestParam("petId") int petId, ModelMap modelMap) {
		modelMap.put("petId", petId);
		System.out.println("PetId is : " + petId);
	}

	@RequestMapping(value = "/notRequired", method = RequestMethod.GET)
	public void notRequired(@RequestParam(value = "petId", required = false) Integer petId) {
		System.out.println(petId);// RequestParam的required属性默认是true,如果请求中没有对应参数，将无法映射；如果设置为false,需要保证参数类型是Object，而不能是简单类型，因为为false的情况下，变量默认为null
	}
}
