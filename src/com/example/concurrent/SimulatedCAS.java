package com.example.concurrent;
/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 处理器对CAS(比较并交换)模拟实现，此类有点类似于AtomicInteger
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class SimulatedCAS {
	private int value;
	public synchronized int getValue(){
		return value;
	}

	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		int oldValue = value;
		if (oldValue == expectedValue) {
			this.value = newValue;
		}
		return oldValue;
	}

}
