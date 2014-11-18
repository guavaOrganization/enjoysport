package com.example.concurrent.executorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BeeperControl {
	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	
	public void beepForAnHour() {
		final Runnable beeper = new Runnable() {
			@Override
			public void run() {
				System.out.println("beep");
			}
		};
		
		final ScheduledFuture<?> beeprHandle = executorService.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				beeprHandle.cancel(true);
			}
		}, 60 * 60, TimeUnit.SECONDS);
	}
}
