package com.example.javanet.netty.customprotocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	握手的发起是在客户端和服务端TCP链路建立成功通道激活时，握手消息的接入和安全认证在服务端处理
 * 
 * 	此类用于在通道激活时发起握手请求
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		// 如果是握手应答消息，需要判断是否认证成功
		if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
			byte loginResult = (byte) message.getBody();
			if (loginResult != (byte) 0) { // 握手失败，关闭连接
				ctx.close();
			} else {
				System.out.println("Login is ok : " + message);
				ctx.fireChannelRead(msg);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}
	
	private NettyMessage buildLoginReq() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQ.value());
		message.setHeader(header);
		return message;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}
