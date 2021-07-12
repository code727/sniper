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
 * Create Date : 2017-10-13
 */

package org.sniper.http.httpclient.v4.handler.multipart;

import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;

/**
 * 复合Multipart请求体处理器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractCompositeMultipartBodyHandler<T> extends AbstractMultipartBodyHandler {
		
	/** 复合关联的多个Multipart请求体处理器列表 */
	protected final List<MultipartBodyHandler> multipartBodyHandlers;
	
	/** 默认的Multipart请求体处理器 */
	private MultipartBodyHandler defaultMultipartBodyHandler;
			
	protected AbstractCompositeMultipartBodyHandler(Class<?>... supportedBodyTypes) {
		this(null, supportedBodyTypes);
	}
		
	protected AbstractCompositeMultipartBodyHandler(List<MultipartBodyHandler> multipartBodyHandlers, Class<?>... supportedBodyTypes) {
		super(supportedBodyTypes);
		
		if (CollectionUtils.isNotEmpty(multipartBodyHandlers))
			this.multipartBodyHandlers = multipartBodyHandlers;
		else {
			this.multipartBodyHandlers = CollectionUtils.newArrayList();
			this.multipartBodyHandlers.add(new FileMultipartBodyHandler());
			this.multipartBodyHandlers.add(new InputStreamMultipartBodyHandler());
			this.multipartBodyHandlers.add(new ApacheFileItemMultipartBodyHandler());
			this.multipartBodyHandlers.add(new SpringResourceMultipartBodyHandler());
			this.multipartBodyHandlers.add(new SpringMultipartFileBodyHandler());
		}
		
		this.defaultMultipartBodyHandler = new BinaryMultipartBodyHandler();
	}

	/**
	 * 获取默认的Multipart请求体处理器
	 * @author Daniele 
	 * @return
	 */
	public MultipartBodyHandler getDefaultMultipartBodyHandler() {
		return defaultMultipartBodyHandler;
	}

	/**
	 * 获取默认的Multipart请求体处理器
	 * @author Daniele 
	 * @param defaultMultipartBodyHandler
	 */
	public void setDefaultMultipartBodyHandler(MultipartBodyHandler defaultMultipartBodyHandler) {
		AssertUtils.assertNotNull(defaultMultipartBodyHandler, "Default multipart body handler must not be null");
		this.defaultMultipartBodyHandler = defaultMultipartBodyHandler;
	}
	
	/**
	 * 获取匹配与指定请求体的Multipart处理器
	 * @author Daniele 
	 * @param requestBody
	 * @return
	 */
	protected MultipartBodyHandler getMatchedMultipartBodyHandler(Object requestBody) {
		if (canHandle(requestBody))
			return this;
			
		for (MultipartBodyHandler multipartBodyHandler : multipartBodyHandlers) {
			if (multipartBodyHandler.canHandle(requestBody))
				return multipartBodyHandler;
		}
		
		return defaultMultipartBodyHandler;
	}
		
}
