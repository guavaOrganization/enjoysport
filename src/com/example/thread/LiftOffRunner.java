package com.example.thread;

import java.util.concurrent.BlockingQueue;

public class LiftOffRunner implements Runnable {
	private BlockingQueue<LiftOff> rockets;

	public LiftOffRunner(BlockingQueue<LiftOff> queue) {
		this.rockets = queue;
	}

	public void add(LiftOff liftOff) {
		try {
			rockets.put(liftOff);
		} catch (InterruptedException e) {
			System.out.println("Interrupted during put()");
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				LiftOff liftOff = rockets.take();
				liftOff.run();
			}
		} catch (InterruptedException e) {
			System.out.println("Waking from take()");
		}
		System.out.println("Exiting LiftOffRunner");
	}
}
