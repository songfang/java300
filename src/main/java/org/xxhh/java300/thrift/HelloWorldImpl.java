package org.xxhh.java300.thrift;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

	public String sayHello(String username) throws TException {
		// TODO Auto-generated method stub
		return "HI," + username + " welcome to my website......";
	}

}
