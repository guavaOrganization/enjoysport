package com.example.thread;

public class PairManager2 extends PairManager {

	@Override
	public void increment() {
		Pair temp;
		synchronized (this) {// 同步控制块
			pair.incrementX();
			pair.incrementY();
			temp = getPair();
		}
		store(temp);
	}

}
