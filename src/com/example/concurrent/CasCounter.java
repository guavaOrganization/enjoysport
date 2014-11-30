package com.example.concurrent;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 基于CAS的计数器
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class CasCounter {
	private SimulatedCAS cas;
	
	public int getValue() {
		return cas.getValue();
	}
	
	public int increment() {
		int oldValue = cas.getValue();
		while (cas.compareAndSwap(oldValue, oldValue + 1) != oldValue)
			oldValue = cas.getValue();
		return oldValue + 1;
	}
}
