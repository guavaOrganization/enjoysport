package com.example.javanet.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketHandler implements Runnable {

	private Socket socket;

	public SocketHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			Reader reader = new InputStreamReader(new BufferedInputStream(socket.getInputStream()), "UTF-8");
			Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder(80);
			while (true) {
				int c = reader.read();
				if(c == '\r' || c == '\n' || c == -1)
					break;
				sb.append((char)c);
			}
			System.out.println(sb.toString());
			
			System.out.println("开始返回数据...");
			Date current = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			// 需要用回车/换行来结束这一行。
			writer.write("现在是北京时间：" + sdf.format(current) + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
