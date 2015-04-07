package com.example.javanio;

public class TimeServer {
	public static final int PORT = 1223;
	public static final String HOST = "127.0.0.1";
	public static void main(String[] args) {
		new Thread(new MultiplexerTimeServer(HOST, PORT), "NIO-MultiplexerTimeServer-001").start();;
	}
}
