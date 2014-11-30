package com.example.thread;

import java.util.concurrent.TimeUnit;

public class WaxOff implements Runnable {
	private Car car;

	public WaxOff(Car car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();// 等待涂鸦
				System.out.println("Wax off");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();// 抛光
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax off task");
	}
}
