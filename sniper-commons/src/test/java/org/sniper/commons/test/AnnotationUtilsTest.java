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

import org.junit.Before;
import org.sniper.commons.test.annotation.Principal;
import org.sniper.commons.test.annotation.UserName;
import org.sniper.commons.test.domain.User;
import org.sniper.commons.util.AnnotationUtils;
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
	public void testHasX() {
		System.out.println(AnnotationUtils.hasAnnotation(user));
		System.out.println(AnnotationUtils.hasAnnotation(user, Principal.class));
	}
	
//	@Test
	public void testFindXField() {
		System.out.println(AnnotationUtils.findAnnotationField(user.getClass()));
		System.out.println(AnnotationUtils.findAnnotationField(user.getClass(), UserName.class));
		System.out.println(AnnotationUtils.findFirstAnnotationField(user.getClass(), UserName.class));
	}
	
}
