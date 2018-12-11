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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.util.AnnotationUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.test.annotation.Cacheable;
import org.sniper.test.annotation.LoginName;
import org.sniper.test.annotation.UserName;
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
	
	@Test
	public void testGetAnnotations() {
		List<Annotation> annotations = AnnotationUtils.getAnnotations(User.class);
		assertTrue(CollectionUtils.isNotEmpty(annotations));
		System.out.println(annotations);
	}
	
	@Test
	public void testGetAnnotation() {
		Annotation annotation = AnnotationUtils.getAnnotation(User.class, Cacheable.class);
		assertNotNull(annotation);
		System.out.println(annotation);
	}
	
	@Test
	public void testAnnotated() {
		assertTrue(AnnotationUtils.annotated(User.class));
		assertTrue(AnnotationUtils.annotated(User.class, Cacheable.class));
		assertFalse(AnnotationUtils.annotated(User.class, Deprecated.class));
	}
	
	@Test
	public void testGetAnnotatedFields() {
		List<Field> annotatedFields = AnnotationUtils.getAnnotatedFields(User.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
		
		annotatedFields = AnnotationUtils.getAnnotatedFields(User.class, LoginName.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
		
		annotatedFields = AnnotationUtils.getAnnotatedFields(User.class, UserName.class);
		assertTrue(CollectionUtils.isNotEmpty(annotatedFields));
		System.out.println(annotatedFields);
	}
	
	@Test
	public void testGetAnnotatedField() {
		System.out.println(AnnotationUtils.getAnnotatedField(User.class, LoginName.class));
	}
	
	@Test
	public void testGetAnnotatedMethods() {
		System.out.println(AnnotationUtils.getAnnotatedMethods(User.class));
		System.out.println(AnnotationUtils.getAnnotatedMethods(User.class, LoginName.class));
	}
	
	@Test
	public void testGetAnnotatedMethod() {
		System.out.println(AnnotationUtils.getAnnotatedMethod(User.class, LoginName.class));
	}
		
}
