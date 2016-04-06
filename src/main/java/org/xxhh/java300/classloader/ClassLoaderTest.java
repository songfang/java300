package org.xxhh.java300.classloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.xxhh.java300.classloader.ActionInterface;
import org.xxhh.java300.classloader.TestAction;

public class ClassLoaderTest {
	
	public static void main(String[] args) {
	
		try{
			File file = new File("rtest.txt");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s = new String();
			while((s=in.readLine())!=null){
				URL url = new URL(s);
				s = null;
				URL url1 = new URL("file:c:\\classLoadTest01-0.0.1-SNAPSHOT.jar");
				URLClassLoader myCLassLoader = new URLClassLoader(new URL[]{url},Thread.currentThread().getContextClassLoader());
				
				Class myClass = myCLassLoader.loadClass("URLClassLoaderTest.TestAction");
				ActionInterface action = (ActionInterface)myClass.newInstance();
				
				String str = action.action();
				System.out.println(str);
				
				TestAction ts = new TestAction();
				
				URLClassLoader myClassLoader1 = new URLClassLoader(new URL[]{url1},Thread.currentThread().getContextClassLoader());
				Class myClass1 = myClassLoader1.loadClass("URLClassLoaderTest.TestAction");
				ActionInterface action1 = (ActionInterface)myClass1.newInstance();
				String str1 = action1.action();
				System.out.println(str1);
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
