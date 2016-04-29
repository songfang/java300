package org.xxhh.java300.concurrent;


class ATask implements Runnable{
	private double d = 0.0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println("i'm running");
			for(int i=0;i<9000;i++){
				d=d+(Math.PI+Math.E)/d;
			}
			Thread.yield();
			break;
		}
	}
}

public class ThreadInterruptTest{
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new ATask());
		t.start();
		
		Thread.sleep(50);
		System.out.println("******************************");
		System.out.println("Interrupted Thread");
		System.out.println("******************************");
		t.interrupt();
		
	}
}





