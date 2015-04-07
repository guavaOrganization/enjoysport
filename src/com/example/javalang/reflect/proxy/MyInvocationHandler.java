package com.example.javalang.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {
	private Object targetObj;

	public Object bind(Object targetObj) {
		this.targetObj = targetObj;
		return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), targetObj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(proxy.getClass());
		Object result = null;
		System.out.println("调用目标对象方法前....");
		result = method.invoke(targetObj, args);
		System.out.println("调用目标对象方法后....");
		return result;
	}
}
