package com.example.btrace.targetmethod;

public class MyInterfaceImplA implements MyInterface {
	public void test() {
		System.out.println("test() from MyInterfaceImplA");
	};
	
	public String call(String text) {
		TestLocationCall call = new TestLocationCall();
		return "MyInterfaceSub.call的返回值为：[" + call.call("calling TestLocationCall call,from MyInterfaceImplA(call)....") + "]";
	}
}
