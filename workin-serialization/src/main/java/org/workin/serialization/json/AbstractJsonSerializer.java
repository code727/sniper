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
import org.workin.serialization.AbstractTypeSerializer;

/**
 * JSON序列器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractJsonSerializer extends AbstractTypeSerializer implements JsonSerializer {
	
	/** 序列化日期时指定的格式 */
	protected String dateFormat;
	
	@Override
	public String getDateFormat() {
		return dateFormat;
	}
	
	@Override
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
		
	/**
	 * 从字符串中判断出是否为一个JSON数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param jsonString
	 * @return
	 */
	protected boolean isJsonArray(String jsonString) {
		return StringUtils.startsWith(jsonString, "[{") && StringUtils.endsWith(jsonString, "}]");
	}
	
}
