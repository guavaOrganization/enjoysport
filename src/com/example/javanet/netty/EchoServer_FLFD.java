package com.example.javanet.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 *  FixedLengthFrameDecoder：
 *  	固定长度解码器，它能够按照指定的长度对消息进行自动解码。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class EchoServer_FLFD {
	public static void main(String[] args) throws Exception {
		new EchoServer_FLFD().bind(1223);
	}
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 利用FixedLengthFrameDecoder解码器，无论一次接收到多少数据报，它都会按照构造函数中设置的固定长度进行解码。
							// 如果是半包消息，FixedLengthFrameDecoder会缓存半包消息并等待下个包到达后进行拼包，知道读取到一个完整的包
							sc.pipeline().addLast(new FixedLengthFrameDecoder(20));
							sc.pipeline().addLast(new StringDecoder());
							sc.pipeline().addLast(new EchoServerHandler_FLFD());
						}
					});
			
			ChannelFuture cf = sb.bind(port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
