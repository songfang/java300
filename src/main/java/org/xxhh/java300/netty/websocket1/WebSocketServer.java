package org.xxhh.java300.netty.websocket1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

	public void run(int port) throws Exception{
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
	
		try{
			ServerBootstrap b = new ServerBootstrap();
			
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .option(ChannelOption.SO_BACKLOG, 1024)				   //ChannelOption有许多设置,可以优化tcp传输参数设置
			 .handler(new LoggingHandler(LogLevel.INFO))               //配置日志输出
			 .childHandler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast("http-codec",new HttpServerCodec());
					ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
					ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
					ch.pipeline().addLast("handler",new WebSocketServerHandler());
				}
			 });
			
			ChannelFuture f = b.bind("localhost",port).sync();
			System.out.println("start server......");
			f.channel().closeFuture().sync();
			 
		} finally{
		
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8999;
		if(args !=null &&args.length>0){
			try{
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e){
				port = 8999;
			}
		}
		
		new WebSocketServer().run(port);
	}
}
