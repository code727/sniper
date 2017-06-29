/*
 * Copyright (c) 2015 org.sniper-commons 
 * Create Date : 2015-2-26
 */

package org.sniper.beans.test;

import org.junit.Test;
import org.sniper.beans.BeanUtils;

/**
 * JAVA Bean工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-26
 */
public class BeanUtilsTest {
	
	@Test
	public void testInvokeMethod() throws Exception {
		User user = BeanUtils.create(User.class);
		String expression = "department.company.createTime";
		BeanUtils.set(user, expression, "2010-01-01 12:00:00");
		System.out.println(BeanUtils.get(user, expression));
		System.out.println(user.getDepartment().getCompany().getId());
		
		
//		List<Method> methods = BeanUtils.findAllSetter(user);
//		for (Method method : methods)
//			System.out.println(method.getName());
//		
//		methods = BeanUtils.findAllGetter(user);
//		for (Method method : methods)
//			System.out.println(method.getName());
//		
//		System.out.println(BeanUtils.create(user, new String[]{"id"}));
	}
	
//	@Test
	public void testFind() {
		System.out.println(BeanUtils.findGetter(User.class, "vip"));
		System.out.println(BeanUtils.findSetter(User.class, "vip"));
	}
	
//	@Test
	public void testFindAll() {
		System.out.println(BeanUtils.findAllGetterName(User.class));
		System.out.println(BeanUtils.findAllSetterName(User.class));
		System.out.println(BeanUtils.findAllPropertyNameByGetter(User.class));
	}
	
//	@Test
	public void testCreate() throws Exception {
		System.out.println(BeanUtils.create(User.class));
		System.out.println(BeanUtils.create(new User(), new String[] { "id" }));
	}

}
