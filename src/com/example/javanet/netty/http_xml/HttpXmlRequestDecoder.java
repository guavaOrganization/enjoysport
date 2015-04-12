package com.example.javanet.netty.http_xml;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	解码器，将请求消息体xml数据格式转换成Pojo对象
 * 
 * 	服务端将XML数据解码为POJO对象
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {
	public HttpXmlRequestDecoder(Class<?> clazz) {
		this(clazz, false);
	}
	
	public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> out) throws Exception {
		if (!request.decoderResult().isSuccess()) { // 首先对HTTP请求消息本身的解码结果进行判断，如果已经解码失败，再对消息体进行二次解码已经没有意义
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		
		// 对请求消息进行解码
		HttpXmlRequest xmlRequest = new HttpXmlRequest(request, decode0(ctx, request.content()));
		out.add(xmlRequest);
	}
	
	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
			status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
