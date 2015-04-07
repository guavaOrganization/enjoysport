package com.example.javalang.reflect;

import java.util.Date;

public class ClassInfo extends SuperClassInfo<String> implements InterfaceClassInfo<Date>, InterfaceInfo {
	static {
		System.out.println("static block...");
	}

	public ClassInfo() {
		System.out.println("non-args Constructor...");
	}
}