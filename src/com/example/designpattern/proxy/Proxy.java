package com.example.designpattern.proxy;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 并不是说实现类和代理类必须实现完全相同的接口；既然代理类只是在一定程度上代表那个需要它提交(referring)方法的类，这就已经满足了 Proxy模式的基本要求
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class Proxy implements ProxyBase {
	private ProxyBase implemention;

	public Proxy() {
		implemention = new Implementation();
	}

	@Override
	public void f() {
		implemention.f();
	}

	@Override
	public void g() {
		implemention.g();
	}

	@Override
	public void h() {
		implemention.h();
	}
}
