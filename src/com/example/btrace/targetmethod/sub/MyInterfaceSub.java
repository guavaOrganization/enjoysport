package com.example.btrace.targetmethod.sub;

import com.example.btrace.targetmethod.MyInterface;

public class MyInterfaceSub implements MyInterface {
	@Override
	public void test() {
		System.out.println("test() from MyInterfaceSub");
	}

	public String call(String text) {
		return "MyInterfaceSub.call的返回值为：" + text;
	}
}
