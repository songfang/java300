package org.xxhh.java300.disruptor;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventTranslator;
/**
 * Disruptor 中使用，无参数
 * @author shiya
 *
 */
public class SimpleEventTranslator implements EventTranslator<SimpleEvent> {
	private static Logger logger = Logger
			.getLogger(SimpleEventTranslator.class);

	public void translateTo(SimpleEvent event, long sequence) {
		String uuid = UUID.randomUUID().toString();
		// SimpleEvent valueEvent = ringBuffer.get(sequence);
		event.setValue(uuid);
		logger.info("translator end...");
	}

}
