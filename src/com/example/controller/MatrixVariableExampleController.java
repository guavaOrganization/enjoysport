package com.example.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/matrixVariableExample")
public class MatrixVariableExampleController {
	@RequestMapping(value = "/matrixVariable/{name}", method = RequestMethod.GET)
	public String matrixVariable(@PathVariable String name, @MatrixVariable String hobby) {
		System.out.println("name is : " + name);
		System.out.println("hobby is : " + hobby);
		return "matrixVariable";
	}
	
	@RequestMapping(value = "/multiMatrixVariable/{chenjun}/{zengdetian}", method = RequestMethod.GET)
	public void multiMatrixVariable(
			@MatrixVariable(value = "hobby", pathVar = "chenjun") String hobby1,
			@MatrixVariable(value = "hobby", pathVar = "zengdetian") String hobby2) {
		System.out.println("chenjun hobby is : " + hobby1);
		System.out.println("zengdetian hobby is : " + hobby2);
	}

	@RequestMapping(value = "/defaultMatrixVariable/{name}", method = RequestMethod.GET)
	public void defaultMatrixVariable(@MatrixVariable(required = false, defaultValue = "basketball") String hobby) {
		System.out.println("the default hobby is " + hobby);
	}
	
	@RequestMapping(value = "/mapMatrixVariable/{chenjun}/{zengdetian}", method = RequestMethod.GET)
	public void mapMatrixVariable(@MatrixVariable Map<String, String> hobbies,
			@MatrixVariable(pathVar = "chenjun") Map<String, String> cjHobbies) {
		System.out.println("hobbies : " + hobbies == null ? "" : hobbies.toString());
		System.out.println("cjHobbies : " + cjHobbies == null ? "" : cjHobbies.toString());
	}
}
