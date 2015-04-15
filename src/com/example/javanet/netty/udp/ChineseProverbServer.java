package com.example.javanet.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	中国谚语服务器
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ChineseProverbServer {
	public static void main(String[] args) throws Exception {
		new ChineseProverbServer().run(1223);
	}
	
	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)// 支持广播
					// 相对于TCP通信，UDP不存在客户端和服务端的实际连接，因此不需要为连接(ChannelPipeline)设置handler
					// 对于服务端，只需要设置启动辅助类的handler即可
					.handler(new ChineseProverbServerHandler());
			b.bind(port).sync().channel().closeFuture().await();
		} finally {
			group.shutdownGracefully();
		}
	}
}
