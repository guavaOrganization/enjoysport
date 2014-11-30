package com.example.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
	static final int SIZE = 100;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(SIZE);// 构造一个用给定计数初始化的CountDownLatch。

		for (int i = 0; i < 10; i++) {
			executor.execute(new WaitingTask(latch));
		}
		
		for (int i = 0; i < SIZE; i++) {
			executor.execute(new TaskPortion(latch));
		}

		System.out.println("Launched all tasks");
		executor.shutdown();
	}
}
