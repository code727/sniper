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

import org.workin.commons.util.StringUtils;
import org.workin.serialization.SerializationException;
import org.workin.support.codec.CodecSupport;

/**
 * @description JSON序列器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJsonSerializer extends CodecSupport implements JsonSerializer {
	
	/** 序列化日期时指定的格式 */
	private String dateFormat;
	
	/** 序列化结果类型字符串 */
	private String typeClass;
	
	/** 序列化结果类型 */
	private Class<?> type;
	
	protected AbstractJsonSerializer() {}
		
	protected AbstractJsonSerializer(String typeClass) {
		setTypeClass(typeClass);
	}
	
	protected AbstractJsonSerializer(Class<?> type) {
		setTypeClass(typeClass);
	}
	
	@Override
	public String getDateFormat() {
		return dateFormat;
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
		
	@Override
	public String getTypeClass() {
		return typeClass;
	}
	
	@Override
	public void setTypeClass(String typeClass) {
		if (StringUtils.isNotBlank(typeClass) && !(typeClass.equals(this.typeClass))) {
			this.typeClass = typeClass;
			try {
				this.type = Class.forName(typeClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setType(Class<?> type) {
		if (type != null && type != this.type) {
			this.type = type;
			this.typeClass = type.getName();
		}
	}

	@Override
	public Class<?> getType() {
		return type;
	}
	
	/**
	 * @description 从字符串中判断出是否为一个JSON数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonString
	 * @return
	 */
	protected boolean isJsonArray(String jsonString) {
		return StringUtils.startsWith(jsonString, "[") && StringUtils.endsWith(jsonString, "]");
	}
	
	@Override
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		return deserialize(bytes, null);
	}
	
}
