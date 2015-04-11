package com.example.javanet.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {
	public static void main(String[] args) throws Exception {
		new HttpFileServer().run(1223, DEFAULT_URL);
	}
	
	private static final String DEFAULT_URL = "/src/com/example";

	public void run(final int port, final String url) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 请求消息解码器
							sc.pipeline().addLast("http-decoder", new HttpRequestDecoder());
							// HttpObjectAggregator的作用是将多个消息转换为单一的FullHttpRequest和FullHttpResponse，
							// 原因是Http解码器在每个Http消息中会生成多个消息对象。
							// HttpRequest/HttpResponse
							// HttpContent
							// LastHttpContent
							sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
							// 对Http响应消息进行编码
							sc.pipeline().addLast("http-encoder", new HttpResponseEncoder());
							// 支持异步发送大的码流(例如大的文件传输)，但不占用过多的内存，防止发生Java内存溢出错误
							sc.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
							sc.pipeline().addLast("fileServerHandler",new HttpFileServerHandler(url));
						}
					});
			
			ChannelFuture cf = sb.bind("127.0.0.1", port).sync();
			System.out.println("HTTP 文件目录服务器启动，网址是 : http://127.0.0.1:" + port + url);
			cf.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
