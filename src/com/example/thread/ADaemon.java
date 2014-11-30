package com.example.thread;

import java.util.concurrent.TimeUnit;

public class ADaemon implements Runnable {
	@Override
	public void run() {
		try {
			System.out.println("Starting ADaemon");
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("This Shound always run?");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// 当最后一个非后台线程终止时，后台线程就会“突然”终止。
		// 因此main()退出，JCM就会立即关闭所有的后台线程，而不会有任何你希望出现的确认形式。
		// 因此，不能以优雅的方法关闭后台线程
		Thread thread = new Thread(new ADaemon());
		thread.setDaemon(true);// 如果注释掉这行,Runnable中的run方法中的finally块将会被执行
		thread.start();
	}
}
