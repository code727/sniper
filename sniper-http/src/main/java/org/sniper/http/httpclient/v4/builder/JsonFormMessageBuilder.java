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
 * Create Date : 2017年10月26日
 */

package org.sniper.http.httpclient.v4.builder;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.sniper.http.headers.MediaType;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * JSON表单消息构建器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JsonFormMessageBuilder extends AbstractHttpClientMessageBuilder {
	
	private final JsonSerializer jsonSerializer;
	
	public JsonFormMessageBuilder() {
		this(null);
	}
	
	public JsonFormMessageBuilder(JsonSerializer jsonSerializer) {
		super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
		if (jsonSerializer != null)
			this.jsonSerializer = jsonSerializer;
		else
			this.jsonSerializer = new FasterxmlJacksonSerializer();
	}

	@Override
	public HttpEntity build(String url, Object requestBody, MediaType mediaType, String encoding) throws Exception {
		ContentType contentType = createContentType(mediaType, encoding);
		return new ByteArrayEntity(jsonSerializer.serialize(requestBody), contentType);
	}

}
