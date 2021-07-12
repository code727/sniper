/*
 * Copyright (c) 2015 org.sniper-commons 
 * Create Date : 2015-2-26
 */

package org.sniper.beans.test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sniper.beans.BeanUtils;
import org.sniper.commons.exception.NestedNullPointerException;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

/**
 * JAVA Bean工具测试类
 * @author  Daniele
 * @version 1.0, 2015-2-26
 */
public class BeanUtilsTest extends BaseTestCase {
	
	private final User user = new User();
	
	@Before
	public void init() {
		this.user.setBoss(new User());
	}
	
//	@Test
	public void testMapPropertyOperations() throws Exception {	
		String propertyName = "detail.height";
		Object propertyValue = 175;
		Object result;
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		propertyName = "detail.(height)";
		propertyValue = 176;
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		propertyName = "detail(height)";
		propertyValue = 177;
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
	}
	
//	@Test
	public void testListPropertyOperations() throws Exception {	
		int index = 0;
		String propertyName = String.format("addresses.%s", index);
		Object propertyValue = String.format("ads%s", index);
		Object result;
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getAddresses().get(index));
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		index = 1;
		propertyName = String.format("addresses.[%s]", index);
		propertyValue = String.format("ads[%s]", index); 
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getAddresses().get(index));
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		index = 9;
		propertyName = String.format("addresses[%s]", index);
		propertyValue = String.format("ads[%s]", index); 
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getAddresses().get(index));
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
	}
	
//	@Test
	public void testArrayPropertyOperations() throws Exception {	
		int index = 0;
		String propertyName = String.format("keywords.%s", index);
		Object propertyValue = String.format("kw%s", index);
		Object result;
		
		try {
			BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		} catch (Exception e) {
			assertTrue(e instanceof NestedNullPointerException);
			System.out.println("Nested null pointer exception:" + index);
		}
		
		propertyName = String.format("keywords.[%s]", index);
		propertyValue = String.format("kw[%s]", index);
		try {
			BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		} catch (Exception e) {
			assertTrue(e instanceof NestedNullPointerException);
			System.out.println("Nested null pointer exception:" + index);
		}
		
		propertyName = String.format("keywords[%s]", index);
		propertyValue = String.format("kw[%s]", index);
		try {
			BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		} catch (Exception e) {
			assertTrue(e instanceof NestedNullPointerException);
			System.out.println("Nested null pointer exception:" + index);
		}
		
		BeanUtils.setPropertyValue(this.user, propertyName, String[].class, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getKeywords()[index]);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		index = 9;
		propertyName = String.format("keywords.[%s]", index);
		propertyValue = String.format("kw.[%s]", index);
		try {
			BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		} catch (Exception e) {
			assertTrue(e instanceof ArrayIndexOutOfBoundsException);
			System.out.println("Array index out of range: " + index);
		}
		
		propertyName = String.format("keywords.%s", index);
		propertyValue = String.format("kw.%s", index);
		try {
			BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		} catch (Exception e) {
			assertTrue(e instanceof ArrayIndexOutOfBoundsException);
			System.out.println("Array index out of range: " + index);
		}
		
		propertyName = String.format("keywords[%s]", index);
		propertyValue = String.format("kw[%s]", index);
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getKeywords()[index]);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		propertyName = String.format("keywords.[%s]", index);
		propertyValue = String.format("kw.[%s]", index);
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getKeywords()[index]);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
		
		propertyName = String.format("keywords.%s", index);
		propertyValue = String.format("kw.%s", index);
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		result = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, result);
		assertEquals(propertyValue, this.user.getKeywords()[index]);
		System.out.println(String.format("Property '%s' value is:%s", propertyName, result));
	}
	
//	@Test
	public void testBeanPropertyOperations() throws Exception {	
		String propertyName = "name";
		Object propertyValue = "daniele";
		Object value;
		
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		value = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, value);
		assertEquals(propertyValue, this.user.getName());
		System.out.println(propertyValue);
		
		propertyName = "married";
		propertyValue = true;
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		value = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, value);
		assertEquals(propertyValue, this.user.isMarried());
		System.out.println(propertyValue);
		
		propertyName="boss.name";
		propertyValue = "herris";
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		value = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, value);
		assertEquals(propertyValue, this.user.getBoss().getName());
		System.out.println(propertyValue);
		
		propertyName = "boss.married";
		propertyValue = true;
		BeanUtils.setPropertyValue(this.user, propertyName, propertyValue);
		value = BeanUtils.getPropertyValue(this.user, propertyName);
		assertEquals(propertyValue, value);
		assertEquals(propertyValue, this.user.getBoss().isMarried());
		System.out.println(propertyValue);
	}
	
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
	
//	@Test
	public void testFindNestedGetterName() {
		String getterName = BeanUtils.findNestedGetterName(User.class, "abc");
		assertNull(getterName);
		
		getterName = BeanUtils.findNestedGetterName(User.class, "loginName");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.findNestedGetterName(User.class, "married");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.findNestedGetterName(User.class, "boss.married");
		assertNotNull(getterName);
		System.out.println(getterName);
		
		getterName = BeanUtils.findNestedGetterName(User.class, "department.company.name");
		assertNotNull(getterName);
		System.out.println(getterName);
	}
		
//	@Test
	public void testFindNestedSetterName() {
		String setterName = BeanUtils.findNestedSetterName(User.class, "abc");
		assertNull(setterName);
		
		setterName = BeanUtils.findNestedSetterName(User.class, "loginName");
		assertNotNull(setterName);
		System.out.println(setterName);
		
		setterName = BeanUtils.findNestedSetterName(User.class, "married");
		assertNotNull(setterName);
		System.out.println(setterName);
		
		setterName = BeanUtils.findNestedSetterName(User.class, "boss.married");
		assertNotNull(setterName);
		System.out.println(setterName);
		
		setterName = BeanUtils.findNestedSetterName(User.class, "department.company.name");
		assertNotNull(setterName);
		System.out.println(setterName);
	}
	
//	@Test
	public void testFindPropertyNamesByGetter() {
		List<String> names = BeanUtils.findPropertyNamesByGetter(User.class);
		assertTrue(CollectionUtils.isNotEmpty(names));
		System.out.println(names);
		
		names = BeanUtils.findPropertyNamesByGetter(User.class, new String[]{"id", "password"});
		assertTrue(CollectionUtils.isNotEmpty(names));
		System.out.println(names);
	}
	
//	@Test
	public void testCreateBean() throws Exception {
		User user = BeanUtils.create(User.class);
		assertNotNull(user);
		
		Map<String, Object> properties = MapUtils.newHashMap();
		properties.put("name", "daniele");
		properties.put("married", true);
		properties.put("boss.name", "herris");
		properties.put("boss.married", true);
		
		user = BeanUtils.create(User.class, properties);
		assertNotNull(user);
		assertEquals("daniele", user.getName());
		assertEquals(true, user.isMarried());
		assertEquals("herris", user.getBoss().getName());
		assertEquals(true, user.getBoss().isMarried());
	}
	
	@Test
	public void testCreateMapByBean() throws Exception {
		Map<String, Object> map = BeanUtils.create(this.user);
		assertTrue(MapUtils.isNotEmpty(map));
		System.out.println(map);
		
		map = BeanUtils.create(this.user, new String[] { "abc" });
		assertTrue(MapUtils.isNotEmpty(map));
		System.out.println(map);
		
		map = BeanUtils.create(this.user, new String[] { "createTime", "married", "boss" });
		assertTrue(MapUtils.isNotEmpty(map));
		System.out.println(map);
	}
		
}
