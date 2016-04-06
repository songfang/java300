package org.xxhh.java300.netty.netty;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
	
	
	public static void run() throws Exception{
		
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost",8999));
		socketChannel.configureBlocking(false);
		
		ByteBuffer request = ByteBuffer.wrap("hahhahahah".getBytes("UTF-8"));
		socketChannel.write(request);
		request.clear();
		socketChannel.close();
		

		Socket socket = new Socket();
		socket.getInputStream();
		

		
		
	}
	
	public static void main(String[] args) throws Exception {
		run();
	}
}
