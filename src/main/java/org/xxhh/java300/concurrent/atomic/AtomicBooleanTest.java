package org.xxhh.java300.concurrent.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest {

	public final static AtomicBoolean TEST_BOOLEAN = new AtomicBoolean();
	
	public static void main(String[] args) {
		
		Thread[] threads = new Thread[10];
		for(int i=0;i<10;i++){
			final int num = i;
			threads[i] = new Thread(){
				public void run(){
					try{
						Thread.sleep(1000);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					if(TEST_BOOLEAN.compareAndSet(false, true)){
						System.out.println("我成功了!我是线程" + num);
					}
				}
			};
			threads[i].start();
		}		
	}
}
