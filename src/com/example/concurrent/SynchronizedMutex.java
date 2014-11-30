package com.example.concurrent;

public class SynchronizedMutex {
	private Thread curOwner = null;

	public synchronized void acquire() throws InterruptedException {
		if (Thread.interrupted())
			throw new InterruptedException();
		if (null != curOwner)
			wait();// 等待
		curOwner = Thread.currentThread();
	}

	public synchronized void release() {
		if (curOwner == Thread.currentThread()) {
			curOwner = null;
			notify();// 唤醒
		} else {
			throw new IllegalStateException("not owner of mutex");
		}
	}
}
