package com.example.thread;

import java.util.concurrent.TimeUnit;

public class WaxOn implements Runnable {
	private Car car;

	public WaxOn(Car car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Wax on!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();// 涂鸦
				car.waitForBuffing();// 等待抛光
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax On task");
	}
}
