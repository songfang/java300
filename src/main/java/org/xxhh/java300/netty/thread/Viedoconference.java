package org.xxhh.java300.netty.thread;

import java.util.concurrent.CountDownLatch;

public class Viedoconference implements Runnable{

	private final CountDownLatch controller;
	
	public Viedoconference(int number) {
	
		this.controller = new CountDownLatch(number);
	}


	public void arrive(String name){
		System.out.println(name + " has arrived.");
		controller.countDown();
		System.out.println("Viedoconference:Waiting for" + controller.getCount());
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Viedoconference:Initialization" + controller.getCount());
		try{
			controller.await();
            System.out.printf("VideoConference: All the participants have come\n");  
            System.out.printf("VideoConference: Let's start...\n");  
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
