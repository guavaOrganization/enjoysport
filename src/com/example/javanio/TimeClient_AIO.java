package com.example.javanio;

public class TimeClient_AIO {
	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler("127.0.0.1", 1223)).start();
	}
}
