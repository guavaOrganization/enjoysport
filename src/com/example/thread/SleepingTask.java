package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {
	@Override
	public void run() {
		try {
			while (countDown-- > 0) {// run中的异常不能跨线程传播回main线程，所以必须在本地处理所有在任务内部产生的异常
				System.out.println(status());
				// 可以是线程调度器切换到另一个线程、进而驱动另一个任务。
				TimeUnit.MILLISECONDS.sleep(100);// 睡眠、阻塞、休眠。
			}
		} catch (InterruptedException e) {
			System.err.println("Interrupted...");
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++)
			executorService.execute(new SleepingTask());
		executorService.shutdown();
	}
}
