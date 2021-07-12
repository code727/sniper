/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-1-5
 */

package org.sniper.http.httpclient.v4.handler.request;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.http.HttpMessageBuilder;
import org.sniper.http.headers.MediaType;
import org.sniper.http.headers.request.HttpRequestHeaders;
import org.sniper.http.httpclient.v4.builder.ApplicationFormMessageBuilder;
import org.sniper.http.httpclient.v4.builder.JsonFormMessageBuilder;
import org.sniper.http.httpclient.v4.builder.MultipartFormMessageBuilder;

/**
 * 请求处理器默认实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefualtRequestHandler implements RequestHandler {
	
	private final List<HttpMessageBuilder<HttpEntity>> messageBuilders;
	
	public DefualtRequestHandler() {
		this(null);
	}
	
	public DefualtRequestHandler(List<HttpMessageBuilder<HttpEntity>> messageBuilders) {
		if (CollectionUtils.isNotEmpty(messageBuilders))
			this.messageBuilders = messageBuilders;
		else {
			this.messageBuilders = CollectionUtils.newArrayList();
			this.messageBuilders.add(new ApplicationFormMessageBuilder());
			this.messageBuilders.add(new MultipartFormMessageBuilder());
			
			/* 必须在MultipartFormMessageBuilder之后加入。
			 * 如果在MultipartFormMessageBuilder之前加入，则requestBody参数中如果包含有文件资源，
			 * 下面的handle方法用的是JsonFormMessageBuilder执行消息构建操作，会将requestBody中的文件资源作为JSON字符串而被序列化，产生不正确的结果
			 */
			this.messageBuilders.add(new JsonFormMessageBuilder());
		}
	}
	
	@Override
	public void handle(HttpEntityEnclosingRequestBase httpRequest, String url, HttpRequestHeaders requestHeaders,
			Object requestBody, String encoding) throws Exception {
		
		MediaType contentType = (requestHeaders != null ? requestHeaders.getContentType() : null);
		
		for (HttpMessageBuilder<HttpEntity> builder : this.messageBuilders) {
			if (builder.support(requestBody, contentType)) {
				httpRequest.setEntity(builder.build(url, requestBody, contentType, encoding));
				break;
			}
		}
	}
			
}
