package com.example.spring.service.impls;

import org.springframework.stereotype.Service;

import com.example.spring.service.interfaces.IExampleService;

@Service("com.example.spring.service.impls.ExampleServiceImpl")
public class ExampleServiceImpl implements IExampleService {
	public void x() {
		System.out.println("zzzzzzzzzzzz");
		System.out.println("zzzzzzzzzzzzzzzzz");
	}
}
