package com.example.thread;

/**
 * 哲学家就餐问题
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	任何两个Philosopher都不能成功take()同一根筷子。
 * 	如果一根Chopstick已经被某个Philosopher获得，那么另一个Philosopher可以wait()，直至这个Chopstick的当前持有者调用drop()使其可用为止
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class Chopstick {// 筷子
	private boolean taken = false;// 筷子是否已经被拿走标识

	public synchronized void take() throws InterruptedException {
		while (taken)
			wait();
		taken = true;
	}

	public synchronized void drop() {
		taken = false;
		notifyAll();
	}
}
