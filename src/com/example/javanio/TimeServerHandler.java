package com.example.javanio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class TimeServerHandler implements Runnable {
	private Socket socket;

	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			String currentTime = null;
			String body = null;
			while (true) {
				// 当对Socket的输入流进行读取操作的时候，它会一直阻塞下去，直到发生如下三种情况：
				// a、有数据可读;
				// b、可用数据已经读取完毕;
				// 发生空指针或者I/O异常;
				body = in.readLine();
				if(body == null)
					break;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH小时mm分ss秒");
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? sdf.format(new Date(System.currentTimeMillis())) : "BAD ORDER";
				out.println(currentTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
