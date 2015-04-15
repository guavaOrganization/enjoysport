package com.example.javanet.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		String response = packet.content().toString(CharsetUtil.UTF_8);
		System.out.println(response);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
