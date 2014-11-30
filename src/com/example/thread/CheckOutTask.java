package com.example.thread;

import java.util.concurrent.TimeUnit;

public class CheckOutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;

	public CheckOutTask(Pool<T> pool) {
		this.pool = pool;
	}

	@Override
	public void run() {
		try {
			T item = pool.checkOut();
			System.out.println(this + " check out " + item);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + " check in " + item);
			pool.checkIn(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "CheckTask " + id + " ";
	}
}
