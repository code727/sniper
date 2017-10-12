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
 * Create Date : 2017-10-11
 */

package org.sniper.http.httpclient.v4.builder;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.sniper.http.AbstractHttpMessageBuilder;
import org.sniper.http.headers.MediaType;

/**
 * HttpClient消息构建器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractHttpClientMessageBuilder extends AbstractHttpMessageBuilder<HttpEntity> {
	
	protected AbstractHttpClientMessageBuilder(MediaType... supportedMediaTypes) {
		super(supportedMediaTypes);
	}
	
	/**
	 * 创建HttpClient ContentType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mediaType
	 * @param encoding
	 * @return
	 */
	protected ContentType createContentType(MediaType mediaType, String encoding) {
		MediaType supportedMediaType = (mediaType != null ? mediaType : defaultMediaType);
		Charset charset = supportedMediaType.getCharset();
		return ContentType.create(supportedMediaType.getType(), charset != null ? charset.name() : encoding); 
	}

}
