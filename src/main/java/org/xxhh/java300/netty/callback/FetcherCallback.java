package org.xxhh.java300.netty.callback;

public interface FetcherCallback {
	void onData(Data data) throws Exception;
	void onError(Throwable cause);

}
