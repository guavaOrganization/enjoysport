package com.example.btrace.targetmethod;

import java.util.concurrent.TimeUnit;

import com.example.btrace.targetmethod.sub.MyInterfaceSub;

public class TestOnTimerAnOnExit {
	public static void main(String[] args) {
		TestOnTimerAnOnExit onTimer = new TestOnTimerAnOnExit();
		try {
			onTimer.execute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void execute() throws InterruptedException {
		int count = 0;
		while (count < 100) {
			TimeUnit.SECONDS.sleep(1);
			if (count % 3 == 0) {
				MyInterface myInterface = new MyInterfaceImplA();
				myInterface.test();
			}
			if (count % 3 == 1) {
				MyInterface myInterface = new MyInterfaceImplB();
				myInterface.test();
			}
			if (count % 3 == 2) {
				MyInterface myInterface = new MyInterfaceSub();
				myInterface.test();
			}
			count++;
		}
	}
}
