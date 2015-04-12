package com.example.javanet.netty.http_xml;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	HTTP + XML 解码器
 * 	客户端收到应答消息后，需要将XML格式数据解码成POJO对象
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {
	public HttpXmlResponseDecoder(Class<?> clazz) {
		this(clazz, false);
	}

	public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
		// 解码==反序列化
		HttpXmlResponse httpXmlResponse = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
		out.add(httpXmlResponse);
	}
}
