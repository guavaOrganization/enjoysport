package com.example.thread;

public class ToastEater implements Runnable {
	private ToastQueue finishedQueue;

	public ToastEater(ToastQueue finishedQueue) {
		this.finishedQueue = finishedQueue;
	}

	private int counter = 0;

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast toast = finishedQueue.take();

				if (toast.getId() != counter++ || toast.getStatus() != Toast.Status.JAMMED) {
					System.out.println(">>>> Error " + toast);
				} else {
					System.out.println("Chomp " + toast);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("ToastEater interrupted");
		}
		System.out.println("ToastEater off");
	}
}
