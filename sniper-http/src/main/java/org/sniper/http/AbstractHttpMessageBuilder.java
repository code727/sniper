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
 * Create Date : 2017-9-29
 */

package org.sniper.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.http.headers.MediaType;

/**
 * HTTP消息构建器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractHttpMessageBuilder<T> implements HttpMessageBuilder<T> {
	
	protected final List<MediaType> supportedMediaTypes;
	
	protected final MediaType defaultMediaType;
		
	protected AbstractHttpMessageBuilder(MediaType... supportedMediaTypes) {
		AssertUtils.assertNotEmpty(supportedMediaTypes, "Supported media types must not be null or empty");
		this.supportedMediaTypes = Collections.unmodifiableList(Arrays.asList(supportedMediaTypes));
		this.defaultMediaType = supportedMediaTypes[0];
	}
		
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return supportedMediaTypes;
	}
	
	@Override
	public MediaType getDefaultMediaType() {
		return defaultMediaType;
	}
	
	@Override
	public boolean support(Object requestBody, MediaType mediaType) {
		if (mediaType == null)
			return true;
		
		for (MediaType supportedMediaType : supportedMediaTypes) {
			if (supportedMediaType.includes(mediaType)) {
				return true;
			}
		}
		
		return false;
	}
	
}
