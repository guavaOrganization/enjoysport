package com.example.javalang;

import java.util.Map;

public class SystemTest {
	public static void main(String[] args) {
		for (Map.Entry<Object, Object> entry : System.getProperties()
				.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
	}
}
