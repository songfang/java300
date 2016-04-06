package org.xxhh.java300.netty.ser;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		SubscribeReq req = (SubscribeReq)msg;
		if("Lilinfeng".equalsIgnoreCase(req.getUserName())){
			System.out.println("Service accept client subscribe req:["+req.toString()+"]");
			ctx.writeAndFlush(resp(req.getSubReqId()));
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
	private SubscribeResp resp(int subReqID){
		SubscribeResp resp = new SubscribeResp();
		resp.setSubReqId(subReqID);
		resp.setRespCode(0);
		resp.setDesc("Netty book order succeed,3 days later,sent to the designated address");
		return resp;
	}
}
