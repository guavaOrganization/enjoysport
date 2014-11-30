package com.example.thread;

import java.util.concurrent.TimeUnit;

public class Chef implements Runnable {
	private Restaurant restaurant;
	private int count = 0;

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {

				synchronized (this) {
					while (restaurant.meal != null)
						wait();
				}

				if (++count == 10) {
					System.out.println("Out of food,Closing");
					restaurant.executor.shutdownNow();
				}

				// 在ExecutorService.showDownNow()向所有的任务发出Interrupt(),任务并没有在获得该Interrupt()之后立即关闭
				// 因为当人物试图计入一个（可中断的）阻塞操作时，这个中断只能抛出InterruptedException
				System.out.println("Order up");
				synchronized (restaurant.waitPerson) {// 先捕获waitperson上的锁
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef interrupted");
		}
	}
}
