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
public class StaticProxyTest {
	public static void main(String[] args) {
		// 观察代码可以发现每一个代理类只能为一个接口服务，这样一来程序开发中必然会产生过多的代理，而且，所有的代理操作除了调用的方法不一样之外，其他的操作都一样，则此时肯定是重复代码。
		// 解决这一问题最好的做法是可以通过一个代理类完成全部的代理功能，那么此时就必须使用动态代理完成。 
		CountProxy proxy = new CountProxy(new CountImpl());
		proxy.queryCount();
		proxy.updateCount();
		
	}
}
