package com.example.javanet.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class SecureSocketTest {
	public static void main(String[] args) {
		try {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			Socket socket = factory.createSocket("www.baidu.com", 443);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			String s;
			while (!(s = br.readLine()).equals(""))
				System.out.println(s);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
