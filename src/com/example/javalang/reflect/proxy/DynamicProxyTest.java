package com.example.javalang.reflect.proxy;


public class DynamicProxyTest {
	public static void main(String[] args) {
		MyInvocationHandler handler = new MyInvocationHandler();
		ProxyInterface proxyInterface = (ProxyInterface) handler.bind(new ProxyClass());
		proxyInterface.sayHello();
	}
}
