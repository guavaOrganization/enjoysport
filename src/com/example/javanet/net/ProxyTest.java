package com.example.javanet.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	Proxy类允许从Java程序中对代理服务器进行更细粒度的控制。确切地讲，它允许你为不同的远程主机选择不同的代理服务器。
 *  代理本身用java.net.Proxy类的实例来表示。仍然只有三种代理：HTTP、SOCKS和直接连接(即根本没有代理)，
 *  分别用Proxy.Type枚举中的三个常量来表示： 
 *  	a、Proxy.Type.DIRECT 
 *      b、Proxy.Type.HTTP
 *      c、Proxy.Type.SOCKS
 * </p>
 * @author 八两俊
 * @since
 */
public class ProxyTest {
	public static void main(String[] args) {
		SocketAddress address = new InetSocketAddress("10.3.1.6", 8080);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
	}
}
