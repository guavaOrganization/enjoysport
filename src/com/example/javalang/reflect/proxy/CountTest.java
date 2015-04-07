package com.example.javalang.reflect.proxy;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Java代理之静态代理：由程序员或特定代码工具自动生成源代码,在对其编译
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class CountTest {
	public static void main(String[] args) {
		CountProxy proxy = new CountProxy(new CountImpl());
		proxy.queryCount();
		proxy.updateCount();
	}
}
