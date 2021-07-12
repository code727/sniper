/*
 * Copyright (c) 2015 org.sniper-support 
 * Create Date : 2015-1-26
 */

package org.sniper.context;

import org.junit.Test;
import org.sniper.context.ThreadLocalHolder;
import org.sniper.test.junit.BaseTestCase;

/**
 * 上下文单元测试类
 * @author  Daniele
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



