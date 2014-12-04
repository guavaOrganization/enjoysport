package com.example.concurrent.juc.atomic;

public class AtomicIntegerFieldUpdaterData extends AtomicIntegerFieldUpdaterParentData{
	volatile static int NO = 0;// 只能是实例变量、不能是类变量

	private int id;// 存在访问控制
	int age;// 字段必须是volatile
	volatile int number;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
