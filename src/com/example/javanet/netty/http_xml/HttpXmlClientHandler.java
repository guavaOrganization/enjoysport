package com.example.javanet.netty.http_xml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(1234));
		ctx.writeAndFlush(request);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
		System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());
		System.out.println("The client receive response of http body is : " + msg.getResult());
	}
}
