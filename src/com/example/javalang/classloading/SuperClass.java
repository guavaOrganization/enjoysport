package com.example.javalang.classloading;

/**
 * 
 * <p>
 * 	被动使用类字段演示一：
 * 	通过子类引用父类的静态字段，不会导致子类初始化
 * </p>
 * 
 * @since
 */
public class SuperClass {
	static {
		System.out.println("SuperClass init");
	}
	
	public static int value = 123;

	public SuperClass() {
		System.out.println("SuperClass()");
	}
}
