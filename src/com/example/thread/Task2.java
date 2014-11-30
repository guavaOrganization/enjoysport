package com.example.thread;

public class Task2 implements Runnable {
	static Blocker blocker = new Blocker();

	@Override
	public void run() {
		blocker.waitingCall();
	}
}
