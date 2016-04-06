package org.xxhh.java300.disruptor;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventTranslatorOneArg;
/**
 * 在Disruptor中直接使用，参数为pubishEvent中传递的数据中的某一个
 * @author shiya
 *
 */
public class SimpleEventWithArgsTranslator implements EventTranslatorOneArg<SimpleEvent,Object>{
	private static Logger logger = Logger
			.getLogger(SimpleEventWithArgsTranslator.class);

	public void translateTo(SimpleEvent event, long sequence, Object arg0) {
		String uuid = UUID.randomUUID().toString();
		event.setValue(uuid);
		logger.info("translator sequence:"+sequence);
		logger.info("translator arg:"+arg0.toString());
		logger.info("translator end...");
		
	}

}
