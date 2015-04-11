package com.example.javanet.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	public void connect(int port, String host) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();// 客户端辅助启动类
			b.group(group).channel(NioSocketChannel.class)
			 .option(ChannelOption.TCP_NODELAY, true)
			 .handler(new ChannelInitializer<SocketChannel>() {
				 @Override
				 public void initChannel(SocketChannel sc) throws Exception {
					 // 将ChannelHandler设置到ChannelPipeline中，用于处理网络I/O事件
					 // sc.pipeline().addLast(new TimeClientHandler());
					 
					 // 粘包异常案例
					 // sc.pipeline().addLast(new TimeClientHandler_StickPackage());
					 
					 // 通过编解码器解决半包读写问题
					 // 新增两个编码器：LineBasedFrameDecoder和StringDecoder
					 sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
					 sc.pipeline().addLast(new StringDecoder());
					 sc.pipeline().addLast(new TimeClientHandler_StickPackage_Solutions());
				 };
			 });
			// 发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();// 调用sync同步方法等待连接成功
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();// 优雅退出，释放NIO线程组
		}
	}

	public static void main(String[] args) throws Exception {
		new TimeClient().connect(1223, "127.0.0.1");
	}
}
