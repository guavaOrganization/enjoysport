package com.example.javanet.netty;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class SubscribeReqProtoTest {
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray(); // 将对象编码为byte数组
	}

	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
		return SubscribeReqProto.SubscribeReq.parseFrom(body); // 将byte数组解码为原始对象
	}
	
	private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder buidler = SubscribeReqProto.SubscribeReq.newBuilder();
		buidler.setSubReqID(1);
		buidler.setUserName("mcfly");
		buidler.setProductname("Netty Book");
		List<String> address = new ArrayList<String>();
		address.add("GuangZhou");
		address.add("FuZhou");
		buidler.addAllAddress(address);
		return buidler.build();
	}

	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode : " + req.toString());
		
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		
		System.out.println("After decode : " + req.toString());
		System.out.println("Assert equal : --> " + req2.equals(req));
	}
}
