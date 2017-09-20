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
 * Create Date : 2017-5-19
 */

package org.sniper.http.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.sniper.http.MappedHttpAccessor;
import org.sniper.http.form.HttpForm;

/**
 * 已映射的Spring Rest发送器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class MappedRestSender extends MappedHttpAccessor {
	
	@Autowired(required = false)
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		
		if (this.restTemplate == null)
			this.restTemplate = new RestTemplate();
	}
	
	@Override
	protected <T> T doGetRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = getFormRegister().find(name);
		
		logger.debug("Execute {} request [{}] from form [{}]", HttpMethod.GET, url, name);
		String response = restTemplate.getForObject(url, String.class);
		return handleResponse(form, response);
	}

	@Override
	protected <T> T doPostRequest(String name, Object requestBody, Object param) throws Exception {
		String url = format(name, param);
		HttpForm form = getFormRegister().find(name);
		
		logger.debug("Execute {} request [{}] from form [{}]", HttpMethod.POST, url, name);
		String response = restTemplate.postForObject(url, requestBody, String.class);
		return handleResponse(form, response);
	}

	@Override
	protected <T> T doPutRequest(String name, Object requestBody, Object param) throws Exception {
		String url = format(name, param);
		
		logger.debug("Execute {} request [{}] from form [{}]", HttpMethod.PUT, url, name);
		restTemplate.put(url, requestBody, (Object) null);
		return null;
	}

	@Override
	protected <T> T doDeleteRequest(String name, Object param) throws Exception {
		String url = format(name, param);
		
		logger.debug("Execute {} request [{}] from form [{}]", HttpMethod.DELETE, url, name);
		restTemplate.delete(url, (Object) null);
		return null;
	}

}
