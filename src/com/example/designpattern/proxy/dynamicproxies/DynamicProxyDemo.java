package com.example.designpattern.proxy.dynamicproxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {
	public static void main(String[] args) {
		Foo prox = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class[] { Foo.class }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("InvocationHandler called:\n\tMethod = " + method);
				if (args != null) {
					for (int i = 0; i < args.length; i++)
						System.out.println("\t\t" + args[i]);
				}
				return null;
			}
		});
		prox.f("hello");
		prox.g(47);
		prox.h(47, "hello");
	}
}
