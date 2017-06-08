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
 * Create Date : 2017-3-28
 */

package org.workin.serialization;

import org.workin.beans.DefaultTypedBean;
import org.workin.codec.Codecable;
import org.workin.commons.util.CodecUtils;

/**
 * 类型化序列器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractTypedSerializer extends DefaultTypedBean implements TypedSerializer, Codecable {
	
	private String encoding = CodecUtils.DEFAULT_ENCODING;
	
	@Override
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		return (T) deserialize(bytes, getType());
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> type) throws SerializationException {
		return deserialize(CodecUtils.bytesToString(bytes, encoding), type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String text) throws SerializationException {
		return (T) deserialize(text, getType());
	}

}
