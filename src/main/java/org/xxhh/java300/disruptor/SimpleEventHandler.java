package org.xxhh.java300.disruptor;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;

public class SimpleEventHandler implements EventHandler<SimpleEvent> {

	private static Logger logger = Logger.getLogger(SimpleEventHandler.class);

	public void onEvent(SimpleEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		logger.info("catch event:" + sequence);
		logger.info("catch event value:" + event.getValue());
		logger.info("catch event:"+endOfBatch);
	}

}
