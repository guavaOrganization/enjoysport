package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ToastOMatic {
	public static void main(String[] args) throws InterruptedException {
		ToastQueue dryQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishedQueue = new ToastQueue();
		ExecutorService executor = Executors.newCachedThreadPool();

		// 因为同步由队列（其内部是同步的）和系统的设计隐式地管理了
		// 每片Toast在任何时刻都只由一个任务在操作。因为队列的阻塞，使得处理过程中将被自动挂起和恢复
		executor.execute(new Toaster(dryQueue));
		executor.execute(new ToastButterer(dryQueue, butteredQueue));
		executor.execute(new ToastJammer(butteredQueue, finishedQueue));
		executor.execute(new ToastEater(finishedQueue));
		TimeUnit.SECONDS.sleep(5);
		executor.shutdownNow();
	}
}
