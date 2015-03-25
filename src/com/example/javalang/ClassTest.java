package com.example.javalang;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * Class 类的实例表示正在运行的 Java 应用程序中的类和接口。
 * 枚举是一种类，注释是一种接口。
 * 每个数组属于被映射为 Class 对象的一个类，所有具有相同元素类型和维数的数组都共享该 Class 对象。
 * 
 * 基本的 Java 类型（boolean、byte、char、short、int、long、float 和 double）和关键字 void 也表示为 Class 对象。
 * 
 * Class 没有公共构造方法。Class 对象是在 【加载类】时由【Java虚拟机】以及【通过调用类加载器中的 defineClass方法自动构造】的。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ClassTest {
//	@Test
	public void test_toString() {
		// 将对象转换为字符串。字符串的表示形式为字符串 "class" 或 "interface" 后面紧跟一个空格，然后是该类的完全限定名，它具有getName 返回的那种格式。
		System.out.println(MyClass.class.toString());
		System.out.println(MyEnum.class.toString());
		
		System.out.println(MyInterface.class.toString());
		System.out.println(MyAnnotation.class.toString());
		
		// 如果此 Class 对象表示一个基本类型，则此方法返回该基本类型的名称
		System.out.println(byte.class.toString());
		// 如果该 Class 对象表示 void，则此方法返回 "void"。
		System.out.println(void.class.toString());
	}
	
//	@Test
	public void test_forName() {
		try {
			// initialize 为true将会执行MyClass中的static部分
			Class myClassCls = Class.forName("com.example.javalang.MyClass", false, this.getClass().getClassLoader());
			System.out.println(myClassCls.toString());
			
			Class myClassClazz = Class.forName("com.example.javalang.MyClass");
			System.out.println(myClassClazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test_newInstance(){
		try {
			Class myClass = Class.forName("com.example.javalang.MyClass", false, this.getClass().getClassLoader());
			MyClass myClassInstance = (MyClass) myClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test_isInstance() {
		MyClass myClass = new MyClass();
		try {
			Class myClazz = Class.forName("com.example.javalang.MyClass", true, this.getClass().getClassLoader());
			System.out.println(myClazz.isInstance(myClass));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_isSynthetic() {
		// 此示例并没有找到完美的答案
		System.out.println(this.getClass().isSynthetic());
	}
}
