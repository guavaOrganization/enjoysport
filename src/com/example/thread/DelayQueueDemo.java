package com.example.thread;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayQueueDemo {
	public static void main(String[] args) {
		Random rand = new Random(47);
		ExecutorService executor = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		for (int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(rand.nextInt(5000)));
		}
		queue.add(new EndSentinel(5000, executor));
		executor.execute(new DelayedTaskConsumer(queue));
	}
}
