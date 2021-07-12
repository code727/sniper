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
 * Create Date : 2016-7-18
 */

package org.sniper.serialization;

import org.sniper.beans.Typed;

/**
 * 类型化序列器接口
 * @author  Daniele
 * @version 1.0
 */
public interface TypedSerializer extends Typed, Serializer {
	
	/**
	 * 将字节数据反序列化成指定目标类型的对象
	 * @author Daniele 
	 * @param bytes
	 * @param targetType
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(byte[] bytes, Class<T> targetType) throws SerializationException;
	
	/**
	 * 将字符串反序列化成指定目标类型的对象
	 * @author Daniele 
	 * @param text
	 * @param targetType
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(String text, Class<T> targetType) throws SerializationException;
	
	/**
	 * 将字符串文本反序列化成全局目标类型的对象
	 * @author Daniele 
	 * @param text
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(String text) throws SerializationException;
	
}
