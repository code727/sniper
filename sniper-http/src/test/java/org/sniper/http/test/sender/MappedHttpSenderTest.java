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

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Before;
import org.junit.Test;
import org.sniper.commons.LinkedMultiValueMap;
import org.sniper.commons.MultiValueMap;
import org.sniper.commons.response.Response;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.http.MappedHttpSender;
import org.sniper.http.headers.MediaType;
import org.sniper.test.spring.JUnit4SpringContextTestCase;
import org.snipersite.domain.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestTemplate;

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
		Response<Developer> response = mappedHttpSender.request("getBean", parameters);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}
	
	@Test
	public void testPostBean() throws Exception {
		Response<Developer> response = mappedHttpSender.request("postBean", parameters);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}
	
//	@Test
	public void testPostUpload() throws Exception {
//		Map<String, Object> requestBody = MapUtils.newHashMap();
//		requestBody.put("file", new File("C:/Users/Daniele/Desktop/新建.txt"));
//		requestBody.put("age", 34);
//		requestBody.put("name", "杜斌");
//		requestBody.put("loginName", null);
		
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		factory.setSizeThreshold(4096);  
		factory.setRepository(new File("C:/Users/Daniele/Desktop/新建.txt"));
		FileItem fileItem = factory.createItem("file", MediaType.MULTIPART_FORM_DATA.getType(), true,
				factory.getRepository().getName());
		fileItem.getOutputStream();
		
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("file", fileItem);
		multiValueMap.add("age", 34);
		multiValueMap.add("name", "杜斌");
		multiValueMap.add("loginName", null);
//		
//		
//		requestBody.put("file", fileItem);
//		requestBody.put("age", 34);
//		requestBody.put("name", "杜斌");
//		requestBody.put("loginName", null);
		
		Response<Object> response = mappedHttpSender.requestByBody("postUpload", multiValueMap);
		System.out.println(response.wasSuccess());
	}
	
//	@Test
	public void testPostUploads() throws Exception {
		
//		MultiValueMap<String, Object> fileBody = new LinkedMultiValueMap<String, Object>();
//		fileBody.add("files", new File("C:/Users/Daniele/Desktop/新建.txt"));
//		for (Entry<String, Object> entry : this.requestBody.entrySet()) {
//			fileBody.add(entry.getKey(), entry.getValue());
//		}
		
		File[] files = new File[] { new File("C:/Users/Daniele/Desktop/新建.txt") };
		
		Response<Developer> response = mappedHttpSender.requestByBody("postUploads", files);
		if (response.wasSuccess()) {
			Developer developer = response.getData();
			System.out.println(developer.getId());
			System.out.println(developer.getName());
			System.out.println(developer.getBirthday());
		} else {
			System.out.println("Response code:" + response.getCode());
		}
	}
	
//	@Test
	public void testPostBodyBean() throws Exception {
		Response<Developer> response = mappedHttpSender.requestByBody("postBodyBean", requestBody);
		System.out.println(response.getCode());
		System.out.println(response.getData().getId());
		System.out.println(response.getData().getName());
		System.out.println(response.getData().getBirthday());
	}
	
//	@Test
	public void test() {
		String url = "http://localhost:8080/snipersite/test/post/uploads";
		MultiValueMap<String, Object> fileBody = new LinkedMultiValueMap<String, Object>();
		fileBody.add("files", new FileSystemResource("C:/Users/Daniele/Desktop/新建.txt"));
		for (Entry<String, Object> entry : this.requestBody.entrySet()) {
			fileBody.add(entry.getKey(), entry.getValue());
		}
		
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(url, fileBody, String.class);
		System.out.println(response);
	}	

}
