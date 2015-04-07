package com.example.javalang.reflect.proxy;

public class CountImpl implements Count {
	@Override
	public void queryCount() {
		System.out.println("CountImpl method : queryCount()");
	}

	@Override
	public void updateCount() {
		System.out.println("CountImpl method : updateCount()");
	}
}
