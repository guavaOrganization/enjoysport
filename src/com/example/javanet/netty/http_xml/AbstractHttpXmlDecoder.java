package com.example.javanet.netty.http_xml;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 *  HTTP + XML,HTTP请求消息解码类
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
	private IBindingFactory factory;
	private StringReader reader;
	private Class<?> clazz;
	private boolean isPrint;
	private final static String CHARSET_NAME = "UTF-8";
	public final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

	public AbstractHttpXmlDecoder(Class<?> clazz) {
		this(clazz, false);
	}
	
	public AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
		this.clazz = clazz;
		this.isPrint = isPrint;
	}
	
	protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
		factory = BindingDirectory.getFactory(clazz);
		String content = body.toString(UTF_8);
		if (isPrint)
			System.out.println("The body is : " + content);
		reader = new StringReader(content);
		IUnmarshallingContext uctx = factory.createUnmarshallingContext();
		Object result = uctx.unmarshalDocument(reader);// 将XML转换成POJO对象。
		reader.close();
		reader = null;
		return result;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (null != reader) {
			reader.close();
			reader = null;
		}
	}
}
