package com.example.btrace.targetmethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestArgArray {
	public static void main(String[] args) {
		Random rand = new Random();
		int sleepTime = rand.nextInt(10) + 10;
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
			TestArgArray argArray = new TestArgArray();
			
			argArray.readInt(1);
			argArray.readString("readString");
			
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public int readInt(int arg) {
		return arg;
	}

	public String readString(String str) {
		return str;
	}
}
