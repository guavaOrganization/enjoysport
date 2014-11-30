package com.example.thread;

import java.util.concurrent.TimeUnit;

public class InterruptingIdiom {
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		if (args.length != 1) {
			System.out.println("usage : java InterruptingIdiom delay-in-mS");
			System.exit(0);
		}

		Thread thread = new Thread(new Blocked2());
		thread.start();
		TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
		thread.interrupt();
	}
}
