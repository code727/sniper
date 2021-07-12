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

package org.sniper.serialization;

import org.sniper.beans.Typed;
import org.sniper.beans.TypedBean;
import org.sniper.codec.Codecable;
import org.sniper.commons.util.CodecUtils;

/**
 * 类型化序列器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractTypedSerializer extends AbstractSerializer implements TypedSerializer, Codecable {
	
	private final Typed typed = new TypedBean();
	
	@Override
	public void setTargetType(Class<?> targetType) {
		typed.setTargetType(targetType);
	}
	
	@Override
	public Class<?> getTargetType() {
		return typed.getTargetType();
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		return (T) deserialize(bytes, getTargetType());
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> targetType) throws SerializationException {
		return deserialize(CodecUtils.bytesToString(bytes, getEncoding()), targetType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(String text) throws SerializationException {
		return (T) deserialize(text, getTargetType());
	}
	
	/**
	 * 获取一个安全的反序列化类型
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	protected Class<?> safeDeserializeType(Class<?> type) {
		return type != null ? type : typed.getTargetType();
	}

}
