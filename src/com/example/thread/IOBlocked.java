package com.example.thread;

import java.io.IOException;
import java.io.InputStream;

public class IOBlocked implements Runnable {// 演示因为等待IO输入/输出造成线程阻塞
	private InputStream is;

	public IOBlocked(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		System.out.println("Waiting for read()");
		try {
			is.read();// 不可中断
		} catch (IOException e) {
			e.printStackTrace();
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Interrupted from blocked I/o");
			} else {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Exiting IOBlocked.run()");
	}
}
