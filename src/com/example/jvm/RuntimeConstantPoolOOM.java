package com.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	运行时常量池导致的内存溢出异常
 * 
 * 	-XX:PermSize=10M -XX:MaxPermSize=10M
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}
