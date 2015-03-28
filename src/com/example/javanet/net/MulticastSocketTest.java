package com.example.javanet.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class MulticastSocketTest {
	public static void main(String[] args) {
		// receive();
		send();
		
		// 一个MulticastSocket可以加入多个组播组。组播组中的成员信息存储在组播路由器中，而不是对象中。在这里，你要使用存储在入站数据报的地址来确定包要发往什么地址。
		// 相同机器甚至相同Java程序中的多个组播Socket可以加入相同的组。如果是这样，各个Socket会接受到发往到该组并到达本地主机的一个完整的数据副本。
	}
	
	public static void send() {
		try {
			// 可以向构造函数传入null来创建一个绑定的Socket，之后在用bind()方法进行连接。
			// 有些Socket选项只能在绑定Socket之前设置，要设置这样一些Socket选项，这个构造函数就很有用。
			// 例如，下面的代码会创建一个禁用SO_REUSEADDR的组播Socket(默认情况下对组播Socket一般是启用的)
			MulticastSocket ms = new MulticastSocket(null);
			ms.setReuseAddress(false);
			SocketAddress sa = new InetSocketAddress(4000);
			ms.bind(sa);
			// 随机端口
			// MulticastSocket ms = new MulticastSocket();
			// 向组播地址发送数据与向单薄地址发送UDP数据很相似。不需要加入组播组就可以向组播地址发送数据。
			// 可以创建一个新的DatagramPacket，在包中填充数据和组播组的地址，将这个包传入send()方法
			InetAddress ia = InetAddress.getByName("experiment.mcase.net");
			byte[] data = "Here's some multicastData\r\n".getBytes("UTF-8");
			// 对于操作系统而言，组播Socket就是数据报Socket，所以MulticastSocket不能占用已被DatagramSocket占用的端口，反之亦然。
			DatagramPacket dp = new DatagramPacket(data, data.length, ia, 4000);
			ms.send(dp);
			ms.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void receive(){
		MulticastSocket ms = null; 
		InetAddress group = null;
		try {
			ms = new MulticastSocket(1224);
			// 使用joinGroup()方法加入到一个组播组
			// 这回通知你与服务器之间路径上的路由器开始发送数据，并告诉本地主机要将你的IP包发往组播组
			group = InetAddress.getByName("224.2.2.2");
			ms.joinGroup(group);
			// 一旦加入到组播组，就可以像DatagramSocket一样接受UDP数据。要创建一个DatagramPacket，用一个自己数组作为数据缓冲区，再进入一个循环。
			// 在循环中要通过调用继承自DatagramSocket类的receive()方法接收数据：
			byte[] buffer = new byte[8192];
			while (true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);// 阻塞
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 不再希望接受数据时，通过调用这个Socket的leaveGroup()方法离开组播组。然后可以用继承自DatagramSocket的close()方法关闭该Socket
			try {
				ms.leaveGroup(group);
				ms.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
