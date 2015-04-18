package com.example.javanet.netty.customprotocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	EventLoopGroup group = new NioEventLoopGroup();
	private static final int PORT = 1224;
	private static final String HOST = "127.0.0.1";
	
	public static void main(String[] args) {
		try {
			new NettyClient().connect(1223, HOST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect(int port, String host) throws Exception {
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel sc) throws Exception {
							// 为了防止由于单条消息过大导致的内存溢出或者畸形码流导致解码错位引起内存分配失败，我们对单条消息最大长度进行了上限限制
							sc.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
							sc.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
							
							sc.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
							
							sc.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
							sc.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
						}
					});
			
			ChannelFuture future = b.connect(new InetSocketAddress(host, port), new InetSocketAddress(HOST, PORT)).sync();
			future.channel().closeFuture().sync();
		} finally {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						try {
							connect(PORT, HOST);// 发起重连操作
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
