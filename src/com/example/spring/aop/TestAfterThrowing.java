package com.example.spring.aop;

import java.util.Random;

public class TestAfterThrowing {
	public void testAfterThrowing(String name) throws Exception {
		Random random = new Random(10);
		int i = random.nextInt();
		if (i % 2 == 0) {
			throw new Exception("testAfterThrowing , case " + i + " % 2 == 0");
		}
	}
}
