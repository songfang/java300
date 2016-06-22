package org.xxhh.distribution;

import java.io.IOException;
import java.util.Random;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Master implements Watcher  {
	
	private static Logger logger = Logger.getLogger(Master.class);

	ZooKeeper zk;
	String hostPort;
	
    String serverId = Long.toString(new Random().nextLong());
    static boolean isLeader = false;
	
	public Master(String hostPort) {
		this.hostPort = hostPort;
	}


	void startZK(){
		try {
			zk = new ZooKeeper(hostPort,15000,this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean checkMaster(){
		while(true){
			Stat stat = new Stat();
			byte data[];
			try {
				data = zk.getData("/master", false, stat);
				isLeader = new String(data).equals(serverId);
				return true;
			} catch (KeeperException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	void runForMaster() throws InterruptedException{
		while(true){
			try{
				
				zk.create("/master",serverId.getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
				isLeader = true;
				break;
			} catch (NodeExistsException e){
				isLeader = false;
				break;
			} catch(ConnectionLossException e){
				logger.error(e);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
			if(checkMaster()) break;
		}
	}
	
	@Override
	public void process(WatchedEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e);
	}
	
	void stopZK() throws Exception{
		zk.close();
	}
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 Master m = new Master(args[0]);
		 m.startZK();
		 m.runForMaster();
		 if(isLeader){
			 System.out.println("i m the leader");
			 Thread.sleep(1000);
		 } else{
			 System.out.println("someone else is the leader");
		 }
	
		m.stopZK();
	}


}
