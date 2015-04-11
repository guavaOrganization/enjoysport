package com.example.javanet.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient_Protobuf {
	public static void main(String[] args) throws Exception {
		new SubReqClient_Protobuf().connect(1223, "127.0.0.1");
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
							// 解码设置
							// ProtobufVarint32FrameDecoder处理读半包解码器
							sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
							// ProtobufDecoder仅仅负责解码，不支持读半包
							// LengthFieldBasedFrameDecoder : Netty提供的通用半包解码器
							// ByteToMessageDecoder : 自己处理半包信息
							sc.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
							sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
							// 编码设置
							sc.pipeline().addLast(new ProtobufEncoder());
							
							sc.pipeline().addLast(new SubReqClientHandle_Protobuf());
						}
					});
			ChannelFuture cs = b.connect(host, port).sync();
			cs.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
