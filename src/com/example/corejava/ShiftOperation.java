package com.example.corejava;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 *            <p>
 *            移位运算符就是在二进制的基础上对数字进行平移。按照平移的方向和填充数字的规则分为三种：<<（左移）、>>（带符号右移）和>>>（
 *            无符号右移）。
 *            </p>
 * 
 * @author 八两俊
 * @since
 */
public class ShiftOperation {
	static final int SHARED_SHIFT = 16;
	// 左移运算符16位，也就是
	static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

	static int exclusiveCount(int c) {
		return c & EXCLUSIVE_MASK;// 保证最大数为65535
	}

	public static void main(String[] args) {
		System.out.println(exclusiveCount(2));
		System.out.println(1 << SHARED_SHIFT);
		System.out.println(16 >> 2);

		System.out.println(3 << 2);// 00000011-->00001100 = 12
		System.out.println(-3 << SHARED_SHIFT);
	}
}
