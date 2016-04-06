package org.xxhh.java300.netty.callback;

public class Worker {

	public void doWork() {
		Fetcher fetcher = new MyFetcher(new Data(1, 1));
		fetcher.fetchData(new FetcherCallback() {
			public void onError(Throwable cause) {
				// TODO Auto-generated method stub
				System.out.println("An error accour:" + cause.getMessage());
			}

			public void onData(Data data) throws Exception {
				System.out.println("Data received:" + data);
			}
		});
	}

	public static void main(String[] args) {
		Worker w = new Worker();
		w.doWork();
	}
}
