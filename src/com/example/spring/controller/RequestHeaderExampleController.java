package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.javabeans.Human;

@Controller
@RequestMapping(value = "/requestHeaderExample")
public class RequestHeaderExampleController {
	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	public @ResponseBody Human handle(@RequestBody Human human,
			@RequestHeader("Accept") String[] accept,
			@RequestHeader("Accept-Language") String acceptLang,
			@RequestHeader("Accept-Encoding") String acceptEnc,
			@RequestHeader("User-Agent") String userAgent) {
		for (int i = 0; null != accept && i < accept.length; i++) {
			System.out.println("accept >>>> " + accept[i]);
		}
		System.out.println("acceptLang >>>> " + acceptLang);
		System.out.println("acceptEnc >>>> " + acceptEnc);
		System.out.println("userAgent >>>> " + userAgent);
		human.setHobby("篮球");
		return human;
	}
}
