package com.example.javanio;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	非阻塞I/O(NIO)
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TimeServer {
	public static final int PORT = 1223;
	public static final String HOST = "127.0.0.1";
	public static void main(String[] args) {
		new Thread(new MultiplexerTimeServer(HOST, PORT), "NIO-MultiplexerTimeServer-001").start();;
	}
}
