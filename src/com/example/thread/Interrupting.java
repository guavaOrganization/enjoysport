package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Interrupting {
	private static ExecutorService executor = Executors.newCachedThreadPool();

	static void test(Runnable r) throws InterruptedException {
		Future<?> future = executor.submit(r);
		TimeUnit.SECONDS.sleep(40);
		System.out.println("Interrupting " + r.getClass().getName());
		future.cancel(true);// 中断运行线程
		System.out.println("Interrupting sent to " + r.getClass().getName());
	}

	public static void main(String[] args) throws InterruptedException {
		test(new SleepBlocked());
		System.out.println(new IOBlocked(System.in));
		test(new SynchroninzedBlocked());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);
	}
}
