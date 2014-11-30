package com.example.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OverriddingCar {
	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	private boolean waxOn = false;

	public void waxed() {
		lock.lock();
		try {
			waxOn = true;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public void buffed() {
		lock.lock();
		try {
			waxOn = false;
			condition.signalAll();// 在使用内建版本时，任务可以调用await(),single()或signalAll()之前，必须拥有这个锁
		} finally {// 确保释放锁
			lock.unlock();
		}
	}

	public void waitForWaxing() throws InterruptedException {
		lock.lock();
		try {
			while (waxOn == false)
				condition.await();
		} finally {
			lock.unlock();
		}
	}

	public void waitForBuffing() throws InterruptedException {
		lock.lock();
		try {
			while (waxOn == true)
				condition.await();
		} finally {
			lock.unlock();
		}
	}
}
