package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

public class IntgenClient {
	public static void main(String[] args) {
		try {
			int port = 1223;
			SocketAddress address = new InetSocketAddress(port);
//			SocketChannel client = SocketChannel.open(address); // 阻塞
			SocketChannel client = SocketChannel.open(); // 非阻塞，为了在连接前配置通道和socket的各种选项
			client.configureBlocking(false);
			client.connect(address);// 使用非阻塞通道时，connect()方法会立即返回，甚至在连接建立之前就会返回。在等待操作系统建立连接时，程序可以做其他的操作。
			// 不过程序在实际使用连接之前，必须调用client.finishConnect()
			
			ByteBuffer buffer = ByteBuffer.allocate(4);
			IntBuffer view = buffer.asIntBuffer();

			for (int expected = 0; expected < 10; expected++) {
				client.read(buffer);
				int actual = view.get();
				buffer.clear();
				view.rewind();
				
				if(actual!= expected){
					System.err.println("Expected " + expected + " ; was " + actual);
					break;
				}
				System.out.println(actual);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
