package org.xxhh.java300.cglib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import javax.sql.rowset.serial.SerialException;
import javax.sql.rowset.serial.SerialJavaObject;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) throws Throwable {
		
		Person p =  new Person();
		//InvocationHandler ih=new ProxyHandler(p );
		
		//Person tt = (Person)Proxy.newProxyInstance(p.getClass().getClassLoader(),p.getClass().getInterfaces(),ih);  
	
		PersonCglibProxy proxy = new PersonCglibProxy();
		Person tt = (Person)proxy.createProxy(Person.class,p);
		
		
		SerialJavaObject ser = new SerialJavaObject(proxy.createProxy(Person.class,p));
		
		Person pPerson  = (Person)ser.getObject();
	//Person p = (Person)proxy.createProxy(Person.class);
//	  String xml = ObjectSerializable.toXML(po2);  
//      System.out.println(xml);  
//      po = ObjectSerializable.toBean(xml, SerialPO.class);  
//		proxy.createProxy(Person.class);
		pPerson.say();
		serialize(pPerson);
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
