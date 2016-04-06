package org.xxhh.java300.netty.ser;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	public SubReqClientHandler() {
		// TODO Auto-generated constructor stub
	}

	private SubscribeReq subReq(int i){
		SubscribeReq req = new SubscribeReq();
		req.setAddress("上海闸北西藏北路18号");
		req.setPhoneNumber("18616251633");
		req.setProductName("Netty 权威指南");
		req.setSubReqId(i);
		req.setUserName("Lilinfeng");
		return req;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			ctx.write(subReq(i));
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Receive server response: [" + msg + "]");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

}
