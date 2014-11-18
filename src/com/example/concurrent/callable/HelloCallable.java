package com.example.concurrent.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class HelloCallable implements Callable<String> {
	@Override
	public String call() throws Exception {
		Random random = new Random();
		int i = random.nextInt(10);
		if (i == 0)
			i = 5;
		TimeUnit.SECONDS.sleep(i);
		return "HelloCallable>>>>sleep time : " + i + "s";
	}
}
