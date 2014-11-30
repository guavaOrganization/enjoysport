package com.example.thread;

public class MultiLock {
	public synchronized void f1(int count) {
		if (count-- > 0) {
			System.out.println("f1() calling f2() with count " + count);
			f2(count);
		}
	}

	public synchronized void f2(int count) {
		if (count-- > 0) {
			System.out.println("f2() calling f1() with count " + count);
			f1(count);
		}
	}

	public static void main(String[] args) {
		final MultiLock lock = new MultiLock();
		new Thread() {
			public void run() {
				// 因此一个任务应该能够调用在同一个对象中的其他的synchronized方法，而这个任务已经持有锁了
				lock.f1(10);// 因为任务已经在第一个对f1()的调用中获得了MultiLock对象锁,因此同一个任务将在对f2()的调用中再次获得这个锁。
			};
		}.start();
	}
}
