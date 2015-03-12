/*
 * Copyright (c) 2015 org.workin-support 
 * Create Date : 2015-1-26
 */

package org.worin.support.test;

import org.junit.Test;
import org.workin.support.context.ApplicationContext;
import org.workin.support.util.ApplicationContextUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 应用上下文单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-1-26
 */
public class ApplicationContextTest extends BaseTestCase {
	
	@Test
	public void testThreadLocalContext() {
		ApplicationContext<String, String> context1 = ApplicationContextUtils.getThreadLocalContext();
		ApplicationContext<String, String> context2 = ApplicationContextUtils.getThreadLocalContext();
		
		String name = "my";
		String value = "dubin";
		context2.setAttribute(name, value);
		assertEquals(context1.getAttribute(name), value);
		
	}
		
}



