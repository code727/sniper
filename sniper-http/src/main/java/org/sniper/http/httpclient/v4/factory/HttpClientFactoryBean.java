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
 * Create Date : 2015-7-8
 */

package org.sniper.http.httpclient.v4.factory;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * HttpClient4.x工厂对象实现类
 * @author  Daniele
 * @version 1.0
 */
public class HttpClientFactoryBean implements HttpClientFactory {
	
	private HttpClientBuilder builder;
	
	public HttpClientFactoryBean() {
		this(null);
	}
	
	public HttpClientFactoryBean(HttpClientBuilder builder) {
		this.builder = (builder != null ? builder : HttpClients.custom());
	}

	@Override
	public HttpClient create() {
		return this.builder.build();
	}
		
}
