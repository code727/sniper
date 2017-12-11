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

package org.sniper.web.spring.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian HTTP消息转换器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HessianHttpMessageConverter<T> extends AbstractHttpMessageConverter<T> {

	@Override
	protected boolean supports(Class<?> clazz) {
		return Serializable.class.isAssignableFrom(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		HessianInput hessianInput = new HessianInput(inputMessage.getBody());
		return (T) hessianInput.readObject(clazz);
	}

	@Override
	protected void writeInternal(T t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
		hessianOutput.writeObject(t);
		outputMessage.getBody().write(byteArrayOutputStream.toByteArray());
	}

}
