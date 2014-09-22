package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/relativePath/owners/{ownerId}/")
public class RelativePathUriTemplateExampleController {
	// http://localhost:8080/guava/relativePath/owners/12/pets/321321.guava?myParam=myValue
	// 参数必须是"myParam=myValue"
	@RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, params = "myParam=myValue")
	public void findPet(@PathVariable String ownerId,
			@PathVariable String petId, Model model) {
		System.out.println("ownerId>>>" + ownerId + ">>>petId>>>" + petId);
	}

	@RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, headers = "myHeader=myValue")
	public void findPet2(@PathVariable String ownerId,
			@PathVariable String petId, Model model) {
		System.out.println("ownerId>>>" + ownerId + ">>>petId>>>" + petId);
	}
}
