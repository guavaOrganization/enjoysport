package com.example.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	使用cglib动态代理
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class BookFacadeProxy implements MethodInterceptor {
	private Object target;
	
	public Object getInstance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);// 设置回调方法
		return enhancer.create();// 创建代理对象
	}
	
	@Override
	// 回调方法
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println(obj.getClass()); // obj是代理类
		System.out.println("事务开始...");
		proxy.invokeSuper(obj, args);
		System.out.println("事务结束...");
		return null;
	}
}
