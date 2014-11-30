package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class NonblockingCounter {
	private AtomicInteger value;
	public NonblockingCounter(AtomicInteger value) {
		this.value = value;
	}

	public int getValue() {
		return value.get();
	}

	public void setValue(int i) {
		int v;
		do {
			v = value.get();
		} while (!value.compareAndSet(v, i));// 使用CAS方法
	}

	public static void main(String[] args) {
		NonblockingCounter counter = new NonblockingCounter(new AtomicInteger());
		counter.setValue(4);
		System.out.println(counter.getValue());
	}
}
