package org.xxhh.java300.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEventMain {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		
		Executor executor = Executors.newCachedThreadPool();
		LongEventFactory factory = new LongEventFactory();
		
		int bufferSize = 1024;//预分配空间
		
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,bufferSize,executor);
		
		disruptor.handleEventsWith(new LongEventHandler());
		
		disruptor.start();
		
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
		LongEventProducer producer = new LongEventProducer(ringBuffer);
		
		ByteBuffer bb = ByteBuffer.allocate(8);
		
		for(long i=0;i<200; i++){
			bb.putLong(0,1);
			producer.onData(bb);
		}
		
		
	}

}
