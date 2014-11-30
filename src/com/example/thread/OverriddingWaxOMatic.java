package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OverriddingWaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		OverriddingCar car = new OverriddingCar();

		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new OverriddingWaxOn(car));
		executor.execute(new OverriddingWaxOff(car));

		TimeUnit.SECONDS.sleep(5);
		executor.shutdownNow();
	}
}
