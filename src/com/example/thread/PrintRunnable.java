package com.example.thread;

public class PrintRunnable implements Runnable {
	private static int taskCount = 0;
	private final int id = taskCount++;
	
	public PrintRunnable() {
		System.out.println("Task Id Is " + id + " instantiation....");
	}

	@Override
	public void run() {
		int i = 3;
		while (i-- > 0) {
			System.out.println("Task Id Is " + id + " PrintRunnable...run >>> " + i);
			Thread.yield();
		}
		System.out.println("Task Id Is " + id + " closed...");
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new PrintRunnable()).start();
		}
	}
}
