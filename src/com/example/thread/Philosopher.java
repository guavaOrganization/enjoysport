package com.example.thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
	private Chopstick left;
	private Chopstick right;
	private final int id;
	private final int ponderFactor;

	Random rand = new Random(47);

	private void pause() throws InterruptedException {
		if (ponderFactor == 0)
			return;
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 100));
	}

	public Philosopher(Chopstick left, Chopstick right, int id,
			int ponderFactor) {
		this.left = left;
		this.right = right;
		this.id = id;
		this.ponderFactor = ponderFactor;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {// 哲学家不断的思考和吃饭
				System.out.println(this + " " + "thinking");
				pause();
				
				System.out.println(this + " grabbing right");
				right.take();// 拿右手边的筷子
				
				System.out.println(this + " grabbing left");
				left.take();// 拿左手边的筷子
				
				System.out.println(this + " eating");
				pause();
				right.drop();
				left.drop();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " " + "exiting via interrupt");
		}
	}

	@Override
	public String toString() {
		return "Philosopher " + id;
	}
}
