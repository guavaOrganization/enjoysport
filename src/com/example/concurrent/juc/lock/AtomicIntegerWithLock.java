package com.example.concurrent.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicIntegerWithLock {
	private int value;
	private Lock lock = new ReentrantLock();

	public AtomicIntegerWithLock() {
		super();
	}

	public AtomicIntegerWithLock(int value) {
		this.value = value;
	}

	public final int get() {
		lock.lock();
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}

	public void setValue(int value) {
		lock.lock();
		try {
			this.value = value;
		} finally {
			lock.unlock();
		}
	}

	public int incrementAndGet() {
		lock.lock();
		try {
			return ++value;
		} finally {
			lock.lock();
		}
	}
}
