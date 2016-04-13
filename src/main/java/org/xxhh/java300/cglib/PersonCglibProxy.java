package org.xxhh.java300.cglib;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.objenesis.ObjenesisHelper;



import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class PersonCglibProxy {
	
	static final ClassLoader GUICE_CLASS_LOADER = PersonCglibProxy.class.getClassLoader();
	public Object createProxy(Class<?> clazz,Object obj) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallbackType(TestMethodInterceptor.class);
        enhancer.setUseFactory(false);
        enhancer.setSerialVersionUID(1L);
        enhancer.setInterfaces(obj.getClass().getInterfaces());
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setStrategy(new DefaultGeneratorStrategy());
        final Class<?> proxyClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxyClass, new Callback[]{new TestMethodInterceptor()});
        return ObjenesisHelper.newInstance(proxyClass);
    }

	
	public Object getProxy(Class<?> clazz) {
		final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallbackType(TestMethodInterceptor.class);
		//enhancer.setCallback(this);
		//enhancer.setInterfaces(new Class[]{Serializable.class});
		Callback[] methodInterceptors = new Callback[]{new TestMethodInterceptor()};
		
        Class serviceClass = enhancer.createClass();
        
        System.out.println(methodInterceptors.length + "////////////////////////////");
        
        Enhancer.registerCallbacks(serviceClass, methodInterceptors);
        
		return enhancer.create();
	}
	
//	  /**
//	   * Returns the class loader to host generated classes for {@code type}.
//	   */
//	  public static ClassLoader getClassLoader(Class<?> type) {
//	    return getClassLoader(type, type.getClassLoader());
//	  }
//
//	  private static ClassLoader getClassLoader(Class<?> type, ClassLoader delegate) {
//
//	    // simple case: do nothing!
//	    if (getCustomClassLoadingOption() == CustomClassLoadingOption.OFF) {
//	      return delegate;
//	    }
//
//	    // java.* types can be seen everywhere
//	    if (type.getName().startsWith("java.")) {
//	      return GUICE_CLASS_LOADER;
//	    }
//
//	    delegate = canonicalize(delegate);
//
//	    // no need for a bridge if using same class loader, or it's already a bridge
//	    if (delegate == GUICE_CLASS_LOADER || delegate instanceof BridgeClassLoader) {
//	      return delegate;
//	    }
//
//	    // don't try bridging private types as it won't work
//	    if (Visibility.forType(type) == Visibility.PUBLIC) {
//	      if (delegate != SystemBridgeHolder.SYSTEM_BRIDGE.getParent()) {
//	        // delegate guaranteed to be non-null here
//	        return CLASS_LOADER_CACHE.getUnchecked(delegate);
//	      }
//	      // delegate may or may not be null here
//	      return SystemBridgeHolder.SYSTEM_BRIDGE;
//	    }
//
//	    return delegate; // last-resort: do nothing!
//	  }
	


}
