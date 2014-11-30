package com.example.thread;

import java.util.concurrent.ExecutorService;

public class EndSentinel extends DelayedTask {
	private ExecutorService executor;

	public EndSentinel(int delat, ExecutorService executor) {
		super(delat);
		this.executor = executor;
	}

	@Override
	public void run() {
		for (DelayedTask task : sequence) {
			System.out.println(task.summary() + " ");
		}
		System.out.println();
		System.out.println(this + " Calling shutDownNow()");
		executor.shutdownNow();
	}
}
