package com.example.javanio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	多路复用器类
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MultiplexerTimeServer implements Runnable {
	private Selector selector;
	// 针对面向流的侦听套接字的可选择通道。
	private ServerSocketChannel servChannel;
	private volatile boolean stop;

	public MultiplexerTimeServer(String host,int port) {
		try {
			// #SelectableChannel对象的多路复用器。(#ServerSocketChannel和#SocketChannel间接继承了#SelectableChannel)
			selector = Selector.open();// 创建多路复用器
			
			// 打开#ServerSocketChannel，用于监听客户端的连接，它是所有客户端连接的父管道
			servChannel = ServerSocketChannel.open();
			
			// 绑定端口，设置连接为非阻塞模式
			servChannel.configureBlocking(false);
			// 服务器套接字通道不是侦听网络套接字的完整抽象。必须通过调用socket方法所获得的关联{#link ServerSocket}对象来完成对套接字选项的绑定和操作.
			// 不可能为任意的已有服务器套接字创建通道，也不可能指定与【服务器套接字通道】关联的服务器套接字所使用的 SocketImpl对象。
			servChannel.socket().bind(new InetSocketAddress(InetAddress.getByName(host), port), 1024);// 1024表示backlog长度

			// 将#ServerSocketChannel注册到多路复用器Selector上，监听ACCEPT事件
			servChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("The TimeServer is start in port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void stop() {
		this.stop = true;
	}
	
	@Override
	public void run() {
		while (!stop) {
			try {
				// 选择一组键，其相应的通道已为 I/O操作准备就绪。
				// 此方法执行【非阻塞】的选择操作。如果自从前一次选择操作后，没有通道变成可选择的，则此方法直接返回零。
				// 调用此方法会清除所有以前调用wakeup方法所得的结果。
				// selector.selectNow();
				
				// 选择一组键，其相应的通道已为 I/O操作准备就绪。
				// 此方法执行处于【阻塞模式】的选择操作。仅在至少选择一个通道、调用此选择器的wakeup方法、当前的线程已中断，或者给定的超时期满（以先到者为准）后此方法才返回。
				// 此方法不提供实时保证：它安排了超时时间，就像调用 Object.wait(long)方法一样。
				// timeout - 如果为正，则在等待某个通道准备就绪时最多阻塞 timeout 毫秒；如果为零，则无限期地阻塞；必须为非负数
				selector.select(1000);
				// selector.select();

				// 返回此选择器的键集。
				// 不可直接修改键集。仅在已取消某个键并且已注销其通道后才移除该键。试图修改键集会导致抛出UnsupportedOperationException。
				// 已选择键集是非线程安全的。
				// Set<SelectionKey> selectionKeys = selector.keys();

				// 返回此选择器的已选择键集。
				// 可从已选择键集中移除键，但是无法直接添加键。试图向该键集中添加对象会导致抛出UnsupportedOperationException。
				// 已选择键集是非线程安全的。
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
			}
		}
		// 多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (selector != null) {
			try {
				selector.close();// 通过选择器的close方法关闭选择器之前，它一直保持打开状态。
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException{
		if (key.isValid()) {
			if (key.isAcceptable()) {
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("The Time Server receive order : " + body);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH小时mm分ss秒");
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? sdf.format(new Date(System.currentTimeMillis())) : "BAD ORDER";
					doWrite(sc, currentTime);
				} else if (readBytes < 0) {
					key.cancel();
					sc.close();
				} else
					;
			}
		}
	}
	
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
}
