package com.example.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueThreadIdGenerator {
	private static final AtomicInteger uniqueId = new AtomicInteger(0);

	private static final ThreadLocal<Integer> uniqueNum = new ThreadLocal<Integer>() {
		/**
		 * 返回此线程局部变量的当前线程的“初始值”。线程第一次使用get()方法访问变量时将调用此方法，【但如果线程之前调用了set(T)方法，则不会对该线程再调用initialValue方法】。
		 * 通常，此方法对每个线程最多调用一次，【但如果在调用get()后又调用了remove()，则可能再次调用此方法】。 该实现返回 null；
		 * 如果程序员希望线程局部变量具有null以外的值，则必须为ThreadLocal创建子类，并重写此方法。通常将使用匿名内部类完成此操作。
		 */
		protected Integer initialValue() {
			return uniqueId.getAndIncrement();
		};
	};

	// 每个线程都保持对其线程局部变量副本的隐式引用，只要线程是活动的并且ThreadLocal实例是可访问的；
	// 在线程消失之后，其线程局部实例的所有副本都会被垃圾回收（除非存在对这些副本的其他引用）。
	public static int getCurrentThreadId() {
		return uniqueNum.get();
	}
}
