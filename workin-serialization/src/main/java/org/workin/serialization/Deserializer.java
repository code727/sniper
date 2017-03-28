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

/**
 * @description 反序列化器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Deserializer {
	
	/**
	 * 将字节数据反序列化成指定类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @param type
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(byte[] bytes, Class<T> type) throws SerializationException;
	
	/**
	 * 将字符串反序列化成指定类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @param type
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(String text, Class<T> type) throws SerializationException;
	
	/**
	 * 将字符串文本反序列化
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param text
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(String text) throws SerializationException;

}
