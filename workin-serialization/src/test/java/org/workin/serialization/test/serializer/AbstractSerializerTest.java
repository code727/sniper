/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016年7月11日
 */

package org.workin.serialization.test.serializer;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.workin.commons.util.CollectionUtils;
import org.workin.serialization.test.domain.User;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSerializerTest extends BaseTestCase implements SerializerTest {
	
	protected User user;
	
	protected List<User> list;
	
	protected byte[] bytes;
	
	@Before
	public void init() {
		user = new User();
		user.setId(9527L);
		user.setName("dubin");
		user.setAge(33);
		user.setGender((byte) 1);
		user.setMarried(true);
		user.setVision(1.0);
		user.setCreateTime(new Date());
		
		list = CollectionUtils.newArrayList();
		list.add(user);
		for (int i = 0 ; i < 2; i++) {
			User cloneUser = user.clone();
			cloneUser.setId(user.getId() + i + 1);
			list.add(cloneUser);
		}
			
	}

}
