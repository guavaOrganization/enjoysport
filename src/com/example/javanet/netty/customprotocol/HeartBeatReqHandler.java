package com.example.javanet.netty.customprotocol;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	心跳检测机制
 * 
 * 	握手成功之后，由客户端主动发送心跳信息，服务端接受到心跳消息之后，返回心跳应答消息，由于心跳消息的目的是为了检测链路的可用性，因此不需要携带消息体
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter{
	private volatile ScheduledFuture<?> heartBeat;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		// 握手成功，主动发送心跳消息
		if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
			// 当握手成功之后，握手请求Handler会继续将握手成功消息向下透传
			// HeartBeatReqHandler接收到之后对消息进行判断，如果是握手成功消息，则启动无线循环定时器用于定期发送心跳消息。
			// 由于NioEventLoop是一个schedule，因此它支持定时器的执行。
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		} else if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()) {
			System.out.println("Client receive server heart beat message : ----> " + message);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			NettyMessage heartBeat = buildHeartBeat();
			System.out.println("Client send heart beat message to server : ----> " + heartBeat);
			ctx.writeAndFlush(heartBeat);
		}
		
		private NettyMessage buildHeartBeat() {
			NettyMessage message = new NettyMessage();
			Header header = new Header();
			header.setType(MessageType.HEARTBEAT_REQ.value());
			message.setHeader(header);
			return message;
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}
}
