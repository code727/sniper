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
 * Create Date : 2016年7月12日
 */

package org.workin.serialization.json.proxy;

import org.codehaus.jackson.map.ObjectMapper;
import org.workin.serialization.json.AbstractJsonSerializer;

/**
 * @description Jackson序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JacksonSerializer extends AbstractJsonSerializer {
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public <T> byte[] serialize(T t) throws Exception {
		return this.objectMapper.writeValueAsBytes(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes) throws Exception {
		return (T) this.objectMapper.readValue(bytes, 0, bytes.length, getType());
	}
	
}
