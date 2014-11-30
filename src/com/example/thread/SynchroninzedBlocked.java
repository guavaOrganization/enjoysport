package com.example.thread;

public class SynchroninzedBlocked implements Runnable {// 演示因为同步控制块造成的线程阻塞
	public synchronized void f() {
		while (true) {
			Thread.yield();
		}
	}

	public SynchroninzedBlocked() {
		new Thread() {
			@Override
			public void run() {
				f();
			}
		}.start();
	}

	@Override
	public void run() {
		System.out.println("Trying to call f()");
		f();// synchronized不可中断阻塞
		System.out.println("Exiting SynchronizedBlocked.run()");
	}
}
