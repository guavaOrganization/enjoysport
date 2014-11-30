package com.example.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseResource {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket socket = new ServerSocket(8080);
		InputStream is = new Socket("localhost", 8080).getInputStream();
		executor.execute(new IOBlocked(is));
		executor.execute(new IOBlocked(System.in));
		TimeUnit.SECONDS.sleep(100);
		System.out.println("Shutting down all threads");
		executor.shutdownNow();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Closing " + is.getClass().getName());
		is.close();// 一旦底层资源被关闭，任务将解除阻塞
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Closing " + System.in.getClass().getName());
		System.in.close();// 这个不会解除阻塞？
	}
}
