package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimplePriorities implements Runnable {// 线程优先级示例
	private int countDown = 5;
	private volatile double d;// 保证可见性
	private int priority;

	public SimplePriorities(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return Thread.currentThread() + " : " + countDown;
	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(priority);
		while (true) {
			for (int i = 1; i < 1000000; i++) {
				d += (Math.PI + Math.E) / (double) i;// 变量d使用了volatile,以努力确保不进行任何编译器优化
				// 数学运算是可以被终端的。
				if (i % 1000 == 0)
					Thread.yield();
			}

			System.out.println(this);// 控制台打印是开销较大的操作。向控制台打印不能被中断。
			if (--countDown == 0)
				return;
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executor.execute(new SimplePriorities(Thread.MIN_PRIORITY));
		}
		executor.execute(new SimplePriorities(Thread.MAX_PRIORITY));
		executor.shutdown();
	}
}
