package com.example.javanet.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	DelimiterBasedFrameDecoder：
 * 		自动完成以分隔符做结束标识的消息的解码
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class EchoServer_DBFD {
	public static void main(String[] args) throws Exception {
		new EchoServer_DBFD().bind(1223);
	}
	
	public void bind(int port) throws Exception {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							// 1024为单条消息的最大长度，当达到该成都后仍然没有查找到分隔符，就抛出TooLongFrameException异常，
							// 防止由于异常码流缺失分隔符导致的内存溢出，这是Netty解码器的可靠性保护
							// delimiter 就是分隔符缓冲对象
							sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							sc.pipeline().addLast(new StringDecoder());
							sc.pipeline().addLast(new EchoServerHandler_DBFD());
						}
					});
			
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
