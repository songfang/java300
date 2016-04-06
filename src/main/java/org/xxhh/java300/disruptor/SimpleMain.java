package org.xxhh.java300.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class SimpleMain {

	// must be a power of 2
	public static final int RING_BUFFER_SIZE = 16;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(10);
		SimpleEventHandler handler = new SimpleEventHandler();
		Disruptor<SimpleEvent> disruptor = new Disruptor<SimpleEvent>(
				SimpleEvent.EVENT_FACTORY, RING_BUFFER_SIZE, exec,
				ProducerType.SINGLE, new SleepingWaitStrategy());
		disruptor.handleEventsWith(handler);
		RingBuffer<SimpleEvent> ringBuffer = disruptor.start();

		long start = System.currentTimeMillis();
		String arg1 = "test";
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < 10000; i++) {
			/**
			 * without arguments
			 */
			// disruptor.publishEvent(new SimpleEventTranslator());
			/**
			 * publish events use disruptor with params
			 */
			// Object[] params = new Object[2];
			// params[0] = new String("a");
			// params[1] = 12;
			// disruptor
			// .publishEvents(new SimpleEventWithArgsTranslator(), params);
			// publish events with RingBuffer
			/**
			 * publish events use RingBuffer with params
			 */
			ringBuffer.publishEvent(new SimpleEventWithArgsTranslator2(), arg1,
					list);
		}
		System.out.println("executor time:"
				+ (System.currentTimeMillis() - start));
		disruptor.shutdown();
		exec.shutdown();
	}

}
