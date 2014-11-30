package com.example.designpattern.abstractfactory;

public class TelecomProduct implements Product {
	@Override
	public void sayHello() {
		System.out.println("Hello,This is Telecom Product");
	}
}
