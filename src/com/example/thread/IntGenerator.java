package com.example.thread;

public abstract class IntGenerator {
	private volatile boolean canceled = false;// 保证可见性

	public abstract int next();

	public void cancel() {
		this.canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}
}
