package com.example.thread;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PrioritizedTaskProducer implements Runnable {
	private Random rand = new Random(47);
	private Queue<Runnable> queue;
	private ExecutorService executor;

	public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService executor) {
		this.queue = queue;
		this.executor = executor;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {// 加20个随机优先级的任务
			queue.add(new PrioritizedTask(rand.nextInt(10)));
			Thread.yield();
		}
		try {
			for (int i = 0; i < 10; i++) {// 加10个最高优先级的任务
				TimeUnit.MILLISECONDS.sleep(250);
				queue.add(new PrioritizedTask(10));
			}
			
			for (int i = 0; i < 10; i++) {
				queue.add(new PrioritizedTask(i));
			}
			
			queue.add(new PrioritizedTask.EndSentinel(executor));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished PrioritizedTaskProducer");
	}
}
