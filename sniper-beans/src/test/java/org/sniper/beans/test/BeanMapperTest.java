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

package org.sniper.beans.test;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.sniper.beans.mapper.BeanToBeanMapper;
import org.sniper.beans.mapper.MapToBeanMapper;
import org.sniper.beans.mapper.MapperRule;
import org.sniper.beans.mapper.ParametersToBeanMapper;
import org.sniper.beans.parameter.DefaultParameters;
import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.test.domain.User;
import org.sniper.test.junit.BaseTestCase;

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
		ParametersToBeanMapper<Object> mapper = new ParametersToBeanMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "id"));
		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		Parameters<String, Object> source = new DefaultParameters<String, Object>();
		source.add("id", 123L);
		source.add("name", "CMGE");
		
		User user = mapper.mapping(source, User.class);
		System.out.println(user.getId());
		System.out.println(user.getName());
	}

}
