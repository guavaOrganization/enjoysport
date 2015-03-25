package com.example.javanet.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	NetworkInterface类表示一个本地IP地址。这可以是一个吴丽接口，如额外的以太网卡(常见于防火墙和路由器)，也可以是一个虚拟接口，与
 * 	机器的其他IP地址绑定到同一个吴丽硬件。
 * 
 * 	由于NetworkInterface对象表示吴丽硬件和虚拟地址，所以不能任意构造。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class NetworkInterfaceTest {
//	@Test
	public void testMethod_getByName() {
		// getByName()方法返回一个NetworkInterface对象，表示有指定名字的网络接口。
		// 如果没有这样一个接口，就返回null。如果在查找相关网络接口时底层网络栈遇到问题，会抛出一个SocketException异常，不过这种情况不太可能发生
		
		// 名字的格式与平台有关。在典型的UNIX系统上，以太网接口名的形式为eth0、eth1等。
		// 本地回送地址的名字可能类似与"1o"。在Windows上，名字是类似"CE31"和"ELX100"的字符串，取自这个特定网络接口的厂商名和网络模型名。
		try {
			NetworkInterface ni = NetworkInterface.getByName("lo");
			// 获取此网络接口的名称。
			System.out.println("getName() return : " + ni.getName());
		} catch (SocketException e) {
			e.printStackTrace();
		};
	}
	
//	@Test
	public void testMethod_getByInetAddress() throws UnknownHostException, SocketException {
		// getByInetAddress()方法返回一个NetworkInterface对象，表示与指定IP地址绑定的网络接口。如果本地主机上没有网络几口与这个IP地址绑定，就返回null。
		InetAddress local = InetAddress.getByName("127.0.0.1");
		NetworkInterface ni = NetworkInterface.getByInetAddress(local);
		System.out.println(ni.getName());
	}
	
	@Test
	public void testMethod_getNetworkInterfaces() throws SocketException{
		// 列出本地主机上的所有网络接口
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface ni = interfaces.nextElement();
			StringBuilder sb = new StringBuilder(ni.getName()).append(" 对应的IP地址为：");
			// getInetAddresses() 返回一个具有绑定到此网络接口全部或部分 InetAddress 的 Enumeration。
			// 一个网络接口可以绑定多个IP地址
			Enumeration<InetAddress> enumeration = ni.getInetAddresses();
			while(enumeration.hasMoreElements()){
				sb.append(" ").append(enumeration.nextElement().getHostAddress());
			}
			System.out.println(sb.toString());
		}
	}
}
