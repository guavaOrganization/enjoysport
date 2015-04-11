package com.example.javanet.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	序列化/反序列化
 * 		Protobuf
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class SubReqServer_Protobuf {
	public static void main(String[] args) throws Exception {
		new SubReqServer_Protobuf().bind(1223);
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
							// ProtobufVarint32FrameDecoder : 主要用于半包处理
							sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							// ProtobufDecoder : 解码器，参数是com.google.protobuf.MessageLite，实际上就是告诉ProtobufDecoder需要解码的目标类是什么，
							// 否则仅仅从字节数组中是无法判断要解码的目标类型信息的。
							sc.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
							
							sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							sc.pipeline().addLast(new ProtobufEncoder());
							sc.pipeline().addLast(new SubReqServerHandler_Protobuf());
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
