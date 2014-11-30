package com.example.thread;

import java.util.concurrent.TimeUnit;

public class OverriddingWaxOn implements Runnable {
	private OverriddingCar car;

	public OverriddingWaxOn(OverriddingCar car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Wax on!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending wax on task");
	}
}
