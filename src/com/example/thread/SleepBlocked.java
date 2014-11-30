package com.example.thread;

import java.util.concurrent.TimeUnit;

public class SleepBlocked implements Runnable {// 通过Thread.sleep()阻塞线程
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(50);// 可中断的阻塞
		} catch (InterruptedException e) {
			System.out.println("InterruptedException....");
		}
		System.out.println("Exiting SleepBlocked.run()");
	}
}
