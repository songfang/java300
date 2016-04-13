package org.xxhh.java300.cglib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;

public class DoCGLib {
	public static void main(String[] args) {
		// ͨ���������ķ�ʽ����������
		CglibProxy proxy = new CglibProxy();
		SayHello proxyImp = (SayHello) proxy.getProxy(SayHello.class);
		for (Type t : proxyImp.getClass().getGenericInterfaces()) {
			System.out.println(t.toString());
		}
		proxyImp.say();
		proxyImp.hello_interface();
//		SayHelloChild proxyChild = new SayHelloChild();
//		proxyChild.say();
		System.out.println(serialize(proxyImp));
	}
	
	public static byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}