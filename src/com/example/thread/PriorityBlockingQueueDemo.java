package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
		executor.execute(new PrioritizedTaskProducer(queue, executor));
		executor.execute(new PrioritizedTaskConsumer(queue));

	}
}
