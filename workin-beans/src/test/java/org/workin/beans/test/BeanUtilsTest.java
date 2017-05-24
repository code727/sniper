/*
 * Copyright (c) 2015 org.workin-commons 
 * Create Date : 2015-2-26
 */

package org.workin.beans.test;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.workin.beans.BeanUtils;

/**
 * JAVA Bean工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-26
 */
public class BeanUtilsTest {
	
	@Test
	public void testInvokeMethod() throws Exception {
		User user = BeanUtils.create(User.class);
		String expression = "department.";
		BeanUtils.set(user, expression, new Department());
		System.out.println(BeanUtils.get(user, expression));
		
		List<Method> methods = BeanUtils.findAllSetter(user);
		for (Method method : methods)
			System.out.println(method.getName());
		
		methods = BeanUtils.findAllGetter(user);
		for (Method method : methods)
			System.out.println(method.getName());
		
		System.out.println(BeanUtils.create(user, new String[]{"id"}));
	}

}
