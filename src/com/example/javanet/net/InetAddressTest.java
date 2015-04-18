package com.example.javanet.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	InetAddress类没有公共构造函数。实际上，InetAddress有一些静态工厂方法，可以连接到DNS服务器来解析主机名。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class InetAddressTest {
	private static final String TEST_HOST = "www.taobao.com";
	
	// @Test
	public void testMethod_getName() throws UnknownHostException {
		// 此方法会建立与本地DNS服务器的一个连接，来查找名字和数字地址
		// 如果DNS服务器找不到这个地址，这个方法会抛出UnknownHostException异常，这是IOException的一个子类
		InetAddress address1 = InetAddress.getByName(TEST_HOST);
		System.out.println(address1);
		/**
		 * 获取此IP地址的主机名。 如果此 InetAddress是用主机名创建的，则记忆并返回主机名；
		 * 否则，将执行反向名称查找并基于系统配置的名称查找服务返回结果。
		 * 如果需要查找名称服务，则调用getCanonicalHostName。
		 */
		System.out.println("address1's HostName : " + address1.getHostName());
		// 获取此 IP 地址的完全限定域名。最大努力方法，意味着根据底层系统配置可能不能返回 FQDN。
		// getHostName()只是在不知道主机名时才会联系DNS
		// getCanonicalHostName()知道主机名时也会联系DNS，可能会替换缓存的主机名
		System.out.println("address1's CanonicalHostName : " + address1.getCanonicalHostName());

		// 按IP地址反向查找。
		InetAddress address2 = InetAddress.getByName("121.9.212.151");
		System.out.println(address2.getCanonicalHostName());
		System.out.println("address1's HostName : " + address2.getHostName());
		
		// 如果主机为 null，则返回表示回送接口地址的 InetAddress
		InetAddress address3 = InetAddress.getByName(null);
		System.out.println("address3 : " + address3);
	}

	@Test
	public void testMethod_getAllName() throws UnknownHostException {
		// InetAddress.getByName()返回哪一个地址是不确定的
		
		// 在给定主机名的情况下，根据系统上配置的名称服务返回其 IP 地址所组成的数组。
		// 主机名可以是机器名（如 "java.sun.com"），也可以是其IP地址的文本表示形式。如果提供字面值IP地址，则仅检查地址格式的有效性。
		InetAddress[] addresses = InetAddress.getAllByName(TEST_HOST);
		for (InetAddress address : addresses) {
			System.out.print(address);
			if (address.getAddress().length == 4)
				System.out.println(",IP版本为IPv4");
			else if (address.getAddress().length == 6)
				System.out.println(",IP版本为IPv6");
		}
	}
	
	// @Test
	public void testMethod_getLocalHost() throws UnknownHostException {
		// getLocalHost()方法会为运行这个代码的主机返回一个InetAddress对象
		// 这个方法尝试连接DNS来得到一个真正的主机名和IP地址，如"www.taobao.com"和"121.14.89.251"。
		// 不过如果失败，它就会返回回送地址，即主机名"localhost"和点分四段地址"127.0.0.1"
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address);
	}

	// 127.0.0.1是本地回送地址
	//224.0.0.0到239.255.255.255范围内的IPv4地址是组播地址，可以同事发送到多个订购的主机
}
