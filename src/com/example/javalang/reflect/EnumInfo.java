package com.example.javalang.reflect;

public enum EnumInfo {
	PING, SET, GET, QUIT, EXISTS;
	EnumInfo() {
		System.out.println("MyEnum");
	}
	
	static{
		System.out.println("xxxxxxxxx");
	}
}
