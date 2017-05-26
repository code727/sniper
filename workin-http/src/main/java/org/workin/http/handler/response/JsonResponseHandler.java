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
 * Create Date : 2017-5-22
 */

package org.workin.http.handler.response;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.json.JsonSerializer;
import org.workin.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * JSON响应处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonResponseHandler<T> extends AbstractResponseHandler<T> {
	
	/** JSON序列化器 */
	private JsonSerializer jsonSerializer;
	
	public JsonResponseHandler() {
		this((JsonSerializer) null);
	}
	
	public JsonResponseHandler(String typeClass) throws Exception {
		this(typeClass, null);
	}
	
	public JsonResponseHandler(Class<T> type) {
		this(type, null);
	}
	
	public JsonResponseHandler(JsonSerializer jsonSerializer) {
		initJsonSerializer(jsonSerializer);
	}
	
	public JsonResponseHandler(String typeClass, JsonSerializer jsonSerializer) throws Exception {
		initJsonSerializer(jsonSerializer);
		this.jsonSerializer.setTypeClass(typeClass);
	}
	
	public JsonResponseHandler(Class<T> type, JsonSerializer jsonSerializer) {
		initJsonSerializer(jsonSerializer);
		this.jsonSerializer.setType(type);
	}
	
	/**
	 * 初始化JSON序列器接口
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonSerializer
	 */
	private void initJsonSerializer(JsonSerializer jsonSerializer) {
		if (jsonSerializer == null)
			this.jsonSerializer = new FasterxmlJacksonSerializer();
		else
			this.jsonSerializer = jsonSerializer;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public T handleResponse(String json) throws ClientProtocolException, IOException {
		if (StringUtils.isNotBlank(json)) {
			return (T) jsonSerializer.deserialize(json);
		} else
			return null;
	}
	
}
