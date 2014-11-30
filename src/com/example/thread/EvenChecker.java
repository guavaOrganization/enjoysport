package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
	private IntGenerator intGenerator;

	public EvenChecker(IntGenerator intGenerator, int ident) {
		this.intGenerator = intGenerator;
	}

	@Override
	public void run() {
		while (!intGenerator.isCanceled()) {
			int val = intGenerator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				// 本案例中可以被撤销的类不是Runnable，而所有依赖于IntGenerator对象的EvenChecker任务将测试它，以查看它是否已经被撤销
				// 这可以消除所谓竞争条件，即两个或更多的任务竞争响应某个条件，因此产生冲突或不一致结果的情况
				intGenerator.cancel();
			}
		}
	}

	public static void test(IntGenerator generator, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			executor.execute(new EvenChecker(generator, i));
		}
		executor.shutdown();
	}

	public static void test(IntGenerator generator) {
		test(generator, 10);
	}
}
