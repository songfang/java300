package org.xxhh.java300.disruptor;

import com.lmax.disruptor.EventFactory;

public final class SimpleEvent {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public final static EventFactory<SimpleEvent> EVENT_FACTORY = new EventFactory<SimpleEvent>() {
		public SimpleEvent newInstance() {
			return new SimpleEvent();
		}
	};
}
