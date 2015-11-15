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

package org.worin.support.test.mapper;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.worin.support.test.bean.Company;
import org.worin.support.test.bean.User;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.support.mapper.ParameterRule;
import org.workin.support.mapper.impl.BeanToBeanMapper;
import org.workin.support.mapper.impl.MapToMapMapper;
import org.workin.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapperTest extends BaseTestCase {
	
//	@Test
	public void testMapToMapMapper() {
		MapToMapMapper<Object> mapper = new MapToMapMapper<Object>();
		Map<String, Object> source = MapUtils.newHashMap();
		source.put("userId", "123456");
		source.put("userName", "daniele");
		
		Set<ParameterRule> rules = CollectionUtils.newHashSet();
		rules.add(new ParameterRule("userId", "user_id"));
		rules.add(new ParameterRule("userName", "user_name"));
		
		mapper.setParameterRules(rules);
		Map<String, Object> result = mapper.mapping(source);
		System.out.println(result);
	}
	
	@Test
	public void testBeanToBeanMapper() throws Exception {
		BeanToBeanMapper<User, Company> mapper = new BeanToBeanMapper<User, Company>(Company.class.getName());
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		
		Set<ParameterRule> rules = CollectionUtils.newHashSet();
		rules.add(new ParameterRule("id", "id"));
		rules.add(new ParameterRule("name", "name"));
		
		mapper.setParameterRules(rules);
		Company company = (Company) mapper.mapping(source);
		System.out.println(company.getId());
		System.out.println(company.getName());
	}

}
