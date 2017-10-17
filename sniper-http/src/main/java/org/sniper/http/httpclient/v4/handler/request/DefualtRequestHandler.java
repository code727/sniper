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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NetUtils;
import org.sniper.http.HttpMessageBuilder;
import org.sniper.http.headers.MediaType;
//import org.sniper.http.headers.MimeType;
import org.sniper.http.headers.request.HttpRequestHeaders;
import org.sniper.http.httpclient.v4.builder.ApplicationFormMessageBuilder;
import org.sniper.http.httpclient.v4.builder.MultipartFormMessageBuilder;

/**
 * 请求处理器默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
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
		
	/**
	 * 根据url中的查询字符串构建NameValuePair对象列表
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param url
	 * @return
	 */
	protected List<NameValuePair> buildeNameValuePairByQueryString(String url) {
		Map<String, String> parameterMap = NetUtils.getParameterMap(url);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		if (MapUtils.isNotEmpty(parameterMap)) {
			Iterator<Entry<String, String>> iterator = parameterMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> parameter = iterator.next();
				nameValueList.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
			}
		}
		return nameValueList;
	}
	
	/**
	 * 添加查询参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param url
	 * @param contentType
	 */
	protected void addParameteres(MultipartEntityBuilder builder, String url, ContentType contentType) {
		Map<String, String> parameterMap = NetUtils.getParameterMap(url);
		if (MapUtils.isNotEmpty(parameterMap)) {
			Iterator<Entry<String, String>> iterator = parameterMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> parameter = iterator.next();
				builder.addPart(parameter.getKey(), new StringBody(parameter.getValue(), contentType));
			}
		}
	}
	
	/**
	 * 添加RequestBody
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param requestBody
	 * @param contentType
	 */
	@SuppressWarnings("unchecked")
	protected void addRequestBody(MultipartEntityBuilder builder, Object requestBody, ContentType contentType) {
		if (requestBody instanceof Map) {
			Map<String, Object> mapBody = (Map<String, Object>) requestBody;
			if (MapUtils.isNotEmpty(mapBody)) {
				Iterator<Entry<String, Object>> iterator = mapBody.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, Object> parameter = iterator.next();
					builder.addPart(parameter.getKey(), new StringBody(parameter.getValue().toString(), contentType));
				}
			}
		}
		
//		if (requestBody instanceof File) {
//			File file = (File) requestBody;
//			builder.addPart("file", new FileBody(file, contentType, file.getName()));
//		} else if (requestBody instanceof InputStream) {
//			InputStream input = (InputStream) requestBody;
//			builder.addPart("file", new InputStreamBody(input, contentType, "input"));
//		} else {
//			
//		}
	}
	
}
