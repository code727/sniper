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
 * Create Date : 2015-7-9
 */

package org.workin.http.httpclient.v4;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.workin.http.HttpSender;

/**
 * @description HttpClient4.x发送器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpClientSender implements HttpSender, InitializingBean {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	@Override
	public <T> T request(String name) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T request(String name, Map<String, String> parameters)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param parameter
	 * @return
	 * @throws IOException 
	 */
	@Override
	public <T> T request(String name, Object parameter) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
