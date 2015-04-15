package com.example.javanet.netty.websocket;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
	private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
	private WebSocketServerHandshaker handshaker;
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {// 第一次握手请求消息由HTTP协议承载，所以它是一个HTTP消息
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			// 握手之后，分析链路简历成功
			// 客户端通过文本框提交请求消息给服务器端，WebSocketServerHandler接收到的是已经解码后的WebSocketFrame消息。
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	// 处理WebSocket握手请求
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		// 握手请求中需要包含值为websocket的Upgrade字段
		if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sentHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:1223/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			// 构造握手响应消息返回给客户端，同时将WebSocket相关的编码和解码类动态加到ChannelPipeline中，用于WebSocket消息编解码
			handshaker.handshake(ctx.channel(), req);
		}
	}
	
	private static void sentHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
		if (resp.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(resp.status().toString(), CharsetUtil.UTF_8);
			resp.content().writeBytes(buf);
			buf.release();
			HttpHeaderUtil.setContentLength(resp, resp.content().readableBytes());
		}
		
		ChannelFuture f = ctx.channel().writeAndFlush(resp);
		if (!HttpHeaderUtil.isKeepAlive(req) || resp.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		// 判断是否是关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {// 对控制帧进行判断
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		// 判断是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
		
		String request = ((TextWebSocketFrame) frame).text();
		if (logger.isLoggable(Level.FINE)) {
			logger.fine(String.format("%s received %s", ctx.channel(), request));
		}
		ctx.channel().write(new TextWebSocketFrame(request + ",欢迎使用Netty WebSocket服务，现在时刻：" + new Date().toString()));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
