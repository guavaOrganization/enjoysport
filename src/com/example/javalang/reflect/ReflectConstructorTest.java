package com.example.javalang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ReflectConstructorTest {

	@Test
	public void test() {
		try {
			Class<?> cls = Class
					.forName("com.example.javalang.reflect.ReflectConstructor");
			Constructor<?>[] constructors = cls.getDeclaredConstructors();
			for (Constructor<?> constructor : constructors) {
				System.out.println("+++++++++++++++++++++++++++++");
				System.out.println(constructor.getName());
				System.out.println("getModifiers() : " + constructor.getModifiers());
				System.out.println(constructor.toString());
				System.out.println("isVarArgs() : " + constructor.isVarArgs());
				System.out.println("+++++++++++++++++++++++++++++");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main1(String[] args) {
		try {
			Class<?> cls = Class
					.forName("com.example.javalang.reflect.ReflectConstructor");
			System.out.println(cls.getName());

			System.out.println("++++++++++++++getConstructors()++++++++++++++");
			// 返回一个包含某些 Constructor 对象的数组，这些对象反映此 Class 对象所表示的类的所有公共构造方法
			// 如果该类没有公共构造方法，或者该类是一个数组类，或者该类反映一个基本类型或 void，则返回一个长度为 0 的数组。
			Constructor<?>[] constructors = cls.getConstructors();
			for (Constructor<?> constructor : constructors) {
				System.out.println(constructor.toString());
				System.out.println(constructor.toGenericString());
				// 返回Class对象，该对象表示声明由此Constructor对象表示的构造方法的类。
				System.out.println(constructor.getDeclaringClass());
			}
			System.out.println("++++++++++++++getConstructor()++++++++++++++");
			Constructor<?> constructor = cls.getConstructor(int.class);
			System.out.println(constructor.toString());
			// 以整数形式返回此 Constructor 对象所表示构造方法的 Java 语言修饰符。应该使用 Modifier
			// 类对这些修饰符进行解码。
			System.out.println("getModifiers : " + constructor.getModifiers());
			System.out.println(Modifier.isPublic(constructor.getModifiers()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
