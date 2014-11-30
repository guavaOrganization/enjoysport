package com.example.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadlockingDiningPhilosophers {// 哲学家就餐问题死锁版
	public static void main(String[] args) throws InterruptedException, IOException {
		int ponder = 1;
		if (null != args && args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}

		int size = 5;
		if (null != args && args.length > 1) {
			size = Integer.parseInt(args[1]);
		}

		ExecutorService executor = Executors.newCachedThreadPool();
		Chopstick[] sticks = new Chopstick[size];
		for (int i = 0; i < size; i++) {
			sticks[i] = new Chopstick();
		}

		for (int i = 0; i < size; i++) {
			// executor.execute(new Philosopher(sticks[i], sticks[(i + 1) % size], i, ponder));// 死锁演示
			// 打破死锁条件：循环等待
			if(i < size-1)
				executor.execute(new Philosopher(sticks[i], sticks[i + 1], i, ponder));// 左边是第i根筷子，右边是第i+1根筷子。
			else 
				executor.execute(new Philosopher(sticks[0], sticks[i], i, ponder));// 左边的是第0根筷子，右边的是最后一根筷子
			// 第0个哲学家：左0右1
			// 第1个哲学家：左1右2
			// 第2个哲学家：左2右3
			// 第3个哲学家：左3右4
			// 第4个哲学家：左0右4(不死锁)，左4右0(死锁，循环等待)
		}
		
		if (null != args && args.length == 3 && "timeout".equals(args[2])) {
			TimeUnit.SECONDS.sleep(5);
		} else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		executor.shutdownNow();
	}
}
