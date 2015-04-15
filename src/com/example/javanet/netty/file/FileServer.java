package com.example.javanet.netty.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	可通过telnet访问：
 *  E:\guava\workspace\enjoysport\binding.xml
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class FileServer {
	public static void main(String[] args) throws Exception {
		new FileServer().run(1223);
	}

	public void run(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc)throws Exception {
							sc.pipeline().addLast(
									// 将文件内容编码为字符串，因为我们例子中的binding.xml的内容是纯文本的，所以在CMD控制台可见
									new StringEncoder(CharsetUtil.UTF_8),
									// 按照回车换行符对数据报进行解码
									new LineBasedFrameDecoder(1024),
									new StringDecoder(CharsetUtil.UTF_8),
									new FileServerHandler());
						}
					});

			ChannelFuture cf = sb.bind(port).sync();
			System.out.println("Start file server at port : " + port);
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
