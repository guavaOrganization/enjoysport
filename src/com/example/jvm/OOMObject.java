package com.example.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	-Xms100M -Xmx100M -XX:+UseSerialGC
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class OOMObject {
	public byte[] placeholder = new byte[64 * 1024];

	public static void fillHeap(int num) throws InterruptedException {
		List<OOMObject> list = new ArrayList<OOMObject>();

		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		
		System.gc();
		TimeUnit.SECONDS.sleep(1000);
	}
	
	public static void main(String[] args) throws InterruptedException {
		fillHeap(1000);
	}
}
