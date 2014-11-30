package com.example.thread;

public class SelfManaged implements Runnable {
	private int countDown = 5;
	private Thread thread = new Thread(this);

	public SelfManaged() {
		thread.start();// 在构造器中启动线程可能会变得很有问题，因为另一个任务可能会在构造器结束之前开始执行，这意味着该任务能够访问处于不稳定状态的对象。
	}

	@Override
	public String toString() {
		return Thread.currentThread().getName() + "(" + countDown + ")";
	}

	@Override
	public void run() {
		while (true) {
			System.out.println(this);
			if (--countDown == 0)
				return;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new SelfManaged();
		}
	}
}
