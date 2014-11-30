package com.example.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEvenGenerator extends IntGenerator {
	private int currenEvenValue = 0;
	private Lock lock = new ReentrantLock();// 可重入互斥锁

	@Override
	public int next() {
		lock.lock();
		try {// 使用lock()和unlock()方法在next()内部创建了临界资源。
			++currenEvenValue;
			Thread.yield();
			++currenEvenValue;
			return currenEvenValue;// return语句必须在try子句中出现，以确保unlock()不会过早发生，从而将数据暴露给了第二个任务
		} finally {
			lock.unlock();// 必须调用unlock()方法释放锁
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new MutexEvenGenerator());
	}
}
