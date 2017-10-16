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
 * Create Date : 2017-10-16
 */

package org.sniper.http.httpclient.v4.handler.multipart;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.commons.util.AssertUtils;

/**
 * Multipart请求体处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractMultipartBodyHandler implements MultipartBodyHandler {
	
	protected final Logger logger;
	
	protected final List<Class<?>> supportedBodyTypes;
	
	protected AbstractMultipartBodyHandler(Class<?>... supportedBodyTypes) {
		AssertUtils.assertNotEmpty(supportedBodyTypes, "Supported body types must not be null or empty");
		this.supportedBodyTypes = Collections.unmodifiableList(Arrays.asList(supportedBodyTypes));
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	@Override
	public List<Class<?>> getSupportedBodyTypes() {
		return supportedBodyTypes;
	}
	
	@Override
	public boolean support(Object requestBody) {
		if (requestBody == null)
			return false;
		
		Class<?> bodyType = requestBody.getClass();
		for (Class<?> supportedBodyType : supportedBodyTypes) {
			if (supportedBodyType.isAssignableFrom(bodyType)) 
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canHandle(Object requestBody) {
		return support(requestBody);
	}
	
}
