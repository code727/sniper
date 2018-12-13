/*
 * Copyright (c) 2015 org.sniper-commons 
 * Create Date : 2015-2-26
 */

package org.sniper.beans.test;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.sniper.beans.BeanUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

/**
 * JAVA Bean工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0, 2015-2-26
 */
public class BeanUtilsTest extends BaseTestCase {
	
//	@Test
	public void testFindGetters() {
		List<Method> getters = BeanUtils.findGetters(User.class);
		assertTrue(CollectionUtils.isNotEmpty(getters));
		System.out.println(getters);
	}
	
//	@Test
	public void testFindGetterNames() {
		List<String> getterNames = BeanUtils.findGetterNames(User.class);
		assertTrue(CollectionUtils.isNotEmpty(getterNames));
		System.out.println(getterNames);
	}
	
//	@Test
	public void testFindGetter() {
		Method getter = BeanUtils.findGetter(User.class, "abc");
		assertNull(getter);
		
		getter = BeanUtils.findGetter(User.class, "loginName");
		assertNotNull(getter);
		System.out.println(getter);
	}
	
//	@Test
	public void testFindGetterName() {
		String getterName = BeanUtils.findGetterName(User.class, "abc");
		assertNull(getterName);
		
		getterName = BeanUtils.findGetterName(User.class, "loginName");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.findGetterName(User.class, "married");
		assertNotNull(getterName);
		System.out.println(getterName);
	}
	
//	@Test
	public void testFindSetters() {
		List<Method> setters = BeanUtils.findSetters(User.class);
		assertTrue(CollectionUtils.isNotEmpty(setters));
		System.out.println(setters);
	}
	
//	@Test
	public void testFindSetterNames() {
		List<String> setterNames = BeanUtils.findSetterNames(User.class);
		assertTrue(CollectionUtils.isNotEmpty(setterNames));
		System.out.println(setterNames);
	}
	
//	@Test
	public void testFindSetter() {
		Method setter = BeanUtils.findSetter(User.class, "abc");
		assertNull(setter);
		
		setter = BeanUtils.findSetter(User.class, "loginName");
		assertNotNull(setter);
		System.out.println(setter);
	}
	
//	@Test
	public void testFindSetterName() {
		String setterName = BeanUtils.findSetterName(User.class, "abc");
		assertNull(setterName);
		
		setterName = BeanUtils.findSetterName(User.class, "loginName");
		assertNotNull(setterName);
		System.out.println(setterName);
		
		setterName = BeanUtils.findSetterName(User.class, "married");
		assertNotNull(setterName);
		System.out.println(setterName);
	}
	
	@Test
	public void testGetterName() {
		String getterName = BeanUtils.getterName(User.class, "abc");
		assertNull(getterName);
		
		getterName = BeanUtils.getterName(User.class, "loginName");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.getterName(User.class, "married");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.getterName(User.class, "department.company.name");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.getterName(User.class, "boss.married");
		assertNotNull(getterName);
		System.out.println(getterName);
	}
	
//	@Test
//	public void testInvokeMethod() throws Exception {
//		User user = BeanUtils.create(User.class);
//		String expression = "department.company.createTime";
//		BeanUtils.set(user, expression, "2010-01-01 12:00:00");
//		System.out.println(BeanUtils.get(user, expression));
//		System.out.println(user.getDepartment().getCompany().getId());
//		
//	}
//	
////	@Test
//	public void testFind() {
//		System.out.println(BeanUtils.findGetter(User.class, "vip"));
//		System.out.println(BeanUtils.findSetter(User.class, "vip"));
//	}
//	
////	@Test
//	public void testFindAll() {
//		System.out.println(BeanUtils.findAllGetterName(User.class));
//		System.out.println(BeanUtils.findAllSetterName(User.class));
//		System.out.println(BeanUtils.findAllPropertyNameByGetter(User.class));
//	}
//	
////	@Test
//	public void testCreate() throws Exception {
//		System.out.println(BeanUtils.create(User.class));
//		System.out.println(BeanUtils.create(new User(), new String[] { "id" }));
//	}

}
