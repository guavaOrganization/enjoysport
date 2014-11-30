package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Pairtest {
	static void testApproaches(PairManager pm1, PairManager pm2, PairManager pm3) {
		ExecutorService executor = Executors.newCachedThreadPool();
		PairManipulator manipulator1 = new PairManipulator(pm1);
		PairManipulator manipulator2 = new PairManipulator(pm2);
		PairManipulator manipulator3 = new PairManipulator(pm3);

		PairChecker pc1 = new PairChecker(pm1);
		PairChecker pc2 = new PairChecker(pm2);
		PairChecker pc3 = new PairChecker(pm3);

		executor.execute(manipulator1);
		executor.execute(manipulator2);
		executor.execute(manipulator3);

		executor.execute(pc1);
		executor.execute(pc2);
		executor.execute(pc3);

		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("manipulator1：" + manipulator1 + ",manipulator2" + manipulator2);
		System.out.println("manipulator3：" + manipulator3);
		System.exit(0);
	}

	public static void main(String[] args) {
		testApproaches(new PairManager1(), new PairManager2(), new PairManager3());
	}
}
