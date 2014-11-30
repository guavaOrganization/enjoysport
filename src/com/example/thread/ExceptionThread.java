package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThread implements Runnable {
	@Override
	public void run() {
		throw new RuntimeException();
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			executor.execute(new ExceptionThread());
		} catch (Exception e) {// 无法捕获异常
			System.out.println("Exception has been handled...");
		}
		
	}
}
