package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.spring.javabeans.Pet;

@Controller
@RequestMapping(value = "/modelAttributeExample")
@SessionAttributes("pet")
public class ModelAttributeExampleController {
	@ModelAttribute
	@RequestMapping(value = "/addPet", method = RequestMethod.GET)
	public Pet addPet(@RequestParam String petId) {
		Pet pet = new Pet();
		pet.setPetId(null == pet ? 0 : Integer.parseInt(petId));
		pet.setPetName("pig");
		System.out.println(pet);
		return pet;// 方法的返回值被加入到model中，名字为pet，当然我们也可以使用@ModelAttribute("myPet")自定义名字.
	}
	
	@ModelAttribute
	@RequestMapping(value = "/populateModel", method = RequestMethod.GET)
	public void populateModel(@RequestParam String petId, Model model) {
		Pet pet = new Pet();
		pet.setPetId(null == pet ? 0 : Integer.parseInt(petId));
		model.addAttribute(pet);
	}
	
	@RequestMapping(value = "/processSubmit/{petName}/{hobby}", method = RequestMethod.GET)
	public void processSubmit(@ModelAttribute Pet pet) {
		System.out.println(pet);// 先看看@ModelAttribute中是否存在，如果没有则实例化，如果有则根据参数名称覆盖
	}
	
	@RequestMapping(value = "/processSubmitWithBindingResult/{petName}", method = RequestMethod.GET)
	public void processSubmitWithBindingResult(@ModelAttribute("pet") Pet pet,BindingResult result, Model model) {
		ObjectError error = new ObjectError("NullPointException", "空指针了");
		result.addError(error);
		if(result.hasErrors()){
			System.out.println(result.getTarget());
		}
	}
}
