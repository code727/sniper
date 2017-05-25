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

package org.workin.beans.test;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.workin.beans.mapper.BeanToBeanMapper;
import org.workin.beans.mapper.BeanToParameterMapper;
import org.workin.beans.mapper.MapToBeanMapper;
import org.workin.beans.mapper.MapToMapMapper;
import org.workin.beans.mapper.MapToParameterMapper;
import org.workin.beans.mapper.MapperRule;
import org.workin.beans.mapper.ParameterToBeanMapper;
import org.workin.beans.mapper.ParameterToMapMapper;
import org.workin.beans.mapper.ParameterToParameterMapper;
import org.workin.beans.parameter.MapParameter;
import org.workin.beans.parameter.Parameter;
import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
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
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("userId", "user_id"));
		rules.add(new MapperRule("userName", "user_name"));
		mapper.setMapperRules(rules);
		
		Map<String, Object> source = MapUtils.newHashMap();
		source.put("userId", "123456");
		source.put("userName", "daniele");
		
		Map<String, Object> result = mapper.mapping(source);
		System.out.println(result);
	}
	
	@Test
	public void testBeanToBeanMapper() throws Exception {
		BeanToBeanMapper<User, Company> mapper = new BeanToBeanMapper<User, Company>(Company.class);
		Set<MapperRule> rules = CollectionUtils.newHashSet();
//		rules.add(new MapperRule("id", "id"));
//		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		
		Company company = (Company) mapper.mapping(source);
		System.out.println(company.getId());
		System.out.println(company.getName());
	}
	
//	@Test
//	public void testBeanToMapMapper() throws Exception {
//		BeanToMapMapper<User, Object> mapper = new BeanToMapMapper<User, Object>();
//		Set<MapperRule> rules = CollectionUtils.newHashSet();
//		rules.add(new MapperRule("id", "user_id"));
//		rules.add(new MapperRule("name", "user_name"));
//		mapper.setMapperRules(rules);
//		
//		User source = new User();
//		source.setId(123L);
//		source.setName("CMGE");
//		System.out.println(mapper.mapping(source));
//	}
	
//	@Test
	public void testBeanToParameter() throws Exception {
		BeanToParameterMapper<User, Object> mapper = new BeanToParameterMapper<User, Object>();
		Set<MapperRule> rules = CollectionUtils.newHashSet();
//		rules.add(new MapperRule("id", "user_id"));
//		rules.add(new MapperRule("name", "user_name"));
		mapper.setMapperRules(rules);
		
		User source = new User();
		source.setId(123L);
		source.setName("CMGE");
		
		System.out.println(mapper.mapping(source).getParameters());
	}
	
//	@Test
	public void testMapToBeanMapper() throws Exception {
		MapToBeanMapper<Object, User> mapper = new MapToBeanMapper<Object, User>(User.class);
		Set<MapperRule> rules = CollectionUtils.newHashSet();
//		rules.add(new MapperRule("id", "id"));
//		rules.add(new MapperRule("name", "name"));
		mapper.setMapperRules(rules);
		
		Map<String, Object> map = MapUtils.newHashMap();
		map.put("id", 123L);
		map.put("name", "CMGE");
		
		User user = mapper.mapping(map);
		System.out.println(user.getId());
		System.out.println(user.getName());
	}
	
//	@Test
	public void testParameterToMapMapper() throws Exception {
		Parameter<String, Object> source = new MapParameter<String, Object>();
		source.add("id", 123);
		source.add("name", "CMGE");
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		
		ParameterToMapMapper<Object> mapper = new ParameterToMapMapper<Object>(rules);
		Map<String, Object> map = mapper.mapping(source);
		System.out.println(map);
	}
	
//	@Test
	public void testParameterToParameterMapper() throws Exception {
		Parameter<String, Object> source = new MapParameter<String, Object>();
		source.add("id", 123);
		source.add("name", "CMGE");
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		
		ParameterToParameterMapper<Object> mapper = new ParameterToParameterMapper<Object>(rules);
		Parameter<String, Object> parameter = mapper.mapping(source);
		System.out.println(parameter);
	}
	
//	@Test
	public void testMapToParameterMapper() throws Exception {
		Map<String, Object> source = MapUtils.newHashMap();
		source.put("id", 123);
		source.put("name", "CMGE");
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
		rules.add(new MapperRule("id", "user_id"));
		rules.add(new MapperRule("name", "user_name"));
		
		MapToParameterMapper<Object> mapper = new MapToParameterMapper<Object>();
		mapper.setMapperRules(rules);
		System.out.println(mapper.mapping(source));
	}
	
//	@Test
	public void testParameterToBeanMapper() throws Exception {
		Parameter<String, Object> source = new MapParameter<String, Object>();
		source.add("id", 123L);
		source.add("name", "CMGE");
		
		Set<MapperRule> rules = CollectionUtils.newHashSet();
//		rules.add(new MapperRule("_id", "id"));
//		rules.add(new MapperRule("_name", "name"));
		
		ParameterToBeanMapper<Object, User> mapper = new ParameterToBeanMapper<Object, User>(User.class);
		mapper.setMapperRules(rules);
		
		User user = mapper.mapping(source);
		System.out.println(user.getId());
		System.out.println(user.getName());
	}

}
