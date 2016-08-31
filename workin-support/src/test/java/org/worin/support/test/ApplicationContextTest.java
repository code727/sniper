/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-26
 */

package org.worin.support.test;

import org.junit.Test;
import org.workin.support.context.ApplicationContext;
import org.workin.support.context.ApplicationContextHolder;
import org.workin.test.junit.BaseTestCase;

/**
 * 应用上下文单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-26
 */
public class ApplicationContextTest extends BaseTestCase {
	
	@Test
	public void testMapThreadLocalContext() {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread1...");
				
				ApplicationContext<String, String> context1 = ApplicationContextHolder.newMapThreadLocalContext();
				ApplicationContext<String, String> context2 = ApplicationContextHolder.newMapThreadLocalContext();
				
				context1.setAttribute("my", "dubin");
				assertEquals("dubin", context1.getAttribute("my"));
				System.out.println(context1.getAttribute("my"));
				
				context2.setAttribute("my", "daniele");
				assertTrue(context1.getAttribute("my").equals(context2.getAttribute("my")));
				System.out.println(context2.getAttribute("my"));
				System.out.println(context1.getAttribute("my"));
				
				assertTrue(context1.getAttribute("my").equals(ApplicationContextHolder.getAttribute("my")));
				System.out.println(ApplicationContextHolder.getAttribute("my"));
				
				
				System.out.println("End thread1!");
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread2...");
				
				ApplicationContext<String, String> context1 = ApplicationContextHolder.newMapThreadLocalContext();
				ApplicationContext<String, String> context2 = ApplicationContextHolder.newMapThreadLocalContext();
				
				assertNull(context1.getAttribute("my"));
				assertNull(context2.getAttribute("my"));
				assertNull(ApplicationContextHolder.getAttribute("my"));
				
				System.out.println(context1.getAttribute("my"));
				System.out.println(context2.getAttribute("my"));
				System.out.println(ApplicationContextHolder.getAttribute("my"));
				
				System.out.println("End thread2!");
			}
		});
		
		thread1.start();
		sleep(3000);
		System.out.println("----------------------------------------");
		thread2.start();
	}
		
}



