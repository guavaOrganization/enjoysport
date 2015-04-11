package com.example.javanet.netty;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 *  未考虑TCP粘包导致功能异常案例
 * 
 * 	一旦压力上来，或者发送大报文之后，就会存在粘包/拆包问题。
 * 
 * 	为了解决TCP粘包/拆包导致的半包读写问题，Netty默认提供了多钟编解码器用于处理半包
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class TimeClientHandler_StickPackage_Solutions extends ChannelHandlerAdapter {
	private static final Logger logger = Logger .getLogger(TimeClientHandler_StickPackage_Solutions.class.getName());
	private int counter;
	private byte[] req;
	
	public TimeClientHandler_StickPackage_Solutions() throws UnsupportedEncodingException {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes("UTF-8");
	}
	
	@Override
	// 当客户端和服务端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法，发送查询时间的指令给服务端
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			// 每发送一条就刷新一次，保证每条消息都会被写入Channel中。
			ctx.writeAndFlush(message);// 调用writeAndFlush方法将请求消息发送到服务端
		}
	}
	
	@Override
	// 当服务端返回应答消息时，channelRead方法被调用。
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("Now is : " + body + "; this counter is : " + ++counter);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("Unexpected exception from downstream : " + cause.getMessage());
		ctx.close();
	}
}
