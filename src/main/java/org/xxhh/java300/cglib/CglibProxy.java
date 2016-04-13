package org.xxhh.java300.cglib;

import java.io.Serializable;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3511731657451853609L;
	private EnhancerNew enhancer = new EnhancerNew();

	public Object getProxy(Class<?> clazz) {
		// ������Ҫ�����������
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		//enhancer.setInterfaces(new Class[]{IFoo.class});
		// ͨ���ֽ��뼼����̬��������ʵ��
		return enhancer.create();
	}

	// ʵ��MethodInterceptor�ӿڷ���
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("ǰ�ô���");
		// ͨ���������ø����еķ���
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("���ô���");
		return result;
	}
}