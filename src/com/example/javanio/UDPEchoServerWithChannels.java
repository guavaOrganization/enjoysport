package com.example.javanio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoServerWithChannels {
	public static void main(String[] args) {
		try {
			DatagramChannel channel = DatagramChannel.open();
			DatagramSocket socket = channel.socket();
			SocketAddress address = new InetSocketAddress(1223);
			socket.bind(address);

			ByteBuffer buffer = ByteBuffer.allocateDirect(65507);
			while (true) {
				SocketAddress client = channel.receive(buffer);
				buffer.flip();
				channel.send(buffer, client);
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
