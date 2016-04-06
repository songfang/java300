package org.xxhh.java300.netty.thread;

public class TestCountDownLatch {
	
	public static void main(String[] args) {  
		Viedoconference conference = new Viedoconference(9);  
        Thread threadConference = new Thread(conference);  
        threadConference.start();  
        for(int i=0;i<10;i++){  
            Participant p = new Participant(conference, "Participant:"+i);  
            Thread t = new Thread(p);  
            t.start();  
        }  
    }  

}
