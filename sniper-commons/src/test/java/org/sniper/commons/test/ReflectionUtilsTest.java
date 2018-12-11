/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Create Date : 2018-12-3
 */

package org.sniper.commons.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

/**
 * 反射工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ReflectionUtilsTest extends BaseTestCase {
	
	private final Class<?>[] parameterTypes = new Class<?>[] { String.class, String.class, String.class };
	
	private final String[] parameterValues = new String[] {"dubin", "daniele", User.DEFAULT_PASSWORD + NumberUtils.randomIn(10)};
			
	private User user = new User();
	
//	@Test
	public void testGetConstructors() {
		System.out.println(ReflectionUtils.getConstructors(User.class));
		System.out.println(ReflectionUtils.getDeclaredConstructors(User.class));
	}
	
//	@Test
	public void testGetConstructor() {
		Constructor<?> constructor1 = ReflectionUtils.getConstructor(User.class, parameterTypes);
		Constructor<?> constructor2 = ReflectionUtils.getDeclaredConstructor(User.class, parameterTypes);
		
		assertNull(constructor1);
		assertNotNull(constructor2);
		
		System.out.println(constructor2);
	}
	
//	@Test
	public void testHasConstructor() {
		assertTrue(ReflectionUtils.hasConstructor(User.class));
		assertFalse(ReflectionUtils.hasConstructor(User.class, parameterTypes));
	}
	
//	@Test
	public void testHasDeclaredConstructor() {
		assertTrue(ReflectionUtils.hasDeclaredConstructor(User.class));
		assertTrue(ReflectionUtils.hasDeclaredConstructor(User.class, parameterTypes));
		assertFalse(ReflectionUtils.hasDeclaredConstructor(User.class, new Class<?>[] { Integer.class }));
	}
	
//	@Test
	public void testGetMethods() {
		System.out.println(ReflectionUtils.getMethods(Object.class));
	}
	
//	@Test
	public void testGetDeclaredMethods() {
		System.out.println(ReflectionUtils.getDeclaredMethods(Object.class));
	}
	
//	@Test
	public void testGetMethodNames() {
		System.out.println(ReflectionUtils.getMethodNames(Object.class));
		System.out.println(ReflectionUtils.getMethodNames(User.class));
	}
	
//	@Test
	public void testGetDeclaredMethodNames() {
		System.out.println(ReflectionUtils.getDeclaredMethodNames(Object.class));
		System.out.println(ReflectionUtils.getDeclaredMethodNames(User.class));
	}
	
//	@Test
	public void testGetMethod() {
		Method method1 = ReflectionUtils.getMethod(User.class, "getLoginName");
		Method method2 = ReflectionUtils.getMethod(User.class, "clone");
		
		assertNotNull(method1);
		assertNull(method2);
		
		System.out.println(method1);
		System.out.println(method2);
	}
	
//	@Test
	public void testGetDeclaredMethod() {
		Method method1 = ReflectionUtils.getDeclaredMethod(User.class, "getName", null);
		Method method2 = ReflectionUtils.getDeclaredMethod(User.class, "clone");
		
		assertNotNull(method1);
		assertNotNull(method2);
		
		System.out.println(method1);
		System.out.println(method2);
	}
	
	@Test
	public void testHasDeclaredMethod() {
		assertTrue(ReflectionUtils.hasDeclaredMethod(Object.class, "toString"));
		assertFalse(ReflectionUtils.hasDeclaredMethod(User.class, "toString"));
	}
	
//	@Test
	public void testGetFields() {
		System.out.println(ReflectionUtils.getFields(User.class));
		System.out.println(ReflectionUtils.getDeclaredFields(User.class));
	}
	
//	@Test
	public void testGetFieldNames() {
		System.out.println(ReflectionUtils.getFieldNames(User.class));
		System.out.println(ReflectionUtils.getDeclaredFieldNames(User.class));
	}
	
//	@Test
	public void testGetField() {
		System.out.println(ReflectionUtils.getField(User.class, "name"));
		System.out.println(ReflectionUtils.getDeclaredField(User.class, "name"));
	}
	
//	@Test
	public void testHasField() {
		assertFalse(ReflectionUtils.hasField(User.class, "name"));
		assertFalse(ReflectionUtils.hasField(User.class, "abc"));
		
		assertTrue(ReflectionUtils.hasDeclaredField(User.class, "name"));
		assertFalse(ReflectionUtils.hasDeclaredField(User.class, "abc"));
	}
	
//	@Test
	public void testGetFieldType() {
		assertEquals(String.class, ReflectionUtils.getFieldType(this.user, "name"));
		assertNull(ReflectionUtils.getFieldType(User.class, "abc"));
	}
	
//	@Test
	public void testFieldValue() throws Exception {
		String password = ReflectionUtils.getFieldValue(this.user, "password");
		String name = ReflectionUtils.getFieldValue(this.user, "name");
		
		assertEquals(User.DEFAULT_PASSWORD, password);
		assertNull(name);
		
		String userName = "dubin";
		ReflectionUtils.setFieldValue(this.user, "name", userName);
		name = ReflectionUtils.getFieldValue(this.user, "name1");
		assertEquals(userName, name);
	}
	
//	@Test
	public void testNewInstance() throws Exception {
		User user1 = ReflectionUtils.newInstance(User.class);
		User user2 = ReflectionUtils.newInstance(User.class, parameterTypes, parameterValues);
		User user3 = ReflectionUtils.newInstance(User.class, parameterTypes, new String[] {null, null, null});
		
		assertNotNull(user1);
		assertNotNull(user2);
		assertNotNull(user3);
		
		assertEquals(parameterValues[0], user2.getName());
		assertEquals(parameterValues[1], user2.getLoginName());
		assertEquals(parameterValues[2], user2.getPassword());
		
		assertNull(user3.getName());
		assertNull(user3.getLoginName());
		assertNull(user3.getPassword());
	}
	
//	@Test
	public void testInvokeMethod() throws Exception {
		Object setterExecuteResult = ReflectionUtils.invokeMethod(this.user, "setName",
				new Class<?>[] {String.class}, new Object[] {parameterValues[0]});
		
		Object getterExecuteResult = ReflectionUtils.invokeMethod(this.user, "getName");
		
		assertNull(setterExecuteResult);
		assertEquals(parameterValues[0], getterExecuteResult);
	}
		
}
