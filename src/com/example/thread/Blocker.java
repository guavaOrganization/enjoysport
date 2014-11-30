package com.example.thread;

public class Blocker {
	synchronized void waitingCall() {
		try {
			while (!Thread.interrupted()) {
				wait();
				System.out.println(Thread.currentThread() + " ");
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting waitingCall() by " + e);
		}
	}

	synchronized void prod() {// 确保只唤醒在等待这个特定锁的任务
		notify();
	}

	synchronized void prodAll() {
		notifyAll();
	}
}
