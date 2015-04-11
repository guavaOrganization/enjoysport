package com.example.javanet.netty;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger .getLogger(TimeClientHandler.class.getName());
	private final ByteBuf firstMessage;

	public TimeClientHandler() throws UnsupportedEncodingException {
		byte[] req = "QUERY TIME ORDER".getBytes("UTF-8");
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	
	@Override
	// 当客户端和服务端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法，发送查询时间的指令给服务端
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstMessage);// 调用writeAndFlush方法将请求消息发送到服务端
	}
	
	@Override
	// 当服务端返回应答消息时，channelRead方法被调用。
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is : " + body);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());
		ctx.close();
	}
}
