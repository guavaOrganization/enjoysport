package com.example.javanet.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class DatagramSocketTest {
	public static void main(String[] args) {
		// 这与TCP socket有很大不同。你只需要指定要连接的一个本地端口。Socket并不知道远程主机或地址是什么。
		// 通过指定端口0，就是在请求Java为你随机选择一个可用的端口，就像是服务器Socket一样。
		try (DatagramSocket socket = new DatagramSocket(0)){
			// DatagramSocket : 此类表示用来发送和接收数据报包的套接字。
			// 数据报套接字是包投递服务的发送或接收点。每个在数据报套接字上发送或接收的包都是单独编址和路由的。
			// 从一台机器发送到另一台机器的多个包可能选择不同的路由，也可能按不同的顺序到达。
			// 通过指定端口0，就是在请求Java为你随机选择一个可用的端口，就像是服务器Socket一样
			socket.setSoTimeout(10000);// 10s
			// 请求数据包
			InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 1223);
			DatagramPacket request = new DatagramPacket(new byte[1], 1, socketAddress);
			byte[] data = new byte[1024];
			DatagramPacket response = new DatagramPacket(data, data.length);
			
			socket.send(request);
			socket.receive(response);// 无限阻塞，直到一个UDP数据报到达

			String result = new String(response.getData(), 0, response.getLength(), "UTF-8");
			System.out.println(result);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
