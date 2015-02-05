package com.example.btrace.targetmethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.example.btrace.targetmethod.sub.MyInterfaceSub;

public class TestOnMethod {
	public static void main(String[] args) {
		TestOnMethod method = new TestOnMethod();
		Random rand = new Random();
		int sleepTime = rand.nextInt(20) + 10;
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
			method.execute(sleepTime);
			method.execute();
			
			method.test("test() from TestOnMethod");

			MyInterface interfaceA = new MyInterfaceImplA();
			interfaceA.test();
			
			MyInterface interfaceB = new MyInterfaceImplB();
			interfaceB.test();
			
			MyInterface interfaceSub = new MyInterfaceSub();
			interfaceSub.test();
			
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void test(String text){
		System.out.println(text);
	}
	
	public void execute() {
		System.out.println("execute().........");
		User user = new User();
		user.setId(1);
		user.setName("八两俊");
		
		User newUser = new User(2, "猪古丽");
		System.out.println(newUser.getName());
	}

	public int execute(int sleepTime) {
		System.out.println("Sleep Time Is " + sleepTime);
		return sleepTime;
	}
}
