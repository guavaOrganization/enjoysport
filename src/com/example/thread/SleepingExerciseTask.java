package com.example.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingExerciseTask implements Runnable {
	private int index;

	public SleepingExerciseTask(int index) {
		this.index = index;
	}

	@Override
	public void run() {
		int i = 0;
		do {
			Random random = new Random();
			i = random.nextInt(11);
		} while (i <= 0);
		try {
			TimeUnit.SECONDS.sleep(i);
			System.out.println("我的编号是：" + index + "，Sleeping Time is " + i + " 秒");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			executor.execute(new SleepingExerciseTask(i));
		}
		executor.shutdown();
	}
}
