package com.example.thread;

import java.util.concurrent.TimeUnit;

public class Daemons {
	class Daemon implements Runnable {
		private Thread[] threads = new Thread[10];

		@Override
		public void run() {
			for (int i = 0; i < threads.length; i++) {
				threads[i] = new Thread(new DaemonSpawn());
				threads[i].start();
				System.out.println("DaemonSpawn " + i + " started.");
			}

			for (int i = 0; i < threads.length; i++) {
				System.out.println("thread[" + i + "].isDaemon = " + threads[i].isDaemon());
			}

			while (true)
				Thread.yield();
		}
	}

	class DaemonSpawn implements Runnable {
		@Override
		public void run() {
			while (true) {
				Thread.yield();
			}
		}
	}

	public static void main(String[] args) {
		Daemons daemons = new Daemons();
		Thread thread = new Thread(daemons.new Daemon());
		thread.setDaemon(true);// Daemon线程被设置成了后台模式，然后派生出许多子线程，这些线程并没有被显式地设置为后台模式,不过它们的确是后台线程
		thread.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
