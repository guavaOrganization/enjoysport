package com.example.javalang;

public enum MyEnum {
	PING, SET, GET, QUIT, EXISTS;
	MyEnum() {
		System.out.println("MyEnum");
	}
	
	static{
		System.out.println("xxxxxxxxx");
	}
}
