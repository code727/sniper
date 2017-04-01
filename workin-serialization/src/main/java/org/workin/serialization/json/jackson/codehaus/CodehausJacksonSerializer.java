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
 * Create Date : 2016-7-12
 */

package org.workin.serialization.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.workin.commons.util.DateUtils;
import org.workin.commons.util.StringUtils;
import org.workin.serialization.SerializationException;

/**
 * Jackson序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JacksonSerializer extends AbstractJsonSerializer {
	
	private ObjectMapper objectMapper;
	
	public JacksonSerializer() {
		this(null);
	}
	
	public JacksonSerializer(ObjectMapper objectMapper) {
		if (objectMapper != null )
			this.objectMapper = objectMapper;
		else {
			this.objectMapper = new ObjectMapper();
			this.objectMapper.setDateFormat(DateUtils.getDateFormat(dateFormat));
		}
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		if (objectMapper != null)
			this.objectMapper = objectMapper;
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		super.setDateFormat(dateFormat);
		
		if (StringUtils.isNotBlank(dateFormat))
			this.objectMapper.setDateFormat(DateUtils.getDateFormat(dateFormat));
		else
			this.objectMapper.setDateFormat(null);
	}

	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception e) {
			throw new SerializationException("Cannot serialize", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) throws SerializationException {
		try {
			return (T) this.objectMapper.readValue(bytes, 0, bytes.length, type != null ? type : getType());
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String text, Class<T> type) throws SerializationException {
		try {
			return (T) this.objectMapper.readValue(text, type != null ? type : getType());
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		}
	}
	
}
