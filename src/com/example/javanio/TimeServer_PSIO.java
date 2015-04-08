package com.example.javanio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	伪异步(Pseudo-asynchronous)I/O
 * 
 * 	采用线程池和任务队列可以实现一种叫做伪异步I/O通信框架
 * 
 * 	当有新的客户端接入的时候，将客户端的Socket封装成一个Task(该任务实现java.lang.Runnable接口)投递到后端的线程池中进行处理，
 * 	JDK的线程池维护一个消息队列和N个活跃线程对消息队列中的任务进行处理。由于线程池可以设置消息队列的大小和最大线程数，因此，它的资源占用
 * 	是可控的，无论多少个客户端并发访问，都不会导致资源的耗尽和宕机。
 * 
 * 	伪异步I/O通信框架采用了线程池实现，因此避免了为每个请求都创建一个独立线程造成的线程资源耗尽问题。但是由于它底层的通信依然采用同步阻塞模型，因此无法从根本上解决问题。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TimeServer_PSIO {
	public static void main(String[] args) {
		int port = 1223;
		
		ServerSocket server =null;
		try {
			server = new ServerSocket(port);
			Socket socket = null;
			TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);
			while (true) {
				socket = server.accept();
				pool.execute(new TimeServerHandler(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != server) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
