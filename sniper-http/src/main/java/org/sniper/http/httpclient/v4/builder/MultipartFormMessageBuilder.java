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
 * Create Date : 2017-9-28
 */

package org.sniper.http.httpclient.v4.builder;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.http.headers.MediaType;
import org.sniper.http.httpclient.v4.handler.multipart.ApacheFileItemMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.FileMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.InputStreamMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.MapMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.MultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.MultiValueMapMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.SpringMultiValueMapMultipartBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.SpringMultipartFileBodyHandler;
import org.sniper.http.httpclient.v4.handler.multipart.SpringResourceMultipartBodyHandler;

/**
 * multipart/form-data表单消息构建器实现类
 * @author  Daniele
 * @version 1.0
 */
public class MultipartFormMessageBuilder extends AbstractHttpClientMessageBuilder {
	
	/** 当前构建器所支持的所有Multipart请求体处理器  */
	protected final List<MultipartBodyHandler> multipartBodyHandlers;
			
	public MultipartFormMessageBuilder() {
		this(null);
	}
	
	public MultipartFormMessageBuilder(List<MultipartBodyHandler> multipartBodyHandlers) {
		super(MediaType.MULTIPART_FORM_DATA);
		
		if (CollectionUtils.isNotEmpty(multipartBodyHandlers))
			this.multipartBodyHandlers = multipartBodyHandlers;
		else {
			this.multipartBodyHandlers = CollectionUtils.newArrayList(8);
			this.multipartBodyHandlers.add(new FileMultipartBodyHandler());
			this.multipartBodyHandlers.add(new InputStreamMultipartBodyHandler());
			this.multipartBodyHandlers.add(new ApacheFileItemMultipartBodyHandler());
			this.multipartBodyHandlers.add(new SpringResourceMultipartBodyHandler());
			this.multipartBodyHandlers.add(new SpringMultipartFileBodyHandler());
			this.multipartBodyHandlers.add(new MapMultipartBodyHandler());
			this.multipartBodyHandlers.add(new SpringMultiValueMapMultipartBodyHandler());
			this.multipartBodyHandlers.add(new MultiValueMapMultipartBodyHandler());
		}
	}
	
	public List<MultipartBodyHandler> getMultipartBodyHandlers() {
		return multipartBodyHandlers;
	}

	public void setMultipartBodyHandlers(List<MultipartBodyHandler> multipartBodyHandlers) {
		AssertUtils.assertNotEmpty(multipartBodyHandlers, "Multipart body handlers must not be null or empty");
		this.multipartBodyHandlers.clear();
		this.multipartBodyHandlers.addAll(multipartBodyHandlers);
	}

	@Override
	public boolean support(Object requestBody, MediaType mediaType) {
		return isMultipart(requestBody) && super.support(requestBody, mediaType);
	}
	
	/**
	 * 判断是否为Multipart请求体
	 * @author Daniele 
	 * @param requestBody
	 * @return
	 */
	private boolean isMultipart(Object requestBody) {
		for (MultipartBodyHandler multipartBodyHandler : multipartBodyHandlers) {
			if (multipartBodyHandler.support(requestBody))
				return true;
		}
		return false;
	}
	
	/**
	 * 根据请求体获取能匹配处理的Multipart处理器
	 * @author Daniele 
	 * @param requestBody
	 * @return
	 */
	protected MultipartBodyHandler getMatchedMultipartBodyHandler(Object requestBody) {
		for (MultipartBodyHandler multipartBodyHandler : multipartBodyHandlers) {
			if (multipartBodyHandler.canHandle(requestBody))
				return multipartBodyHandler;
		}
		
		return null;
	}

	@Override
	public HttpEntity build(String url, Object requestBody, MediaType mediaType, String encoding) throws Exception {
		MultipartBodyHandler multipartBodyHandler = getMatchedMultipartBodyHandler(requestBody);
		if (multipartBodyHandler != null) {
			ContentType contentType = createContentType(mediaType, encoding);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setCharset(contentType.getCharset());
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartBodyHandler.addBodyPart(builder, null, requestBody, contentType);
			return builder.build();
		}
		
		throw new IllegalArgumentException(
				"Can not found matched multipart body handler for request body:[" + requestBody + "]");
	}

	
}
