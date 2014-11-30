package com.example.designpattern.proxy;

public class ConnectionPool {
	private static PoolManager pool = new PoolManager();

	private ConnectionPool() {
	}

	public static void addConnections(int number) {
		for (int i = 0; i < number; i++) {
			pool.add(new ConnectionImplementation());
		}
	}

	public static Connection getConnection() {
		PoolManager.ReleaseableReference rr = pool.get();
		if (rr == null)
			return null;
		return new ConnectionProxy(rr);
	}

	private static class ConnectionProxy implements Connection {
		private PoolManager.ReleaseableReference implementation;

		public ConnectionProxy(PoolManager.ReleaseableReference rr) {
			this.implementation = rr;
		}

		@Override
		public void release() {
			implementation.release();
		}

		@Override
		public void set(Object x) {
			((Connection) implementation.getReference()).set(x);
		}

		@Override
		public Object get() {
			return ((Connection) implementation.getReference()).get();
		}
	}
}
