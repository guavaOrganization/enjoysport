package com.example.javanet.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class DatagramServerSocketTest {
	public static void main(String[] args) {
		try(DatagramSocket socket = new DatagramSocket(1223)) {
			// 此类表示数据报包。
			// 【数据报包用来实现无连接包投递服务】。每条报文仅根据该包中包含的信息从一台机器路由到另一台机器。
			// 从一台机器发送到另一台机器的多个包可能选择不同的路由，也可能按不同的顺序到达。不对包投递做出保证
			DatagramPacket request = new DatagramPacket(new byte[1024], 0, 1024);
			// 这个调用会无限阻塞，知道一个UDP数据包到达端口1223.如果有UDP数据包到达，Java就会将这个数据填充到byte数组中，receive()方法返回
			socket.receive(request);
			
			String dayTime = new Date().toString();
			byte[] data = dayTime.getBytes("UTF-8");
			System.out.println(request.getPort());
			DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
			socket.send(response);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
