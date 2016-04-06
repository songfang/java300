package org.xxhh.java300.netty.protobuf.ser;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeReqProto {

	/**
	 * 使用protobuf编码
	 * @param req
	 * @return
	 */
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}

	/**
	 * 使用protobuf解码
	 * @param body
	 * @return
	 * @throws InvalidProtocolBufferException
	 */
	private static SubscribeReqProto.SubscribeReq decode(byte[] body)
			throws InvalidProtocolBufferException {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}

	/**
	 * 创建一个protobuf的对象
	 * @return
	 */
	private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq
				.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("Lilinfeng");
		builder.setProductName("Netty Book");
		builder.setAddress("ShenZhen HongShuLin");
		return builder.build();

	}
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode: " + req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After decode :" + req.toString());
		System.out.println("Assert equal : -->" + req2.equals(req));
	}
}
