package com.example.javanet.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
	public static void main(String[] args) throws Exception {
		new WebSocketServer().run(1223);
	}
	
	public void run(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							ChannelPipeline pipeline = sc.pipeline();
							// 将请求和应答消息编码或者解码为HTTP消息
							pipeline.addLast("http-codec" , new HttpServerCodec());
							// 将HTTP消息的多个部分组合成一条完整的HTTP消息
							pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
							// 向客户端发送HTML5文件，它主要用于支持浏览器和服务端进行WebServer通信
							pipeline.addLast("http-chunked", new ChunkedWriteHandler());
							pipeline.addLast("handler", new WebSocketServerHandler());
						}
					});
			System.out.println("Web socket server started at port " + port + ".");
			System.out.println("Open your browser and navigate to http://localhhost:" + port + "/");
			Channel ch = sb.bind(port).sync().channel();
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
