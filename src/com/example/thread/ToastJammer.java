package com.example.thread;

public class ToastJammer implements Runnable {
	private ToastQueue butteredQueue, finishedQueue;

	public ToastJammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
		this.butteredQueue = butteredQueue;
		this.finishedQueue = finishedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = butteredQueue.take();
				toast.jam();
				System.out.println(toast);
				finishedQueue.put(toast);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer interrupted");
		}
		System.out.println("Jammer off");
	}
}
