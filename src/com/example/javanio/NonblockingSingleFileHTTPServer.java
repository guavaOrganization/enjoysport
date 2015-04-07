package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NonblockingSingleFileHTTPServer {
	private ByteBuffer contentBuffer;
	private int port = 80;

	public NonblockingSingleFileHTTPServer(ByteBuffer data, String encoding, String MIMEType, int port) {
		this.port = port;
		String header = "HTTP/1.0 200 OK\r\n"
				+ "Server: NonblockingSingleFileHTTPServer\r\n"
				+ "Content-length: " + data.limit() + "\r\n"
				+ "Content-type: " + MIMEType + "\r\n\r\n";
		byte[] headerData = header.getBytes(Charset.forName("US-ASCII"));
		
		ByteBuffer buffer = ByteBuffer.allocate(data.limit() + headerData.length);
		buffer.put(headerData);
		buffer.put(data);
		buffer.flip();
		this.contentBuffer = buffer;
	}
	
	public void run() throws IOException {
		// 创建一个新的ServerSocketChannel对象。
		// 不过，这个方法有一点欺骗性。这个方法实际上并不打开一个新的服务器Socket，而是只创建这个对象。。
		// 在使用之前，需要调用socket()方法来获得相应的对等端(peer)ServerSocket。
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket ss = serverChannel.socket();
		// 配置服务器Socket选项
		
		InetSocketAddress address = new InetSocketAddress(port);
		ss.bind(address);
		serverChannel.configureBlocking(false);

		Selector selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			selector.select();
			Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				SelectionKey key = keys.next();
				keys.remove();
				
				try {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					} else if (key.isWritable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (buffer.hasRemaining() && channel.write(buffer) != -1) {
							
						} else
							channel.close();
					} else if (key.isReadable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(4096);
						while (buffer.hasRemaining() && channel.read(buffer) != -1);
						key.interestOps(SelectionKey.OP_WRITE); // 将通道切换为只写模式
						key.attach(contentBuffer.duplicate());
					}
				} catch (IOException e) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e2) {
					}
				}
			}
		}
	}
}
