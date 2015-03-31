package com.example.javalang.reflect;

public class ClassInfo implements InterfaceInfo {
	static {
		System.out.println("static block...");
	}

	public ClassInfo() {
		System.out.println("non-args Constructor...");
	}
}