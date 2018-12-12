/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-7-22
 */

package org.sniper.commons.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.KeyValuePair;
import org.sniper.commons.util.AnnotationUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.test.annotation.Testable;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

/**
 * Annotation工具测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AnnotationUtilsTest extends BaseTestCase {
	
	private User user;
	
	@Before
	public void init() {
		this.user = new User();
		this.user.setName("dubin");
		this.user.setLoginName("daniele");
	}
	
//	@Test
	public void testGetAnnotations() {
		List<Annotation> annotations = AnnotationUtils.getAnnotations(User.class);
		assertTrue(CollectionUtils.isNotEmpty(annotations));
		System.out.println(annotations);
	}
	
//	@Test
	public void testGetAnnotation() {
		Annotation annotation = AnnotationUtils.getAnnotation(User.class, Testable.class);
		assertNotNull(annotation);
		System.out.println(annotation);
	}
	
//	@Test
	public void testAnnotated() {
		assertTrue(AnnotationUtils.annotated(User.class));
		assertTrue(AnnotationUtils.annotated(User.class, Testable.class));
		assertFalse(AnnotationUtils.annotated(User.class, Deprecated.class));
	}
	
//	@Test
	public void testGetAnnotatedFields() {
		List<Field> annotatedFields = AnnotationUtils.getAnnotatedFields(User.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
		
		annotatedFields = AnnotationUtils.getAnnotatedFields(User.class, Testable.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
		
		annotatedFields = AnnotationUtils.getAnnotatedFields(User.class, Testable.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
	}
	
//	@Test
	public void testGetAnnotatedField() {
		Field field = AnnotationUtils.getAnnotatedField(User.class, Testable.class);
		assertNotNull(field);
		System.out.println(field);
	}
	
	@Test
	public void testSetAnnotatedFieldValue() throws Exception {
		Field field = AnnotationUtils.setAnnotatedFieldValue(this.user, Testable.class, "daniele");
		assertNotNull(field);
		System.out.println(field);
	}
	
	@Test
	public void testGetAnnotatedFieldValue() throws Exception {
		KeyValuePair<String, Object> result = AnnotationUtils.getAnnotatedFieldValue(this.user, Testable.class);
		assertNotNull(result);
		System.out.println(result);
	}
	
//	@Test
	public void testGetAnnotatedMethods() {
		List<Method> methods = AnnotationUtils.getAnnotatedMethods(User.class);
		assertTrue(CollectionUtils.isNotEmpty(methods));
		System.out.println(methods);
		
		methods = AnnotationUtils.getAnnotatedMethods(User.class, Testable.class);
		assertTrue(CollectionUtils.isNotEmpty(methods));
		System.out.println(AnnotationUtils.getAnnotatedMethods(User.class, Testable.class));
	}
	
//	@Test
	public void testGetAnnotatedMethod() {
		Method method = AnnotationUtils.getAnnotatedMethod(User.class, Testable.class);
		assertNotNull(method);
		System.out.println(method);
	}
	
//	@Test
	public void testFindAnnotatedGetters() {
		List<Method> getters = AnnotationUtils.findAnnotatedGetters(User.class, Testable.class);
		assertTrue(CollectionUtils.isNotEmpty(getters));
		System.out.println(getters);
	}
	
//	@Test
	public void testFindAnnotatedGetter() {
		Method getter = AnnotationUtils.findAnnotatedGetter(User.class, Testable.class);
		assertNotNull(getter);
		System.out.println(getter);
	}
	
//	@Test
	public void testFindAnnotatedSetters() {
		List<Method> setters = AnnotationUtils.findAnnotatedSetters(User.class, Testable.class);
		assertTrue(CollectionUtils.isNotEmpty(setters));
		System.out.println(setters);
	}
	
//	@Test
	public void testFindAnnotatedSetter() {
		Method setter = AnnotationUtils.findAnnotatedSetter(User.class, Testable.class);
		assertNotNull(setter);
		System.out.println(setter);
	}
	
//	@Test
	public void testInvokeAnnotatedMethod() throws Exception {
		KeyValuePair<String, Object> result = AnnotationUtils.invokeAnnotatedMethod(this.user, Testable.class);
		assertNotNull(result);
		System.out.println(result);
	}
	
//	@Test
	public void testInvokeAnnotatedSetter() throws Exception {
		String value = "daniele";
		Method setter = AnnotationUtils.invokeAnnotatedSetter(this.user, Testable.class, value);
		assertNotNull(setter);
		System.out.println(setter);
	}
	
//	@Test
	public void testInvokeAnnotatedGetter() throws Exception { 
		KeyValuePair<String, Object> result = AnnotationUtils.invokeAnnotatedGetter(this.user, Testable.class);
		assertNotNull(result);
		System.out.println(result);
	}
		
}
