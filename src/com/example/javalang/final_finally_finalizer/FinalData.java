package com.example.javalang.final_finally_finalizer;

import java.util.Random;

public class FinalData {
	private static Random rand = new Random(47);
	private String id;

	public FinalData(String id) {
		this.id = id;
	}

	// 编译时数值的final基本类型，所以两者均可以用作编译期常量
	private final int valueOne = 9;
	private static final int VALUE_TWO = 99;

	// 是一种更加典型的对常量进行定义的方式：定义为public，则可以被用于包之外；定义为static，则强调只有一份；定义为final，则说明它是一个常量
	public static final int VALUE_THREE = 39;

	// 我们不能因为数据是final的就认为在编译时可以知道它的值。在运行时使用随机生成的数值来初始化i4和INT_5就说明了这点。
	private final int i4 = rand.nextInt(20);
	static final int INT_5 = rand.nextInt(20); // 在类状态被初始化，而不是每次创建新对象时都初始化

	// v1到VAL_3这些变量说明了final引用的意义。
	private Value v1 = new Value(11);
	private final Value v2 = new Value(22);
	private static final Value VAL_3 = new Value(33);

	private final int[] a = { 1, 2, 3, 4, 5, 6 };

	@Override
	public String toString() {
		return id + " : " + "i4:" + i4 + ".INT_5:" + INT_5;
	}

	public static void main(String[] args) {
		FinalData fd1 = new FinalData("fd1");
		// fd1.valueOne++;//不能改变此值
		// 不能因为v2是final的，就认为无法改变它的值。由于它是一个引用，final意味着无法将v2再次指向另一个新的对象。
		// 这对数组具有相同的意义，数组只不过是另一个引用
		fd1.v2.i++;
		fd1.v1 = new Value(9);

		for (int i = 0; i < fd1.a.length; i++) {
			fd1.a[i]++;
		}

		// fd1.v2 = new Value(0);
		// fd1.VAL_3 = new Value(1);
		// fd1.a = new int[3];
		System.out.println("Creating new FinalData");
		FinalData fd2 = new FinalData("fd2");
		System.out.println(fd1);
		System.out.println(fd2);
	}
}
