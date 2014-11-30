package com.example.thread;

public class PairManager1 extends PairManager {
	@Override
	public synchronized void increment() {// synchronized关键字不属于方法特征签名的组成部分，所以可以在覆盖方法的时候加上去
		pair.incrementX();
		pair.incrementY();
		store(getPair());
	}
}
