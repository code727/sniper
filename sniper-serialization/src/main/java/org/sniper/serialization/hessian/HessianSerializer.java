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
 * Create Date : 2017-12-11
 */

package org.sniper.serialization.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.Base64Utils;
import org.sniper.commons.util.IOUtils;
import org.sniper.serialization.AbstractTypedSerializer;
import org.sniper.serialization.SerializationException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HessianSerializer extends AbstractTypedSerializer {

	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
		AssertUtils.assertNotNull(t, "Serialized object must not be null");
		
		ByteArrayOutputStream byteArrayOutputStream = null;
		HessianOutput hessianOutput = null;  
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			hessianOutput = new HessianOutput(byteArrayOutputStream);
			hessianOutput.writeObject(t);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new SerializationException("Cannot serialize", e);
		} finally {
			try {
				IOUtils.close(byteArrayOutputStream);
			} catch (IOException e) {
				throw new SerializationException("Cannot serialize", e);
			}
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> targetType) throws SerializationException {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(bytes), "Deserialized byte array must not be null or empty");
		
		ByteArrayInputStream byteArrayInputStream = null;  
	    HessianInput hessianInput = null;
	    try {
	    	byteArrayInputStream = new ByteArrayInputStream(bytes);
	    	hessianInput = new HessianInput(byteArrayInputStream);
	    	return (T) hessianInput.readObject(safeDeserializeType(targetType));
	    } catch (IOException e) {
	    	throw new SerializationException("Cannot deserialize", e);
	    } finally {
			try {
				IOUtils.close(byteArrayInputStream);
			} catch (IOException e) {
				throw new SerializationException("Cannot deserialize", e);
			}
	    }
	}
	
	@Override
	public <T> T deserialize(String text, Class<T> targetType) throws SerializationException {
		return deserialize(Base64Utils.decodeToBytes(text), targetType);
	}

}
