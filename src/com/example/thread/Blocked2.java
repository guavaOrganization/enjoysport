package com.example.thread;

import java.util.concurrent.TimeUnit;

public class Blocked2 implements Runnable {
	private volatile double d = 0.8;

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {// 清除中断状态位
				// Point1
				NeedsCleanup n1 = new NeedsCleanup(1);
				try {
					System.out.println("Sleeping...");
					/**
					 * <p>
					 * 	如果interrupt()在Point1和Point2之间（在while语句之后，但是在阻塞操作sleep()之前或其过程中）被调用，
					 * 	那么这个任务就会在第一次试图调用阻塞操作之前，经由InterruptedException退出，
					 * 	在这种情况下，在异常被抛出之时唯一被创建出来的NeedsCleanup对象将被清除，而你也就有了在catch子句中执行其他任何清除工作的机会
					 * </p>
					 */
					TimeUnit.SECONDS.sleep(1);
					// Point2
					NeedsCleanup n2 = new NeedsCleanup(2);
					try {
						/**
						 * <p>
						 *	如果interrupt()在Point2注释之后（即在非阻塞的操作过程中）被调用，
						 * 	那么首先循环将结束，然后所有的本地对象将被销毁,最后循环会经由while()语句的顶部退出
						 * </p>
						 */
						System.out.println("Calculating");
						for (int i = 1; i < 2500000; i++) {
							d = d + (Math.PI + Math.E) / d;
						}
						System.out.println("Finished time-consuming operation");
					} finally {
						n2.cleanup();
					}
				} finally {
					n1.cleanup();
				}
			}
			System.out.println("Exiting via while() test()");
		} catch (InterruptedException e) {
			System.out.println("Exiting via InterruptedException");
		}
	}
}
