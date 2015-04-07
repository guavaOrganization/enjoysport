package com.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest { // 通道和缓冲区主要用于需要高校处理很多并发连接的服务器系统。
	public static void main(String[] args) {
		try {
			// 调用ServerSocketChannel.open()静态工厂方法创建一个新的ServerSocketChannel
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			ServerSocket ss = serverChannel.socket();
			ss.bind(new InetSocketAddress(1223));
			// Java7以上版本，可以直接绑定而不用java.net.ServerSocket
			// serverChannel.bind(new InetSocketAddress(1223));

			// 默认情况下，accept()方法会阻塞，知道有一个入站连接为止，这与ServerSocket的accept()方法类似。为了改变这点，
			// 只需要在调用accept()之前调用configureBlocking(false)
			serverChannel.configureBlocking(false);
			
			// 在传统方法中，要为每个连接分配一个线程，线程数目会随着客户端连接迅速攀升。
			// 相反，在新的I/O API中，可以创建一个Selector，允许程序迭代处理所有准备好的连接。要构造一个新的Selector，只需要调用Selector.open()静态工厂方法
			Selector selector = Selector.open();
			// 接下来需要为每个通道的register()方法向监视这个通道的选择器进行注册。在注册时，要使用SelectionKey类提供的命名常量指定所关注的操作。
			// 对于服务器Socket，唯一关心的操作就是OP_ACCEPT，也就是服务器Socket通道是否准备好接受一个新连接？
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			// 调用accept()方法接受连接，返回一个SocketChannel对象
			SocketChannel clientChannel = serverChannel.accept();// 如果没有入站连接，非阻塞的accept()几乎会立即返回null
			// 在服务器端，你肯定希望客户端通道处于非阻塞模式，以允许服务器处理多个并发连接
			clientChannel.configureBlocking(false);
			SelectionKey key = clientChannel.register(selector, SelectionKey.OP_WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
