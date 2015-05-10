package com.example.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Java堆溢出
 * 	-Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class HeapOOM {
	static class OOMObject {
	}

	public static void main(String[] args) {
		List<HeapOOM.OOMObject> list = new ArrayList<HeapOOM.OOMObject>();
		while (true)
			list.add(new HeapOOM.OOMObject());
	}
}
