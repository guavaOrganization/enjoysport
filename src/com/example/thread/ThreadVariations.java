package com.example.thread;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 使用内部类来将线程代码隐藏在类中
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ThreadVariations {
	class InnerThread1 {
		private int countDown = 5;
		private Inner inner;

		private class Inner extends Thread {
			Inner(String name) {
				super(name);
				start();
			}
			
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println(this);
						if (--countDown == 0)
							return;
						sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public String toString() {
				return getName() + " : " + countDown;
			}
		}
		
		public InnerThread1(String name) {
			inner = new Inner(name);
		}
	}
	
	class InnerThread2 {
		private int countDown = 5;
		private Thread thread;

		public InnerThread2(String name) {
			thread = new Thread(name) {// 匿名内部类
				@Override
				public void run() {
					try {
						while (true) {
							System.out.println(this);
							if (--countDown == 0)
								return;
							sleep(10);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				@Override
				public String toString() {
					return getName() + " : " + countDown;
				}
			};
			thread.start();
		}
	}
	
	class InnerRunnable1 {
		private int countDown = 5;
		private Inner inner;

		private class Inner implements Runnable {
			Thread thread;

			public Inner(String name) {
				thread = new Thread(this, name);
				thread.run();
			}

			@Override
			public void run() {
				try {
					while (true) {
						System.out.println(this);
						if (--countDown == 0) {
							return;
						}
						TimeUnit.MILLISECONDS.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public String toString() {
				return thread.getName() + " : "  + countDown;
			}
		}
		
		public InnerRunnable1(String name) {
			inner = new Inner(name);
		}
	}
	
	class InnerRunnable2 {
		private int countDown = 5;
		private Thread thread;

		public InnerRunnable2(String name) {
			thread = new Thread(name) {
				@Override
				public void run() {
					try {
						while (true) {
							System.out.println(this);
							if (--countDown == 0)
								return;
							sleep(10);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				@Override
				public String toString() {
					return getName() + " : " + countDown;
				}
			};
			thread.start();
		}
	}
	
	class ThreadMethod {
		private int countDown = 5;
		private String name;
		private Thread thread;
		public ThreadMethod(String name) {
			this.name = name;
		}
		
		public void runTask() {
			if (thread == null) {
				thread = new Thread(name) {
					@Override
					public void run() {
						try {
							while (true) {
								System.out.println(this);
								if (--countDown == 0)
									return;
								sleep(10);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public String toString() {
						return getName() + " : " + countDown;
					}
				};
				thread.start();
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadVariations variations = new ThreadVariations();
		variations.new InnerThread1("InnerThread1");
		variations.new InnerThread2("InnerThread2");
		variations.new InnerRunnable1("InnerRunnable1");
		variations.new InnerRunnable2("InnerRunnable2");
		variations.new ThreadMethod("ThreadMethod").runTask();
	}
}
