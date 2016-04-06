package org.xxhh.java300.netty.thread;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable {

	private Viedoconference conference;
	private String name;
	
	
	public Participant(Viedoconference conference,String name) {
		this.conference = conference;
		this.name = name;
	}



	public void run() {
		// TODO Auto-generated method stub
		long duration = (long)(Math.random()*10);
		try{
			TimeUnit.SECONDS.sleep(duration);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		conference.arrive(name);
	}

}
