/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-26
 */

package org.worin.support.test;

import org.junit.Test;
import org.workin.support.context.ApplicationContext;
import org.workin.support.context.ApplicationContextUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 应用上下文单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-26
 */
public class ApplicationContextTest extends BaseTestCase {
	
	@Test
	public void testThreadLocalContext() {
		ApplicationContext<String, String> context1 = ApplicationContextUtils.newThreadLocalContext();
		ApplicationContext<String, String> context2 = ApplicationContextUtils.newThreadLocalContext();
		
		context1.setAttribute("my", "dubin");
		System.out.println(context1.getAttribute("my"));
		context2.setAttribute("my", "daniele");
		System.out.println(context2.getAttribute("my"));
		System.out.println(context1.getAttribute("my"));
	}
		
}



