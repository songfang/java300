package org.xxhh.java300.aop;

import org.junit.Before;
import org.junit.Test;

public class AOPTest  {
	
	private AService aService;
	
	private BServiceImpl bService;
	
	@Before
	protected String[] getConfigLocations() {
		String[] configs = new String[] { "/applicationContext.xml"};
		return configs;
	}
	
	
	/**
	 * 测试正常调用
	 */
	@Test
	public void testCall()
	{
		System.out.println("SpringTest JUnit test");
		aService.fooA("JUnit test fooA");
		aService.barA();
		bService.fooB();
		bService.barB("JUnit test barB",0);
	}
	
	/**
	 * 测试After-Throwing
	 */
	@Test
	public void testThrow()
	{
		try {
			bService.barB("JUnit call barB",1);
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Test
	public void setAService(AService service) {
		aService = service;
	}
	
	public void setBService(BServiceImpl service) {
		bService = service;
	}
}