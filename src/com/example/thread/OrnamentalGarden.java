package com.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {
	static class Count {
		private int count = 0;
		private Random rand = new Random(47);

		public synchronized int increment() {
			int temp = count;
			if (rand.nextBoolean()) {// 返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 boolean 值
				Thread.yield();
			}
			return (count = ++temp);
		}

		public synchronized int value() {
			return count;
		}
	}

	static class Entrnce implements Runnable {
		private static Count count = new Count();
		private static List<Entrnce> entrnces = new ArrayList<Entrnce>();

		private int number = 0;
		private final int id;
		private static volatile boolean canceled = false;

		public static void cancel() {
			canceled = true;
		}

		public Entrnce(int id) {
			this.id = id;
			entrnces.add(this);
		}

		@Override
		public void run() {
			while (!canceled) {
				synchronized (this) {
					++number;
				}
				System.out.println(this + " Total:" + count.increment());
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Stopping " + this);
		}

		public synchronized int getValue() {
			return number;
		}

		@Override
		public String toString() {
			return "Entrnce " + id + ":" + getValue();
		}

		public static int getTotalCount() {
			return count.value();
		}

		public static int sumEntrnces() {
			int sum = 0;
			for (Entrnce entrnce : entrnces) {
				sum += entrnce.getValue();
			}
			return sum;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executor.execute(new Entrnce(i));
		}

		TimeUnit.SECONDS.sleep(3);
		Entrnce.cancel();
		executor.shutdown();
		// 等待每个任务结束，如果所有的任务在超时时间到达之前全部结束,则返回true,否则返回false，表示不是所有的任务都结束了。
		if (!executor.awaitTermination(250, TimeUnit.MILLISECONDS)) {
			System.out.println("some tasks ware not terminated");
		}

		System.out.println("Total " + Entrnce.getTotalCount());
		System.out.println("Sum of Entrnces:" + Entrnce.sumEntrnces());
	}
}
