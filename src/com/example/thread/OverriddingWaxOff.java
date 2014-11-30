package com.example.thread;

import java.util.concurrent.TimeUnit;

public class OverriddingWaxOff implements Runnable {
	private OverriddingCar car;

	public OverriddingWaxOff(OverriddingCar car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("Wax off!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax off task");
	}
}
