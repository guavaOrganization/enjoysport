package com.example.thread;

public class SyncObject {
	static class DualSynch {
		private Object syncObject = new Object();

		public synchronized void f() {// 在整个this上同步
			for (int i = 0; i < 5; i++) {
				System.out.println("f()-->" + i);
				Thread.yield();
			}
		}

		public void g() {
			for (int i = 0; i < 5; i++) {
				synchronized (syncObject) {// 在syncObject上同步的synchronized块，域this是独立的
					System.out.println("g()-->" + i);
				}
			}
		}
	}

	public static void main(String[] args) {
		final DualSynch synch = new DualSynch();
		new Thread() {
			public void run() {
				synch.g();
			};
		}.start();
		synch.f();
	}
}
