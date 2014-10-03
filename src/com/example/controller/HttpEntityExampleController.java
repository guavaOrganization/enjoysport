package com.example.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/httpEntityExample")
public class HttpEntityExampleController { // 此例没有测试通过，出现400错误
	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) {
		String requestHeader = requestEntity.getHeaders().getFirst("Content-Type");
		System.out.println("requestHeader is " + requestHeader);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
	}
}
