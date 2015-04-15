package com.example.javanet.netty.file;

import java.io.File;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpConstants;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		File file = new File(msg);
		if (file.exists()) {
			if (!file.isFile()) {
				ctx.writeAndFlush("Not a file : " + file + HttpConstants.CR);
				return;
			}
			ctx.write(file + " " + file.length() + HttpConstants.CR);
			RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
			FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
			ctx.write(region);
			ctx.writeAndFlush(HttpConstants.CR);
			randomAccessFile.close();
		} else {
			ctx.writeAndFlush("File not found :" + file + HttpConstants.CR);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
