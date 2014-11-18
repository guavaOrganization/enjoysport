package com.example.concurrent.executorservice;

import java.net.Socket;

public class Handler implements Runnable {
	private final Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// read and service request on socket
		socket.getPort();
	}
}
