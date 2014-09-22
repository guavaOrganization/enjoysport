package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.javabeans.Pet;

// URI Template Patterns
@Controller
@RequestMapping(value = "/pathVariable/")
public class PathVariableExampleController {
	// 测试URL：http://localhost:8080/guava/owners/1234.guava
	@RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.GET)
	public String findOwner(@PathVariable String ownerId, Model model) {
		System.out.println(ownerId);
		return "displayOwner";
	}

	// http://localhost:8080/guava/theOwners/1234sss.guava
	@RequestMapping(value = "/theOwners/{ownerId}", method = RequestMethod.GET)
	public String findTheOwner(@PathVariable("ownerId") String theOwner,
			Model model) {
		System.out.println("theOwner~~" + theOwner);
		return "displayOwner";
	}

	// http://localhost:8080/guava/owners/1234sss/pets/xsadsads.guava
	@RequestMapping(value = "/owners/{ownerId}/pets/{petId}", method = RequestMethod.GET)
	public String findPet(@PathVariable String ownerId,
			@PathVariable String petId, Model model) {
		System.out.println("ownerId::" + ownerId);
		System.out.println("petId::" + petId);
		return "findPet";
	}

	// consumes：指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
	@RequestMapping(value = "/pets", method = RequestMethod.POST, consumes = "application/json")
	public void addPet(@RequestBody Pet pet, Model model) {
	}

	// produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
	@RequestMapping(value = "/pets", method = RequestMethod.GET, produces = "application/json")
	public void getPet(@PathVariable String petId, Model model) {

	}
}
