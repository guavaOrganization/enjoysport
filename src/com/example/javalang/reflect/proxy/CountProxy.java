package com.example.javalang.reflect.proxy;

public class CountProxy implements Count {
	private Count count;

	public CountProxy(Count count) {
		this.count = count;
	}

	@Override
	public void queryCount() {
		System.out.println("queryCount() ~~ before");
		count.queryCount();
		System.out.println("queryCount() ~~ after");
	}

	@Override
	public void updateCount() {
		System.out.println("updateCount() ~~ before");
		count.updateCount();
		System.out.println("updateCount() ~~ after");
	}
}
