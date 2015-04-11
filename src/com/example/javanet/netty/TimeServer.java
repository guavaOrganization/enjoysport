package com.example.javanet.netty;

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

public class TimeServer {
	public void bind(int port) throws Exception {
		// 配置服务端的NIO线程组
		// 包含了一组NIO线程，专门用于网络事件的处理，实际上它们就是Reactor线程组。
		// 这里创建连个的原因是一个用于服务端接受客户端的连接，另一个用于进行SocketChannel的网络读写。
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// ServerBootstrap是Nety用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度。
			ServerBootstrap b = new ServerBootstrap();
			// 将两个NIO线程组当作入参传递到ServerBootstrap中。
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) // 对应Java NIO中的ServerSocketChannel类
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler()); // 绑定I/O事件处理类，用于处理网络I/O时间，例如记录日志，对消息进行编解码等。
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();// bind方法绑定监听端口。调用同步阻塞方法sync等待绑定操作完成。
			// ChannelFuture类似于java.util.concurrent.Future,主要用于异步操作的通知回调
			
			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();// 阻塞，等待服务端链路关闭之后main函数才退出
		} finally {
			bossGroup.shutdownGracefully();// 关闭，释放跟shutdownGracefully相关联的资源。
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			// arg0.pipeline().addLast(new TimeServerHandler());
			// 粘包异常案例演示
			// arg0.pipeline().addLast(new TimeServerHandler_StickPackage());
			
			// 通过编解码器解决半包读写问题
			// 新增两个编码器：LineBasedFrameDecoder和StringDecoder
			
			// LineBasedFrameDecoder的工作原理是它依次遍历ByteBuf中的可读字节，判断看是否有"\n"或者"\r\n"，
			// 如果有，就以此位置为结束位置，从可读索引到结束位置区间的字节就组成了一行。它是以换行符为结束标识的解码器，支持携带结束符或者不懈怠结束符两种编码方式。
			// 同事支持配置单行的最大长度。如果连续读取到最大长度后仍然没有发现换行符，就会抛出异常，同事忽略掉之前独到的异常码流
			arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
			arg0.pipeline().addLast(new StringDecoder());
			arg0.pipeline().addLast(new TimeServerHandler_StickPackage_Solutions());
		}
	}
	
	public static void main(String[] args) throws Exception {
		new TimeServer().bind(1223);
	}
}
