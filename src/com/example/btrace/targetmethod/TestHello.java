package com.example.btrace.targetmethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestHello {
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(20000);
		TestHello hello = new TestHello();
		hello.test(new Random(47).nextInt(9) + 1);
	}
	
	private Map<String, String> model = new HashMap<String, String>();
	String tips = "您的年龄是：";
	public boolean test(int age) {
		put(age, tips);
		String variable1 = "默认提示信息:你输入的参数是：" + age;
		String variable2 = "欢迎观临网上乐园";
		model.put("variable1", variable1);
		model.put("variable2", variable2);
		if (age >= 1 && age <= 5) {
			variable1 = "你的输入参数介于1和5之间";
			variable2 = "欢迎小朋友玩益智类游戏";
			model.put("variable1", variable1);
			model.put("variable2", variable2);
			return true;
		} else if (age > 5 && age <= 10) {
			variable1 = "你的输入参数介于5和10之间";
			variable2 = "欢迎小朋友进入英语游乐场";
			model.put("variable1", variable1);
			model.put("variable2", variable2);
			return false;
		} else {
			return true;
		}
	}

	private void put(int age, String tips) {
		System.out.println(tips + age);
	}
}
