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

package org.sniper.serialization.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.sniper.commons.util.IOUtils;
import org.sniper.serialization.SerializationException;
import org.sniper.serialization.Serializer;

/**
 * JDK原生序列器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class JdkSerializer implements Serializer {
	
	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
			objectOutputStream.writeObject(t);  
			objectOutputStream.flush();
			byte[] bytes = byteArrayOutputStream.toByteArray();
			return bytes;
		} catch (IOException e) {
			throw new SerializationException("Cannot serialize", e);
		} finally {
			try {
				IOUtils.close(objectOutputStream);
				IOUtils.close(byteArrayOutputStream);
			} catch (IOException e) {
				throw new SerializationException("Cannot serialize", e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			T result = (T) objectInputStream.readObject();
			return result;
		} catch (Exception e) {
			throw new SerializationException("Cannot deserialize", e);
		} finally {
			try {
				IOUtils.close(objectInputStream);
				IOUtils.close(byteArrayInputStream);
			} catch (IOException e) {
				throw new SerializationException("Cannot deserialize", e);
			}
		}
	}

}
