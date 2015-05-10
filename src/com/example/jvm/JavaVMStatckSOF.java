package com.example.jvm;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	虚拟机栈h额本地方法栈OOM测试
 * 	-Xss128k
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class JavaVMStatckSOF {
	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	
	public static void main(String[] args) {
		JavaVMStatckSOF oom = new JavaVMStatckSOF();
		oom.stackLeak();
	}
}
