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

import org.sniper.commons.util.IOUtils;
import org.sniper.serialization.AbstractSerializer;
import org.sniper.serialization.SerializationException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian序列化器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HessianSerializer extends AbstractSerializer {

	@Override
	public <T> byte[] serialize(T t) throws SerializationException {
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
	public <T> T deserialize(byte[] bytes) throws SerializationException {
		ByteArrayInputStream byteArrayInputStream = null;  
	    HessianInput hessianInput = null;
	    try {
	    	byteArrayInputStream = new ByteArrayInputStream(bytes);
	    	hessianInput = new HessianInput(byteArrayInputStream);
	    	return (T) hessianInput.readObject();
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

}
