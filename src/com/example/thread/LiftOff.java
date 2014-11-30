package com.example.thread;

public class LiftOff implements Runnable {
	protected int countDown = 10;// 默认值
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public LiftOff() {
	}
	
	public String status(){
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ")";
	}

	@Override
	public void run() {
		while (countDown-- > 0) {
			System.out.println(status());
			Thread.yield();// 对"线程调度器(Java 线程机制的一部分，可以将CPU从一个线程转移给另一个线程)"的一种建议，它在声明："我已经执行完生命周期中最重要的部分了，此刻正是切换给其他任务执行一段时间的大好机会"。这完全是选择性的。
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new LiftOff()).start();
		}
	}
}
