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
 * Create Date : 2016-7-13
 */

package org.sniper.serialization.json.jackson.codehaus;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.sniper.commons.util.DateUtils;

/**
 * CodehausJackson 日期序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CodehausJacksonDateSerializer extends JsonSerializer<Date> {
	
	private String dateFormat;
	
	public CodehausJacksonDateSerializer() {
		this(null);
	}
	
	public CodehausJacksonDateSerializer(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Override
	public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) 
			throws IOException, JsonProcessingException {
		
		jgen.writeString(DateUtils.dateToString(date, dateFormat));  
	}

}
