package com.example.btrace.targetmethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestLocationReturn {
	public static void main(String[] args) {
		Random rand = new Random();
		int sleepTime = rand.nextInt(10) + 10;
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
			TestLocationReturn location = new TestLocationReturn();
			location.testReturn();
			location.testReturn(sleepTime);
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int testReturn() {
		return 10;
	}

	public int testReturn(int sleepTime) {
		System.out.println("sleepTime is " + sleepTime);
		return sleepTime;
	}
}
