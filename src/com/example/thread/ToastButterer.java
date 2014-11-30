package com.example.thread;

public class ToastButterer implements Runnable {
	private ToastQueue dryQueue, butterQueue;

	public ToastButterer(ToastQueue dryQueue, ToastQueue butterQueue) {
		this.dryQueue = dryQueue;
		this.butterQueue = butterQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = dryQueue.take();
				toast.butter();
				System.out.println(toast);
				butterQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Butterer interrupted");
		}
		System.out.println("Butterer Off");
	}
}
