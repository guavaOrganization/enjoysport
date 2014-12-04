package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TestAtomicIntegerFieldUpdater {
	public static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterData> getFieldUpdater(String fieldName) {
		return AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterData.class, fieldName);
	}

	public static void main(String[] args) {
		AtomicIntegerFieldUpdaterData updaterData = new AtomicIntegerFieldUpdaterData();
		System.out.println(getFieldUpdater("NO").addAndGet(updaterData, 1));
		// 不能操作由父类继承来的字段
		System.out.println(getFieldUpdater("parentId").addAndGet(updaterData, 2));
	}
}
