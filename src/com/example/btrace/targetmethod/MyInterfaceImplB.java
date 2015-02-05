package com.example.btrace.targetmethod;

public class MyInterfaceImplB implements MyInterface {
	@Override
	public void test() {
		System.out.println("test() from MyInterfaceImplB");
	}

	@Override
	public String call(String text) {
		return null;
	}
}
