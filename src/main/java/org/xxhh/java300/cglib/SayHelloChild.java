package org.xxhh.java300.cglib;

public class SayHelloChild extends SayHello{

	public void say() {
		System.out.println("begin");
		super.say();
		System.out.println("after");
	}
	
}