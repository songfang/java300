package org.xxhh.java300.cglib;

import java.io.Serializable;


public class SayHello implements helloInterface,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void say() {
		System.out.println("hello everyone");
	}

	public void hello_interface() {
		System.out.println("hello interface");
	}
}