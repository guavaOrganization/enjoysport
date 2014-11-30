package com.example.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
	private static int counter = 0;
	private final int id = counter++;// 马匹编号
	private int strides = 0;

	private static Random rand = new Random(47);
	private static CyclicBarrier barrier;

	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}

	public synchronized int getStrides() {
		return strides;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += rand.nextInt(3);
				}
				barrier.await();// 等待其他所有的马匹都准备完毕，然后调用CyclicBarrier中的"栅栏动作"Runnable
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Horse " + id + ";";
	}

	public String tracks() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			sb.append("*");
		}
		sb.append(id);
		return sb.toString();
	}
}
