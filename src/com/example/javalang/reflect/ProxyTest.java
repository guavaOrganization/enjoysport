package com.example.javalang.reflect;

import java.lang.reflect.Proxy;

public class ProxyTest {
	public static void main(String[] args) {
		ProxyInterface proxyClass = (ProxyInterface) Proxy.newProxyInstance(
				ProxyClass.class.getClassLoader(),
				new Class[] { ProxyInterface.class }, new MyInvocationHandler());
		proxyClass.sayHello();
	}
}
