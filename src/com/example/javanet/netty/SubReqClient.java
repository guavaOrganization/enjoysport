package com.example.javanet.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubReqClient {
	public static void main(String[] args) throws Exception {
		new SubReqClient().connect(1223, "127.0.0.1");
	}
	
	public void connect(int port, String host) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 禁止对类加载器进行缓存，它在基于OSGi的动态模块化编程中经常使用。
							// 由于OSGi的bundle可以进行热部署和热升级，当某个bundle升级后，它对应的类加载器也将一起升级，因此在动态模块化编程中，很少对类加载器进行缓存，因为它随时可能会发生变化
							sc.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
							sc.pipeline().addLast(new ObjectEncoder());
							sc.pipeline().addLast(new SubReqClientHandler());
						}
					});
			ChannelFuture cs = b.connect(host, port).sync();
			cs.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
