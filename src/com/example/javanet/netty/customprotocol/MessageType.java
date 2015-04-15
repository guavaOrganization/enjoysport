package com.example.javanet.netty.customprotocol;

public enum MessageType {
	/**
	 * 0 : 业务请求消息
	 * 1 : 业务响应消息
	 * 2 : 业务 ONE WAY消息(既是请求又是响应消息)
	 * 3 : 握手请求消息
	 * 4 : 握手应答消息
	 * 5 : 心跳请求消息
	 * 6 : 心跳应答消息
	 */
	LOGIN_REQ((byte) 3), LOGIN_RESP((byte) 4), HEARTBEAT_REQ((byte) 5), HEARTBEAT_RESP((byte) 6);
	
	private byte type;

	private MessageType(byte type) {
		this.type = type;
	}
	
	public byte value(){
		return this.type;
	}
}
