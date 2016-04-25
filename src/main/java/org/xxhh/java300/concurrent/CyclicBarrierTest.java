package org.xxhh.java300.concurrent;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
	
	
	public static class ComponentThread implements Runnable{

		CyclicBarrier barrier;	//计数器
		int ID;					//组件标识
		int[] array;
		
		public ComponentThread(CyclicBarrier barrier, int iD, int[] array) {
			super();
			this.barrier = barrier;
			ID = iD;
			this.array = array;
		}

		public void run() {
			try{
				array[ID] = new Random().nextInt(100);
				System.out.println("Component " + ID + "generates: " + array[ID]);
				System.out.println("Component " + ID + "sleep");
				barrier.await();
				System.out.println("Component " + ID + " awaked");
				
				int result = array[ID] + array[ID+1];
				System.out.println("Component " + ID + " result: " + result);
			} catch (Exception ex){
				
			}
		}
		
	}
	
	public static void testCyclicBarrier(){
		final int[] array = new int[3];
		CyclicBarrier barrier = new CyclicBarrier(2,new Runnable(){

			public void run() {
			System.out.println("testCyclicBarrier run");
				array[2] = array[0] + array[1];
			}
			
		});
		
		new Thread(new ComponentThread(barrier,0,array)).start();
		new Thread(new ComponentThread(barrier,1,array)).start();
		
	}
	
	public static void main(String[] args) {
		CyclicBarrierTest.testCyclicBarrier();
	}
	

}
