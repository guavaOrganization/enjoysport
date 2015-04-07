package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPDiscardServer {
	public static void main(String[] args) {
		try {
			DatagramChannel channel = DatagramChannel.open();
			SocketAddress address = new InetSocketAddress(1223);
			// JDK6或以下版本
			// DatagramSocket socket = channel.socket();
			// socket.bind(address);
			channel.bind(address);// JDK7或以上版本

			ByteBuffer buffer = ByteBuffer.allocateDirect(65507);
			while (true) {
				SocketAddress client = channel.receive(buffer);
				buffer.flip();
				System.out.print(client + " says : ");
				while (buffer.hasRemaining()) {
					System.out.write(buffer.get());
				}
				System.out.println();
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
