package com.example.jvm;
/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	创建线程导致内存溢出异常
 * 	-Xss2M
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class JavaVMStackOOM {
	public void dontStop() {
		while (true) {

		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		new JavaVMStackOOM().stackLeakByThread();
	}
}
