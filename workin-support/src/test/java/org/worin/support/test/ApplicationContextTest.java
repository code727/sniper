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
 * @description 应用上下文单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-26
 */
public class ApplicationContextTest extends BaseTestCase {
	
	@Test
	public void testMapThreadLocalContext() {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("--- 线程1的执行结果如下：");
				
				ApplicationContext<String, String> context1 = ApplicationContextHolder.newMapThreadLocalContext();
				ApplicationContext<String, String> context2 = ApplicationContextHolder.newMapThreadLocalContext();
				
				context1.setAttribute("my", "dubin");
				System.out.println(context1.getAttribute("my"));
				
				context2.setAttribute("my", "daniele");
				System.out.println(context2.getAttribute("my"));
				System.out.println(context1.getAttribute("my"));
				
				System.out.println(ApplicationContextHolder.getAttribute("my"));
				
				ApplicationContextHolder.set(new Object());
				System.out.println(ApplicationContextHolder.get());
				
				System.out.println("--- 线程1的执行结束！");
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("--- 线程2的执行结果如下：");
				
				ApplicationContext<String, String> context1 = ApplicationContextHolder.newMapThreadLocalContext();
				ApplicationContext<String, String> context2 = ApplicationContextHolder.newMapThreadLocalContext();
				
				System.out.println(context2.getAttribute("my"));
				System.out.println(context1.getAttribute("my"));
				System.out.println(ApplicationContextHolder.getAttribute("my"));
				System.out.println(ApplicationContextHolder.get());
				
				System.out.println("--- 线程2的执行结束！");
			}
		});
		
		thread1.start();
		sleep(3000);
		thread2.start();
	}
		
}



