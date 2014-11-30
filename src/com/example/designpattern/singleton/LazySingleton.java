package com.example.designpattern.singleton;

public class LazySingleton {
	private static LazySingleton singleton;

	private LazySingleton() {
	}

	public static LazySingleton getInstance() {
		if (null == singleton) {
			synchronized (singleton) {
				if (null == singleton)
					singleton = new LazySingleton();
			}
		}
		return singleton;
	}

	/* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return getInstance();
	}
}
