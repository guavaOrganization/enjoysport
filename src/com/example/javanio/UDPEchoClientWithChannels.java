package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
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

		try (DatagramChannel channel = DatagramChannel.open()) {
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
				else {
					Iterator<SelectionKey> it = keys.iterator();
					while (it.hasNext()) {
						SelectionKey key = (SelectionKey) it.next();
						it.remove();
						if (key.isReadable()) {
							buffer.clear();
							channel.read(buffer);
							buffer.flip();
							int echo = buffer.getInt();
							System.out.println("Read: " + echo);
							numbersRead++;
						} 
						
						if (key.isWritable()) {
							buffer.clear();
							buffer.putInt(n);
							buffer.flip();
							channel.write(buffer);
							System.out.println("Write: " + n);
							n++;
							if (n == LIMIT) {
								key.interestOps(SelectionKey.OP_READ);// 所有包已写入，切换到只读模式
							}
						}
					}
				}
			}
			
			System.out.println("Echoed " + numbersRead + " out of " + LIMIT + " sent");
			System.out.println("Success rate: " + 100.0 * numbersRead / LIMIT + "%");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
