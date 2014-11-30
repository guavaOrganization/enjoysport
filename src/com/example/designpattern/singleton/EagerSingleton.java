package com.example.designpattern.singleton;

public final class EagerSingleton {// 防止通过克隆方法创建对象
	private EagerSingleton() {
	}

	private static class EagerSingletonFactory {
		/* 如果构造器实例化失败，将无法创建该实例 */
		private static EagerSingleton instance = new EagerSingleton();
	}

	public static EagerSingleton getInstance() {
		return EagerSingletonFactory.instance;
	}

	/* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return getInstance();
	}
}
