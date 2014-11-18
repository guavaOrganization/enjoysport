package com.example.concurrent;

import org.junit.Test;

public class TestThread {
	@Test
	public void theFirstThread() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				System.out.println("Running....");
			}
		};
		System.out.println("A thread is created");
		thread.start();
	}
}
