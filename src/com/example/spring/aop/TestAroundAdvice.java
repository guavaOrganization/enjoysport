package com.example.spring.aop;

public class TestAroundAdvice {
	public String newUser(String name,int age){
		System.out.println("name : " + name + ", age : " + age);
		return name;
	}
}
