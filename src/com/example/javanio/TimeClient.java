package com.example.javanio;

public class TimeClient {
	public static final int PORT = 1223;
	public static final String HOST = "127.0.0.1";

	public static void main(String[] args) {
		new Thread(new TimeClientHandler(HOST, PORT), "TimeClient-001").start();
	}
}
