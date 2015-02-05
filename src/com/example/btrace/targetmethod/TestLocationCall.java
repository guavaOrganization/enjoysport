package com.example.btrace.targetmethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.example.btrace.targetmethod.sub.MyInterfaceSub;

public class TestLocationCall {
	public static void main(String[] args) {
		Random rand = new Random();
		int sleepTime = rand.nextInt(10) + 15;
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
			TestLocationCall call = new TestLocationCall();
			call.call("calling TestLocationCall call,from TestLocationCall(main)....");			
			
			call.testLocationCall();
			TimeUnit.SECONDS.sleep(2);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public String call(String text) {
		return "TestLocationCall.call的返回值为：" + text;
	}

	public String testLocationCall() {
		MyInterface interfaceSub = new MyInterfaceSub();
		interfaceSub.call("calling MyInterfaceSub call,from TestLocationCall(testLocationCall)....");
	
		call("calling TestLocationCall call,from TestLocationCall(testLocationCall)....");

		MyInterface interfaceImplA = new MyInterfaceImplA();
		interfaceImplA.call("calling MyInterfaceImplA call,from TestLocationCall(testLocationCall)....");
		return "testLocationCall";
	}
}
