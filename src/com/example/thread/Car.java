package com.example.thread;

public class Car {
	private boolean waxOn = false;// true表示已涂鸦

	public synchronized void waxed() {// 涂鸦
		waxOn = true;
		notifyAll();
	}

	public synchronized void buffed() {// 抛光
		waxOn = false;
		notifyAll();
	}

	public synchronized void waitForWaxing() throws InterruptedException {// 等待涂鸦
		while (waxOn == false)
			wait();
	}

	public synchronized void waitForBuffing() throws InterruptedException {// 等待抛光
		while (waxOn)
			wait();
	}
}
