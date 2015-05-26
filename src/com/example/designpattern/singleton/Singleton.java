package com.example.designpattern.singleton;

public class Singleton {
	private static Singleton instance;

	private Singleton() {
	}

	private static synchronized Singleton innerInit() {
		if (null == instance)
			instance = new Singleton();
		return instance;
	}

	public static Singleton getInstance() {
		if (instance == null)
			return innerInit();
		return instance;
	}
}
