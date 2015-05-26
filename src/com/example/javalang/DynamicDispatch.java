package com.example.javalang;

/**
 * 
 * <p>
 * 	方法动态分派演示
 * </p>
 */
public class DynamicDispatch {
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		man.sayHello();
		woman.sayHello();
		man = new Woman();
		man.sayHello();
	}
}
