package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SemaphoreDemo {
	final static int SIZE = 25;

	public static void main(String[] args) throws InterruptedException {
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);

		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++) {
			executor.execute(new CheckOutTask<Fat>(pool));
		}

		System.out.println("All CheckOutTask created");
		List<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < SIZE; i++) {
			Fat f = pool.checkOut();
			System.out.println(i + " ：mian() thread check out ");
			f.operation();
			list.add(f);
		}
	}
}
