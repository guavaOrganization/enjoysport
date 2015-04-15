package com.example.javanet.netty.customprotocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * LengthFieldBasedFrameDecoder : 支持自动的TCP粘包和半包处理，只需要给出标识消息长度的字段偏移量和消息长度自身所占的字节数，Netty就能自动实现对半包的处理。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	private MarshallingDecoder marshallingDecoder;

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		marshallingDecoder = new MarshallingDecoder();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {// 如果为空，说明是个半包消息，直接返回继续由I/O读取后续的码流
			return null;
		}
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(in.readInt());
		header.setLength(in.readInt());
		header.setSessionID(in.readLong());
		header.setType(in.readByte());
		header.setPriority(in.readByte());
		int size = in.readInt();// 消息长度
		if (size > 0) {
			Map<String, Object> attch = new HashMap<String, Object>();
			int keySize = 0;
			byte[] keyArray = null;
			String key = null;
			for (int i = 0; i < size; i++) {
				keySize = in.readInt();
				keyArray = new byte[keySize];
				in.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");
				attch.put(key, marshallingDecoder.decode(in));
			}
			keyArray = null;
			key = null;
			header.setAttachment(attch);
		}
		if (in.readableBytes() > 4) {
			message.setBody(marshallingDecoder.decode(in));
		}
		message.setHeader(header);
		return message;
	}
}
