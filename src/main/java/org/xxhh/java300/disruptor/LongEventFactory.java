package org.xxhh.java300.disruptor;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory {

	public Object newInstance() {
		// TODO Auto-generated method stub
		return new LongEvent();
	}

}
