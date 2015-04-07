package com.example.javalang.reflect;

public class CountedInteger {
	private static long counter;
	private final long id = counter++;

	@Override
	public String toString() {
		return Long.toString(id);
	}
}
