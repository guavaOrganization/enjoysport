package com.example.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalVariableHolder {
	static class Accessor implements Runnable {
		private final int id;

		public Accessor(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				ThreadLocalVariableHolder.increment();
				System.out.println(this);
				Thread.yield();
			}
		}

		@Override
		public String toString() {
			return "#" + id + ":" + ThreadLocalVariableHolder.get();
		}
	}

	// 通常当作静态域存储。在创建ThreadLocal时，你只能通过get()和set()方法来访问该对象的内容
	// 每个单独的线程都被分配了自己的存储，因为它们每个都需要跟踪自己的计数值，即便只有一个ThreadLocalVariableHolder对象
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		private Random rand = new Random(47);
		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}
	};

	public static void increment() {
		// get()方法将返回与其线程相关联的对象的副本
		// set()会讲参数插入到为其线程存储的对象中
		value.set(value.get() + 1);
	}

	public static int get() {
		return value.get();
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executor.execute(new Accessor(i));
		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdownNow();
	}
}
