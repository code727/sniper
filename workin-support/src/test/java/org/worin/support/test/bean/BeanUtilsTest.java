/*
 * Copyright (c) 2015 org.workin-commons 
 * Create Date : 2015-2-26
 */

package org.worin.support.test.bean;

import org.junit.Test;
import org.workin.support.bean.BeanUtils;

/**
 * @description JAVA Bean工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-26
 */
public class BeanUtilsTest {
	
	@Test
	public void testInvokeMethod() throws Exception {
		User user = new User();
		String expression = "department.";
		BeanUtils.set(user, expression, new Department());
		System.out.println(BeanUtils.get(user, expression));
	}

}
