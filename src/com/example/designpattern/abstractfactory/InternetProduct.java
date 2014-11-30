package com.example.designpattern.abstractfactory;

public class InternetProduct implements Product {
	@Override
	public void sayHello() {
		System.out.println("Hello, this is Internet Product...");
	}
}
