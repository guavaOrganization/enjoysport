package com.example.javanet.netty.http_xml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.net.InetAddress;
import java.util.List;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	HTTP + XML 请求消息编码类，将Pojo对象转换为xml数据格式
 * 
 * 	客户端将POJO对象编码成XML数据格式发往服务端
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest>{
	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
		// 将POJO对象实例通过JiBx序列化为XML字符串，然后封装成ByteBuf
		ByteBuf body = encode0(ctx, msg.getBody());
		FullHttpRequest request = msg.getRequest();
		if (null == request) {
			request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
			HttpHeaders headers = request.headers();
			headers.set(HttpHeaderNames.HOST, InetAddress.getLocalHost().getHostAddress());
			headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
			headers.set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP.toString() + ',' + HttpHeaderValues.DEFLATE.toString());
			headers.set(HttpHeaderNames.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			headers.set(HttpHeaderNames.ACCEPT_LANGUAGE, "zh");
			headers.set(HttpHeaderNames.USER_AGENT, "Netty xml Http Client side");
			headers.set(HttpHeaderNames.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		}
		// 由于请求消息消息体不能为空，也没有使用Chunk方式，所以在HTTP消息头中设置消息体的长度Content-Length，完成消息体的XML序列化后将重新构造的HTTP请求消息加入到out中。
		// 由后续的HTTP请求编码器继续对HTTP请求消息进行编码
		request.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes() + "");
		out.add(request);
	}
}
