package org.xxhh.java300.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
	
	public final static AtomicReference <String>ATOMIC_REFERENCE = new AtomicReference<String>("abc");
	
	public static void main(String[] args) {
		Thread [] threads = new Thread[10];
		for(int i=0;i<10;i++){
			final int num = i;
			threads[i] = new Thread(){
				public void run(){
					try{
						Thread.sleep(3000);
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					if(ATOMIC_REFERENCE.compareAndSet("abc", new String("abc"))){
						System.out.println("我是线程:" + num + ",我得到了锁进行了对象修改");
					}
				}
			};
			threads[i].start();
		}
	}
}
