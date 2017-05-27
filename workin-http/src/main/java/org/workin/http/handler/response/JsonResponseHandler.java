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

import org.workin.commons.util.StringUtils;
import org.workin.serialization.json.JsonSerializer;
import org.workin.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * JSON响应处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonResponseHandler extends AbstractTypedResponseHandler {
	
	/** JSON序列化器 */
	private JsonSerializer jsonSerializer;
	
	public JsonResponseHandler() {
		this((JsonSerializer) null);
	}
	
	public JsonResponseHandler(JsonSerializer jsonSerializer) {
		if (jsonSerializer == null)
			this.jsonSerializer = new FasterxmlJacksonSerializer();
		else
			this.jsonSerializer = jsonSerializer;
	}
		
	public JsonSerializer getJsonSerializer() {
		return jsonSerializer;
	}

	public void setJsonSerializer(JsonSerializer jsonSerializer) {
		this.jsonSerializer = jsonSerializer;
	}

	@Override
	public <T> T handleResponse(String json, Class<T> type, Class<?> nestedType) throws Exception {
		if (StringUtils.isNotBlank(json)) {
			return (T) jsonSerializer.deserialize(json, type);
		}
		
		return null;
	}
	
}
