package org.xxhh.distribution;

import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class MasterByTestingServer implements Watcher {

	TestingServer zk;
	int port;
	
	public MasterByTestingServer(int port) {
		this.port = port;
	}
	
	void startZK(){
		try {
			zk = new TestingServer(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MasterByTestingServer m = new MasterByTestingServer(2181);
		m.startZK();
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
	}

}
