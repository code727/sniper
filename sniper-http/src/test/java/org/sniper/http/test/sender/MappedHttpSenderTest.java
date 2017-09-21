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
 * Create Date : 2017年9月15日
 */

package org.sniper.http.test.sender;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.response.BaseFullResponse;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.http.MappedHttpSender;
import org.sniper.http.test.domain.Developer;
import org.sniper.test.spring.JUnit4SpringContextTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MappedHttpSenderTest extends JUnit4SpringContextTestCase {
	
	@Autowired
	private MappedHttpSender mappedHttpSender;
	
	private Map<String, Object> parameters;
	
	private Map<String, Object> requestBody;
	
	@Before
	public void init() {
		parameters = MapUtils.newLinkedHashMap();
		parameters.put("id", "9527");
		parameters.put("name", "杜斌");
		parameters.put("birthday", DateUtils.dateToString(new Date()));
		
		requestBody = MapUtils.newLinkedHashMap();
		requestBody.put("id", "9527");
		requestBody.put("name", "杜斌");
		requestBody.put("birthday", new Date());
	}
	
//	@Test
	public void testGetBean() throws Exception {
		BaseFullResponse<Developer> response = mappedHttpSender.request("getBean", parameters);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}
	
//	@Test
	public void testPostBean() throws Exception {
		BaseFullResponse<Developer> response = mappedHttpSender.request("postBean", parameters);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}
	
	@Test
	public void testPostBodyBean() throws Exception {
		BaseFullResponse<Developer> response = mappedHttpSender.requestByBody("postBodyBean", requestBody);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}

}
