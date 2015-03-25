package com.example.javaio;

import java.io.Closeable;
import java.io.IOException;
/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Closeable是可以关闭的数据源或目标。调用close方法可释放对象保存的资源（如打开文件）。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MyCloseable implements Closeable {
	private String fileName;

	@Override
	public void close() throws IOException {
		System.out.println("Closeable implement....");
	}

	public MyCloseable(String fileName) {
		this.fileName = fileName;
	}
	
	public void sayHello() {
		System.out.println("Hello," + fileName);
	}
	
	public static void main(String[] args) {
		// MyCloseable closeable = new MyCloseable("Java编程思想.pdf");
		try (MyCloseable closeable = new MyCloseable("Java编程思想.pdf")) {// Java 7开始支持
			closeable.sayHello();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally...");
		}
	}
}
