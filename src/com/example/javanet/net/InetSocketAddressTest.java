package com.example.javanet.net;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetSocketAddressTest {
	public static void main(String[] args) throws UnknownHostException {
		// 根据主机名和端口号创建[未解析的套接字地址]。
		// 不会尝试将主机名解析为 InetAddress。将地址标记为未解析。
		// 有效端口值介于 0 和 65535 之间。端口号 zero 允许系统在 bind 操作中挑选暂时的端口。
		// 未解析
//		InetSocketAddress socketAddress = InetSocketAddress.createUnresolved("www.taobao.com", 8080);
		// 已解析 
		InetSocketAddress socketAddress = new InetSocketAddress("www.taobao.com", 8080);
		// 已解析
//		InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("www.taobao.com"), 8080);
		System.out.println(socketAddress.getHostName());
		System.out.println(socketAddress.getHostString());
		System.out.println(socketAddress.getPort());
		// 获取 InetAddress。
		// 返回：InetAdress；如果未解析，则返回 null。
		System.out.println(socketAddress.getAddress());
		// 检查是否已解析地址。
		// 返回：如果无法将主机名解析为 InetAddress，则返回 true。
		System.out.println(socketAddress.isUnresolved());
	}
}
