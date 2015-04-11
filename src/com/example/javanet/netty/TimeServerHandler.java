package com.example.javanet.netty;


import java.sql.Date;
import java.text.SimpleDateFormat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {// 用于对网络时间进行读写曹祖偶
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];// readableBytes方法可以获取缓冲区可读的字节数
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("The time server receive order : " + body);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH小时mm分ss秒");
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? sdf.format(new Date(System.currentTimeMillis())) : "BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes("UTF-8"));
		// 从性能角度考虑，为了防止频繁地唤醒Selector进行消息发送，Netty的write方法并不直接将消息写入SocketChannel中，
		// 调用write方法只是把待发送的消息放到发送缓冲数组中，再通过调用flush方法，将发送缓冲区中的消息全部写到SocketChannel中。
		ctx.write(resp);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// 将消息发送队列中的消息写入到SocketChannel中发送给对方。
		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
