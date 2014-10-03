package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.javabeans.Human;

@Controller
@RequestMapping("/responseBodyExample")
public class ResponseBodyExampleController {
	@RequestMapping(value = "/helloWorld", method = RequestMethod.POST)
	public @ResponseBody Human helloWorld(@RequestBody Human human) {
		human.setHobby("篮球");
		System.out.println(human);
		return human;
	}
}
