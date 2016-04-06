package org.xxhh.java300.disruptor;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventTranslatorVararg;
/**
 * 只能直接在RingBuffer publishEvent中使用
 * @author shiya
 *
 */
public class SimpleEventWithArgsTranslator2 implements
		EventTranslatorVararg<SimpleEvent> {
	private static Logger logger = Logger
			.getLogger(SimpleEventWithArgsTranslator.class);

	public void translateTo(SimpleEvent event, long sequence, Object... args) {
		String uuid = UUID.randomUUID().toString();
		event.setValue(uuid);
		logger.info("translator sequence:" + sequence);
		logger.info("translator arg1:" + args[0]);
		logger.info("translator arg2:" + args[1]);
		logger.info("translator end...");
	}

}
