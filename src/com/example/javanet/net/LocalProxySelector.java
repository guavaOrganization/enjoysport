package com.example.javanet.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	连接到URL引用的网络资源时选择要使用的代理服务器（如果有）。代理选择器是此类的具体子类，通过调用setDefault方法注册。当前注册的代理选择器可以通过调 getDefault方法获得。
 * 	例如，注册代理选择器时，URLConnection类的子类应该调用每个URL请求的select方法，这样代理选择器便可以决定应该使用直接连接还是代理连接。
 * 	select方法返回使用首选连接方法的连接上的迭代器。如果无法建立到代理（PROXY或 SOCKS）服务器的连接，则调用方应该调用代理选择器的 connectFailed方法来通知代理选择器，
 * 	代理服务器不可用。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class LocalProxySelector extends ProxySelector {
	private List<URI> failed = new ArrayList<URI>();

	// 调用此方法来指示无法建立到代理/socks 服务器的连接。
	@Override
	public void connectFailed(URI paramURI, SocketAddress paramSocketAddress, IOException paramIOException) {
		failed.add(paramURI);
	}

	@Override
	public List<Proxy> select(URI uri) {
		List<Proxy> result = new ArrayList<Proxy>();
		if (failed.contains(uri) || !"http".equalsIgnoreCase(uri.getScheme())) {
			result.add(Proxy.NO_PROXY);
		} else {
			SocketAddress proxyAddress = new InetSocketAddress("proxy.example.com", 8080);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
			result.add(proxy);
		}
		return result;
	}
	
	public static void main(String[] args) {
		ProxySelector proxySelector = ProxySelector.getDefault();
		System.out.println(proxySelector);
		ProxySelector.setDefault(new LocalProxySelector());
		proxySelector = ProxySelector.getDefault();
		System.out.println(proxySelector);
	}
	
	@Override
	public String toString() {
		return "LocalProxySelector";
	}
}
