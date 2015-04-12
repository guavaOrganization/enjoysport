package com.example.javanet.netty.http_xml;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpXmlServer {
	public static void main(String[] args) throws Exception {
		new HttpXmlServer().run(1223);
	}

	public void run(final int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 将二进制码流解码成为HTTP的请求消息
							sc.pipeline().addLast("http-decoder",new HttpRequestDecoder());
							sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
							sc.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true));
							
							// 编码器是倒序执行的
							sc.pipeline().addLast("http-encoder",new HttpResponseEncoder());
							sc.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder());
						
							sc.pipeline().addLast("xmlServerHandler", new HttpXmlServerHandler());
						}
					});
			ChannelFuture cf = sb.bind(new InetSocketAddress(port)).sync();
			System.out.println("HTTP 订购服务器启动，网址是 ： http://localhost:" + port);
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
