package com.example.javanet.netty.customprotocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyServer {
	public void bind() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
						sc.pipeline().addLast(new NettyMessageEncoder());
						
						sc.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
						
						sc.pipeline().addLast(new LoginAuthRespHandler());
						sc.pipeline().addLast(new HeartBeatRespHandler());
					}
				});
		ChannelFuture cf = sb.bind(1223).sync();
		cf.channel().closeFuture().sync();
	}
	
	public static void main(String[] args) throws Exception {
		new NettyServer().bind();
	}
}
