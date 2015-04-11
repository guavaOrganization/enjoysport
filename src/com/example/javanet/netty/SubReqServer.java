package com.example.javanet.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {
	public static void main(String[] args) throws Exception {
		new SubReqServer().bind(1223);
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
							// ObjectDecoder : 负责对实现Serializable的POJO对象进行解码，有多个构造函数，支持不同的ClassResolver。
							// 在此我们使用weakCachingConcurrentResolver创建线程安全的{@link WeakReferenceMap}对类加载器进行缓存，
							// 它支持多线程并发访问，当虚拟机内存不足时，会释放缓存中的内存，防止内存泄漏
							// 为了防止异常码流和解码错位导致的内存溢出，这里将单个对象最大序列化后的字节数组长度设置为1M
							sc.pipeline().addLast(new ObjectDecoder(1024 * 1204,
							   ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
							// ObjectEncoder : 可以在消息发送的时候自动将实现Serializable的POJO对象进行编码，因此用户无须亲自对对象进行手工序列化，
							// 只需要关注自己的业务逻辑处理即可，对象序列化和反序列化都由Netty的对象编解码器搞定。
							sc.pipeline().addLast(new ObjectEncoder());
							sc.pipeline().addLast(new SubReqServerHandler());
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
