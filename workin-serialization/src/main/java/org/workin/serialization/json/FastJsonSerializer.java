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

package org.workin.serialization.json;


import org.workin.commons.util.StringUtils;
import org.workin.serialization.SerializationException;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 阿里FastJson序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class FastJsonSerializer extends AbstractJsonSerializer {
	
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		 SerializeWriter out = null;
		 try {
			 out = new SerializeWriter();
			 JSONSerializer serializer = new JSONSerializer(out);
			 
			 if (StringUtils.isNotBlank(dateFormat)) {
				 serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
				 serializer.setDateFormat(dateFormat);
			 }
			 
			 serializer.write(t);
			 
			 byte[] bytes = out.toBytes(getEncoding());
			 return bytes;
		 } catch (Exception e) {
			 throw new SerializationException("Cannot serialize", e);
		 } finally {
			 if (out != null)
				 out.close();
		 }
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public <T> T deserialize(String text, Class<T> type) throws SerializationException {
		DefaultJSONParser jsonParser = new DefaultJSONParser(text);
		
		if (StringUtils.isNotBlank(dateFormat)) 
			jsonParser.setDateFormat(dateFormat);
		
		Class<?> clazz = (type != null ? type : getType());
		return (T) (isJsonArray(text) ? jsonParser.parseArray(clazz) : jsonParser.parseObject(clazz));
	}

}
