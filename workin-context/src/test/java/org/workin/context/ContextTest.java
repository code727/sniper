/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-26
 */

package org.workin.context;

import org.junit.Test;
import org.workin.test.junit.BaseTestCase;

/**
 * 上下文单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-26
 */
public class ContextTest extends BaseTestCase {
	
	@Test
	public void testMapThreadLocalContext() {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread1...");
				ThreadLocalHolder.setAttribute("my", "dubin");
				System.out.println(ThreadLocalHolder.getAttribute("my"));
				System.out.println("End thread1!");
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread2...");
				System.out.println(ThreadLocalHolder.getAttribute("my"));
				System.out.println("End thread2!");
			}
		});
		
		thread1.start();
		sleep(3000);
		System.out.println("----------------------------------------");
		thread2.start();
	}
		
}



