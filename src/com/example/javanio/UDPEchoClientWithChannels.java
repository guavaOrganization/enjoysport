package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class UDPEchoClientWithChannels {
	private final static int LIMIT = 100;
	public static void main(String[] args) {
		SocketAddress remote;
		try {
			remote = new InetSocketAddress("127.0.0.1", 1223);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return;
		}

		try (DatagramChannel channel = DatagramChannel.open()){
			channel.configureBlocking(false);
			channel.connect(remote);
			
			Selector selector = Selector.open();
			channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			
			ByteBuffer buffer = ByteBuffer.allocate(4);
			int n = 0;
			int numbersRead = 0;
			
			while (true) {
				if(numbersRead == LIMIT) break;
				
				selector.select(60000);
				Set<SelectionKey> keys = selector.selectedKeys();
				if (keys.isEmpty() && n == LIMIT)
					break;
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
