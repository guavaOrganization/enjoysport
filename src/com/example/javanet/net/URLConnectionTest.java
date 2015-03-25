package com.example.javanet.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class URLConnectionTest {
	@Test
	public void testMethod_openConnection() {
		try {
			URL url = new URL("http://www.baidu.com/");
			// URLConnection类近忧的一个构造函数为保护类型：
			// protected URLConnection(URL url)
			// 因此，除非派生URLConnection的子类来处理新的URL类型(即编写一个协议处理器)
			// 否则要通过调用URL类的openConnection()方法来创建这样一个对象。
			URLConnection connection = url.openConnection();
			// 返回响应主体的MIME内容类型
			String contentType = connection.getContentType();
			//  告诉你内容中有多少字节
			int contentLength = connection.getContentLength();
			System.out.println("contentLength=" + contentLength);
			String encoding = "iso-8859-1";
			// charset用来标识文档的字符编码方式
			if (null != contentType && contentType.indexOf("charset") != -1) {
				encoding = contentType.substring(contentType.indexOf("charset") + 8);
			}
			System.out.println("encoding=" + encoding);
			
			// 指出内容是如何编码的
			System.out.println(connection.getContentEncoding());
			// 注意：内容编码方式与字符编码方式不同。字符编码方式由Content-Type首部或文档内部的信息确定，住处如何将字符编码为字节。
			// 内容编码方式则指出字节如何编码为其他字节
			
			InputStream is = new BufferedInputStream(connection.getInputStream());
			Reader reader = new InputStreamReader(is, encoding);
			int c;
//			while ((c = reader.read()) != -1)
//				System.out.println((char) c);
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
