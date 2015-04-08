package com.example.javanio;

public class TimeServer_AIO {
	public static void main(String[] args) {
		new Thread(new AsyncTimeServerHandler(1223)).start();
	}
}
