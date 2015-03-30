package com.example.javalang.classloading;

public class ClassInitializationTest {
	public static void main(String[] args) {
		// Class cls = ClassInitialization.class; // 不会进行初始化
		try {
			// 会初始化ClassInitialization中的static描述数据
			Class cls = Class.forName("com.example.javalang.classloading.ClassInitialization");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
