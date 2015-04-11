package com.example.javanet.netty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class PerformTestUserInfo {
	public static void main(String[] args) throws IOException {
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("Welcome to Netty");
		int loop = 1000000;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(info);
			os.flush();
			os.close();
			byte[] b = bos.toByteArray();
			bos.close();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("The JDK Serializable cost time is : " + (endTime - startTime) + " ms");
		System.out.println("-----------------------");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			byte[] b = info.codeC();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Thebyte array serializable cost time is : " + (endTime - startTime) + " ms");

	}
}
