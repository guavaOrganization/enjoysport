package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		Car car = new Car();
		executor.execute(new WaxOn(car));
		executor.execute(new WaxOff(car));
		TimeUnit.SECONDS.sleep(5);
		executor.shutdownNow();
	}
}
