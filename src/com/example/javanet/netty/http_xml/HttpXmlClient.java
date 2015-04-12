package com.example.javanet.netty.http_xml;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class HttpXmlClient {
	public static void main(String[] args) throws Exception {
		new HttpXmlClient().connect(1223);
	}
	
	public void connect(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 将二进制码流解码成为HTTP的应答消息
							sc.pipeline().addLast("http-decoder",new HttpResponseDecoder());
							// 复制将1个HTTP请求消息的多个部分合并成一条完整的HTTP消息
							sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
						
							// XML解码器
							sc.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true));
							
							// 注意顺序，编码的时候是按照【从尾到头】的顺序调度执行的。
							sc.pipeline().addLast("http-encoder", new HttpRequestEncoder());
							// XML编码器
							sc.pipeline().addLast("xml-encoder" , new HttpXmlRequestEncoder());
							
							sc.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandler());
						}
					});
			ChannelFuture cf = b.connect(new InetSocketAddress(port)).sync();
			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
