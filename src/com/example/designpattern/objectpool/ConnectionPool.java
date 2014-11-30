package com.example.designpattern.objectpool;

public class ConnectionPool {// 连接池，相当于一个工具类
	private static PoolManager pool = new PoolManager();

	public static void addConnections(int number) {
		for (int i = 0; i < number; i++) {
			pool.add(new ConnectionImplemention());
		}
	}

	public static Connection getConnection() throws PoolManager.EmptyPoolException {
		return (Connection) pool.get();
	}
	
	public static void releaseConnection(Connection connection) {
		pool.release(connection);
	}
}
