package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {// 餐厅
	Meal meal;// 食物
	ExecutorService executor = Executors.newCachedThreadPool();

	WaitPerson waitPerson = new WaitPerson(this);// 服务员
	Chef chef = new Chef(this);// 厨师

	public Restaurant() {
		executor.execute(chef);
		executor.execute(waitPerson);
	}

	public static void main(String[] args) {
		new Restaurant();
	}
}
