package com.example.javanet.netty.udp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class ChineseProverbClient {
	public static void main(String[] args) throws Exception {
		new ChineseProverbClient().run(1223);
	}

	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			// 创建UDP Channel和设置支持光波属性等与服务端完全一致。
			// 由于不需要和服务端简历链路，UDP Channel创建完成之后，客户端就要主动发送广播消息；
			// TCP客户端实在客户端和服务端链路简历完成之后有客户端的业务handler发送消息，这就是两者最大的区别。
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)
					.handler(new ChineseProverbClientHandler());
			Channel ch = b.bind(0).sync().channel();
			ByteBuf buf = Unpooled.copiedBuffer("谚语字典查询?", CharsetUtil.UTF_8);
			InetSocketAddress address = new InetSocketAddress("255.255.255.255", port);
			ch.writeAndFlush(new DatagramPacket(buf, address)).sync();
			if (!ch.closeFuture().await(15000)) {
				System.out.println("查询超时...");
			}
		} finally {
			group.shutdownGracefully();
		}
	}
}
