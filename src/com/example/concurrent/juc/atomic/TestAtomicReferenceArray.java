package com.example.concurrent.juc.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class TestAtomicReferenceArray {
	public static void main(String[] args) {
		AtomicReferenceArray<AtomicUser> array = new AtomicReferenceArray<AtomicUser>(2);
		System.out.println(array.length());
		System.out.println(array.get(0));
		// array.set(2, new AtomicUser(1, "陈俊"));// 超出固定大小的下标了，会产生数组越界异常
		array.set(0, new AtomicUser(0, "猪古丽"));
		System.out.println(array.get(0));
	}
}
