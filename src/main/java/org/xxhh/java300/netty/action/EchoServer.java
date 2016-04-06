package org.xxhh.java300.netty.action;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private final int port;

	public EchoServer(int port) {
		super();
		this.port = port;
	}
	
	public void start()	throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(port).childHandler(new ChannelInitializer<Channel>(){

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new EchoServerHandler());
				}
				
			});	
			
			ChannelFuture f = b.bind().sync();
			System.out.println("start server......");
			f.channel().closeFuture().sync();
		} finally{
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new EchoServer(8999).start();
	}
}
