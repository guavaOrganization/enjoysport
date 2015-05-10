package com.example.concurrent;

public class Test {
	static final int SHARED_SHIFT = 16;

	public static void main(String[] args) {
		System.out.println((1 << SHARED_SHIFT) - 1);
	}
}
