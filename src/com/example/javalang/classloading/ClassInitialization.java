package com.example.javalang.classloading;

public class ClassInitialization {
	public static int index = 0;
	static {
		System.out.println("static block...");
	}
	
	public static int j = incr();
	public static ClassInitialization classInitialization = new ClassInitialization();
	
	public int i = incr();
	{ // 构造块
		System.out.println("construction block....");
	}
			
	public static int incr() {
		System.out.println("current index's value is " + index + ",incr it");
		return ++index;
	}
	
	public ClassInitialization() {
		System.out.println("non-args Constructor");
	}
}
