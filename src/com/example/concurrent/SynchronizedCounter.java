package com.example.concurrent;

public class SynchronizedCounter {
	private int value;

	public synchronized int getValue() {
		return value;
	}

	public synchronized int increment() {
		return ++value;
	}

	public synchronized int decrement() {
		return --value;
	}
}
