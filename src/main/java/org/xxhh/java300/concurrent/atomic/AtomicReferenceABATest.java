package org.xxhh.java300.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceABATest {
	
	public final static AtomicReference<String> ATOMIC_REFERENCE = new AtomicReference<String>("abc");

	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			final int num = i;
			new Thread(){
				public void run(){
					try{
						Thread.sleep(Math.abs((int)(Math.random()*100)));
					} catch(InterruptedException e){
						e.printStackTrace();
					}
					if(ATOMIC_REFERENCE.compareAndSet("abc", "abc2")){
						System.out.println("我是线程:" + num + ",我获得了锁进行了对象修改");
					}
				}
			}.start();
		}
		new Thread(){
			public void run(){
				while(!ATOMIC_REFERENCE.compareAndSet("abc2", "abc")){
					System.out.println("已经改为原始值!");
				}
			}
		}.start();
	}
}
