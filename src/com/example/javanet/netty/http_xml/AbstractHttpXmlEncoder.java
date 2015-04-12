package com.example.javanet.netty.http_xml;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.StringWriter;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	请求消息编码基类
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
	IBindingFactory factory = null;
	StringWriter write = null;
	final static String CHARSET_NAME = "UTF-8";
	final static Charset UTF_8 = Charset.forName(CHARSET_NAME);
	
	protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
		factory = BindingDirectory.getFactory(body.getClass());
		write = new StringWriter();
		IMarshallingContext mctx = factory.createMarshallingContext();
		mctx.setIndent(2);
		mctx.marshalDocument(body, CHARSET_NAME, null, write);// 将对象编码成XML格式数据
		String xmlStr = write.toString();
		write.close();
		write = null;
		return Unpooled.copiedBuffer(xmlStr, UTF_8);// 将XML字符串包装成Netty的ByteBuf并返回
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (write != null) {
			write.close();
			write = null;
		}
	}
}
