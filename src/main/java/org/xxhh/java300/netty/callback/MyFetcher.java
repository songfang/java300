package org.xxhh.java300.netty.callback;

public class MyFetcher implements Fetcher {

	final Data data;

	public MyFetcher(Data data) {
		this.data = data;
	}

	public void fetchData(FetcherCallback callback) {
		// TODO Auto-generated method stub
		try {
			callback.onData(data);
		} catch (Exception e) {
			callback.onError(e);
		}
	}
}
