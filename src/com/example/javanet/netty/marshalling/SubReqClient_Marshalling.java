package com.example.javanet.netty.marshalling;

import com.example.javanet.netty.SubReqClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SubReqClient_Marshalling {
	public static void main(String[] args) throws Exception {
		new SubReqClient_Marshalling().connect("127.0.0.1", 1223);
	}
	
	public void connect(String host, int port) throws Exception {
		EventLoopGroup elg = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(elg).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							sc.pipeline().addLast(new SubReqClientHandler());
						}
					});
			
			ChannelFuture cf = b.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			elg.shutdownGracefully();
		}
	}
}
