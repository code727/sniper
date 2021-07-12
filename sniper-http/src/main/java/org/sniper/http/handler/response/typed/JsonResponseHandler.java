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

package org.sniper.http.handler.response.typed;

import org.sniper.serialization.TypedSerializer;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;

/**
 * JSON响应处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class JsonResponseHandler extends AbstractTypedResponseHandler {
	
	public JsonResponseHandler() {
		super();
	}
	
	public JsonResponseHandler(JsonSerializer jsonSerializer) {
		super(jsonSerializer);
	}
		
	@Override
	protected TypedSerializer buildDefaultTypedSerializer() {
		return new FasterxmlJacksonSerializer();
	}	
	
}
