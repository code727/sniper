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
 * Create Date : 2015年11月13日
 */

package org.worin.support.test.parameter;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.workin.commons.util.CollectionUtils;
import org.workin.support.parameter.ConcurrentParameter;
import org.workin.support.parameter.Parameter;
import org.workin.support.parameter.adapter.AdaptableConcurrentParameter;
import org.workin.support.parameter.adapter.AdaptableParameter;
import org.workin.support.parameter.adapter.NameAdaptationRule;
import org.workin.support.parameter.adapter.NameAdapter;
import org.workin.support.parameter.adapter.SimpleNameAdapter;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterTest extends BaseTestCase {
	
//	@Test
	public void testParameter() {
		Parameter<String, String> parameter = new ConcurrentParameter<String, String>();
		String userId = "123456";
		String userName = "daniele";
		
		parameter.add("userId", userId);
		parameter.add("userName", userName);
		
		assertEquals(userId, parameter.getValue("userId"));
		assertEquals(userName, parameter.getValue("userName"));
	}
	
	@Test
	public void testAdaptableParameter() {
		AdaptableParameter<String, String> parameter = new AdaptableConcurrentParameter<String, String>();
		String originalUserId = "userId";
		String adaptationUserId = "user_id";
		
		String originalUserName = "userName";
		String adaptationUserName = "user_name";
		
		/* 创建参数适配规则 */
		List<NameAdaptationRule<String>> rules = CollectionUtils.newArrayList();
		rules.add(new NameAdaptationRule<String>(originalUserId, adaptationUserId));
		rules.add(new NameAdaptationRule<String>(originalUserName, adaptationUserName));
		
		NameAdapter<String> nameAdapter = new SimpleNameAdapter<String>(rules);
		parameter.setNameAdapter(nameAdapter);
		
		String idValue = "123456";
		String nameValue = "daniele";
		
		parameter.add(originalUserId, idValue);
		parameter.add(originalUserName, nameValue);
		
		assertEquals(parameter.getValue(originalUserId), parameter.getValue(adaptationUserId));
		assertEquals(parameter.getValue(originalUserName), parameter.getValue(adaptationUserName));
		
		assertNull(parameter.getAdaptationValue(originalUserId));
		assertNull(parameter.getAdaptationValue(originalUserName));
		
		assertNull(parameter.getOriginalValue(adaptationUserId));
		assertNull(parameter.getOriginalValue(adaptationUserName));
		
		assertEquals(parameter.getAdaptationNames().size(), parameter.getOriginalNames().size());
		assertEquals(parameter.getAdaptationValues().size(), parameter.getOriginalValues().size());
		
		Set<String> originalNames = parameter.getOriginalNames();
		System.out.println("-------- OriginalNames --------");
		for (String originalName : originalNames)
			System.out.println(originalName);
		
		Set<String> adaptationNames = parameter.getAdaptationNames();
		System.out.println("-------- AdaptationNames --------");
		for (String adaptationName : adaptationNames)
			System.out.println(adaptationName);
		
	}

}
