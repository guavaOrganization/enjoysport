package com.example.thread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PipedIO {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService executor = Executors.newCachedThreadPool();
		// Sender和Receiver是在main方法中启动的，即对象构造彻底完毕之后。
		// 如果启动一个没有构造完毕的对象，在不同平台上管道可能会产生不一致的行为。
		executor.execute(sender);
		executor.execute(receiver);

		TimeUnit.SECONDS.sleep(4);
		// 在shutdownNow()被调用时，可以看到PipedReader与普通的I/O之间最重要的差异：
		// PipedReader是可中断的。如果将in.read()调用改成System.in.read()，那么Interrupt()将不能打断read()调用
		executor.shutdownNow();
	}
}
