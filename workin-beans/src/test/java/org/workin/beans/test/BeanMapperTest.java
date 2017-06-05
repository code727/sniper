/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017年6月5日
 */

package org.workin.beans.test;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.workin.beans.mapper.BeanToBeanMapper;
import org.workin.beans.mapper.MapToBeanMapper;
import org.workin.beans.mapper.MapperRule;
import org.workin.beans.mapper.ParameterToBeanMapper;
import org.workin.beans.parameter.MapParameter;
import org.workin.beans.parameter.Parameter;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BeanMapperTest extends BaseTestCase {
	
//	@Test
	public void testBeanToBeanMapper() throws Exception {
		BeanToBeanMapper mapper = new BeanToBeanMapper();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "id"));
		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		
		User company = mapper.mapping(source, User.class);
		System.out.println(company.getId());
		System.out.println(company.getName());
	}
	
//	@Test
	public void testMapToBeanMapper() throws Exception {
		MapToBeanMapper<Object> mapper = new MapToBeanMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "department.id"));
		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		Map<String, Object> map = MapUtils.newHashMap();
		map.put("id", 123L);
		map.put("name", "CMGE");
		
		User user = mapper.mapping(map, User.class);
		System.out.println(user.getName());
		System.out.println(user.getDepartment().getId());
	}
	
	@Test
	public void testParameterToBeanMapper() throws Exception {
		ParameterToBeanMapper<Object> mapper = new ParameterToBeanMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "id"));
		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		Parameter<String, Object> source = new MapParameter<String, Object>();
		source.add("id", 123L);
		source.add("name", "CMGE");
		
		User user = mapper.mapping(source, User.class);
		System.out.println(user.getId());
		System.out.println(user.getName());
	}

}
