package com.example.javaio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DisposePattern {
	public static void main(String[] args) {
		OutputStream os = null;
		try {
			os = new FileOutputStream("/temp/data");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(os != null)
				try {
					os.close(); // 这种技术有时称为释放模式
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		// JDK 7引入了"带资源的try"构造(try with resources)
		try (OutputStream out = new FileOutputStream("/temp/data")){// 现在不需要finally字句，Java会对try块参数中声明的所有AutoCloseable对象自动调用close()
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
