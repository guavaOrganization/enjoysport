package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CaptureUncaughtException {
	static class ExceptionTask implements Runnable {
		@Override
		public void run() {
			Thread t = Thread.currentThread();
			System.out.println("run() by " + t);
			System.out.println("eh = " + t.getUncaughtExceptionHandler());
			throw new RuntimeException();
		}
	}
	
	static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
			System.out.println("caught >>>> " + paramThrowable);
		}
	}
	
	static class HandlerThreadFactory implements ThreadFactory{
		@Override
		public Thread newThread(Runnable paramRunnable) {
			System.out.println(this + " creating new Thread");
			Thread t = new Thread(paramRunnable);
			System.out.println("created " + t);
			t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
			System.out.println("eh >>> " + t.getUncaughtExceptionHandler());
			return t;
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool(new HandlerThreadFactory());
		executor.execute(new ExceptionTask());
		
		// 如果要在代码中处处使用相同的异常处理器,那么简单的方式是在Thread类中设置一个静态域,将这个处理器设置为默认的未捕获异常处理器
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		// 处理器只有在不存在线程专有的未捕获异常处理器的情况下才会被调用。系统会检查线程专有版本，如果没有发现，则检查线程组是否有其专有的uncaughtException()方法，
		// 如果没有，再调用defaultUncaughtExceptionHandler
	}
}
