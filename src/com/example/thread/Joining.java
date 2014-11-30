package com.example.thread;

public class Joining extends Thread {
	class Sleeper extends Thread {
		private int duration;

		public Sleeper(String name, int sleeperTime) {
			super(name);
			this.duration = sleeperTime;
			start();
		}
		
		@Override
		public void run() {
			try {
				sleep(duration);// 有可能在指定的时间期满时返回，但也可能被中断。
			} catch (InterruptedException e) {
				// 当另外一个线程上调用interrupt()时，将给该线程设定一个标志，表明该线程已经被中断。
				// 然而，异常被捕获时将清理这个标志，所以在catch子句中，在异常捕获的时候这个标志总是为假
				System.out.println(getName() + " was interrupted. " + " isInterrupted() : " + isInterrupted());
				return;
			}
			System.out.println(getName() + " has awakened");
		}
	}
	
	class Joiner extends Thread {
		private Thread sleeper;

		public Joiner(String name, Thread sleeper) {
			super(name);
			this.sleeper = sleeper;
			start();
		}
		
		@Override
		public void run() {
			try {
				sleeper.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + " join completed...");
		}
	}
	
	public static void main(String[] args) {
		Joining joining = new Joining();
		
		Sleeper sleepy = joining.new Sleeper("sleepy", 1500), 
				grumpy = joining.new Sleeper("grumpy", 1500);
		
		Joiner 
			dopey = joining.new  Joiner("dopey", sleepy),
			doc = joining.new Joiner("doc", grumpy);
		grumpy.interrupt();
	}
}
