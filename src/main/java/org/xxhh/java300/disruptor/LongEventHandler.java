package org.xxhh.java300.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {

	public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("eventHandler " + Thread.currentThread().getName());
		System.out.println("haha" + longEvent.getValue());
	}
}
