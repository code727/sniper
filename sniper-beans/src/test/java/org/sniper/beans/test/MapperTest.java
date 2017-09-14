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

package org.sniper.beans.test;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.sniper.beans.mapper.BeanToMapMapper;
import org.sniper.beans.mapper.BeanToParametersMapper;
import org.sniper.beans.mapper.MapToMapMapper;
import org.sniper.beans.mapper.MapToParametersMapper;
import org.sniper.beans.mapper.MapperRule;
import org.sniper.beans.mapper.ParametersToMapMapper;
import org.sniper.beans.mapper.ParametersToParametersMapper;
import org.sniper.beans.parameter.DefaultParameters;
import org.sniper.beans.parameter.Parameters;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.test.junit.BaseTestCase;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MapperTest extends BaseTestCase {
	
//	@Test
	public void testMapToMapMapper() throws Exception {
		MapToMapMapper<Object> mapper = new MapToMapMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("userId", "user_id"));
		rules.add(new MapperRule("userName", "user_name"));
		mapper.setMapperRules(rules);
		
		Map<String, Object> source = MapUtils.newHashMap();
		source.put("userId", "123456");
		source.put("userName", "sniper");
		
		Map<String, Object> result = mapper.mapping(source);
		System.out.println(result);
	}
	
//	@Test
	public void testBeanToMapMapper() throws Exception {
		BeanToMapMapper<Object> mapper = new BeanToMapMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
		
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		System.out.println(mapper.mapping(source));
	}
	
//	@Test
	public void testBeanToParameter() throws Exception {
		BeanToParametersMapper<Object> mapper = new BeanToParametersMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
	
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		
		System.out.println(mapper.mapping(source).getParameterItems());
	}
	
//	@Test
	public void testMapToParameterMapper() throws Exception {
		MapToParametersMapper<Object> mapper = new MapToParametersMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
		
		Map<String, Object> source = MapUtils.newHashMap();
		source.put("id", 123);
		source.put("name", "CMGE");
		
		System.out.println(mapper.mapping(source));
	}
	
//	@Test
	public void testParameterToMapMapper() throws Exception {
		ParametersToMapMapper<Object> mapper = new ParametersToMapMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
		
		Parameters<String, Object> source = new DefaultParameters<String, Object>();
		source.add("id", 123);
		source.add("name", "CMGE");
		
		Map<String, Object> map = mapper.mapping(source);
		System.out.println(map);
	}
	
	@Test
	public void testParameterToParameterMapper() throws Exception {
		ParametersToParametersMapper<Object> mapper = new ParametersToParametersMapper<Object>();
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
		
		Parameters<String, Object> source = new DefaultParameters<String, Object>();
		source.add("id", 123);
		source.add("name", "CMGE");
		
		Parameters<String, Object> parameter = mapper.mapping(source);
		System.out.println(parameter);
	}	
}
