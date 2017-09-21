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
 * Create Date : 2016-7-11
 */

package org.sniper.serialization;

/**
 * 序列器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Serializer {
	
	/**
	 * 将对象序列化为字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	public <T> byte[] serialize(T t) throws SerializationException;
	
	/**
	 * 将对象序列化为字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
//	public <T> String serializeToString(T t) throws SerializationException;
	
	/**
	 * 将对象序列化为指定编码格式的字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param t
	 * @param encoding
	 * @return
	 * @throws SerializationException
	 */
//	public <T> String serializeToString(T t, String encoding) throws SerializationException;
	
	/**
	 * 将字节数组反序列化
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bytes
	 * @return
	 * @throws SerializationException
	 */
	public <T> T deserialize(byte[] bytes) throws SerializationException;
		
}
