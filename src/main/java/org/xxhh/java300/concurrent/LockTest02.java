package org.xxhh.java300.concurrent;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest02 {
	
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		final LockTest02 lockTest02 = new LockTest02();
		new Thread(){
			public void run(){
				lockTest02.insert(Thread.currentThread());
			};
		}.start();
		
		new Thread(){
			public void run(){
				lockTest02.insert(Thread.currentThread());
			};
		}.start();
	}

	protected void insert(Thread currentThread) {
		// TODO Auto-generated method stub
		if(lock.tryLock()){
			try{
				System.out.println(currentThread.getName() + "得到了锁");
				for(int i=0;i<5;i++){
					arrayList.add(i);
				}
			} catch(Exception e){
				
			} finally{
				System.out.println(currentThread.getName() + "释放了锁");
				lock.unlock();
			}
		}
		else{
			System.out.println(currentThread.getName() + "获取锁失败");
		}
	}

}
