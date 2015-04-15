package com.example.javalang;

import java.io.UnsupportedEncodingException;

public class StringTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String tempStr = "abc";
		String str = tempStr + "b";
		str = s(tempStr, str);
		System.out.println(System.getProperty("file.encoding"));
		byte[] bytes = "陈俊".getBytes(); // -->字符根据相关编码转换为二进制流
		for (byte b : bytes)
			System.out.println(b);
		// -->二进制流根据相关编码解码为字符
		System.out.println(new String(bytes, "UTF-8"));
		
		bytes = "陈俊".getBytes("GBK");
		for (byte b : bytes)
			System.out.println(b);
		System.out.println(new String(bytes, "GBK"));

		System.out.println("++++++++++++++");
		String name = "陈俊";
		byte[] b = name.getBytes("utf-8");
		String name_utf8 = new String(b, "utf-8");
		System.out.println("name_utf8 is " + name_utf8);
		System.out.println(name_utf8.getBytes("utf-8").length);
		System.out.println(name_utf8.toCharArray().length);
		
		String str1 = "\u7528\u6237\u767B\u5F55";//unicode编码
		System.out.println(str1); 
		System.out.println("\u6C49");
	}
	
	public static String s(String s, String k) {
		String temp = s + k; // 可以通过javap -c StringTest，看到此段逻辑，Java在实现的时候使用StringBuilder进行了字符串联
		temp += s + temp;
		return temp;
	}
}
