package com.example.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PairManager3 extends PairManager {
	private Lock lock = new ReentrantLock();

	@Override
	public void increment() {
		Pair temp;
		lock.lock();
		try {
			pair.incrementY();
			pair.incrementX();
			temp = getPair();// 这个在PairChecker校验的时候会出错
		} finally {
			lock.unlock();
		}
		store(temp);
	}
}
