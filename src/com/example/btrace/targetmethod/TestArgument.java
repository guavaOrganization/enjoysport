package com.example.btrace.targetmethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestArgument {
	public static void main(String[] args) {
		Random rand = new Random();
		int sleepTime = rand.nextInt(10) + 10;
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
			TestArgument argument = new TestArgument();
			TimeUnit.SECONDS.sleep(2);
			argument.test();

			argument.interfaceTest();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int test() {
		System.out.println("test() from TestArgument");
		return 1;
	}

	public void interfaceTest() throws InterruptedException {
		MyInterface interfaceA = new MyInterfaceImplA();
		interfaceA.test();
		TimeUnit.SECONDS.sleep(2);
	}
}
