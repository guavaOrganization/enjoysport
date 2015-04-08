package com.example.javanio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandler implements Runnable {
	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;

	public TimeClientHandler(String host, int port) {
		try {
			this.host = host;
			this.port = port;
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null)
								key.channel().close();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		// 多路复用器关闭后，所有注册在后面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable()) { // 说明服务端已经返回ACK应答消息。
				if (sc.finishConnect()) {// 连接成功
					// 将SocketChannel注册到多路复用器上，注册SelectionKey.OP_READ操作位，监听网络读操作，然后发送请求消息给服务端
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				}else{
					System.exit(1);
				}
			}
			// 如果客户端接收到了服务端的应答消息，则SocketChannel是可读的，由于无法实现判断应答码流的大小，所以预分配1Mde接受缓冲区用于读取应答消息。
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);// 调用read()方法进行异步读取操作，由于是异步操作，所以必须对读取的结果进行判断。
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Now is : " + body);
					this.stop = true;
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				} else {
					;
				}
			}
		}
	}

	private void doConnect() throws IOException {
		// 如果连接成功，则将SocketChannel注册到多路复用器Selector上，注册SelectionKey.OP_READ
		if (socketChannel.connect(new InetSocketAddress(InetAddress.getByName(host), port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else {
			// 如果没有直接成功，则说明服务器没有返回TCP握手应答消息，但这并不代表连接失败，我们需要将SocketChannel注册到多路复用器Selector上，
			// 注册SelectionKey.OP_CONNECT，当服务器返回TCP syn-ask消息后，Selector就能够轮询到这个SocketChannel处于连接就绪状态。
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	private void doWrite(SocketChannel sc) throws IOException {
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		sc.write(writeBuffer);
		// 由于发送是异步的，所以会存在"半包写"问题，通过hasRemaining()方法对发送结果进行判断，如果缓冲区中的消息全部发送完成，则打印消息
		if(!writeBuffer.hasRemaining()) 
			System.out.println("Send order 2 server succeed.");
	}
}
