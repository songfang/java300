package org.xxhh.java300.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TestMethodInterceptor implements MethodInterceptor{

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("before");
		System.out.println(method.getName());
		Object object = proxy.invokeSuper(obj, args);
		System.out.println("after");
		
		return object;
	}

	

}
