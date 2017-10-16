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

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * 二进制Multipart请求体处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
class BinaryMultipartBodyHandler extends AbstractMultipartBodyHandler {
	
	public BinaryMultipartBodyHandler() {
		super(Object.class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception {
		if (requestBody == null)
			return;
		
		logger.info("Add binary request body [name={},value={}]", name, requestBody);
		if (requestBody instanceof byte[])
			builder.addBinaryBody(name, (byte[]) requestBody, contentType, "");
		else {
			String text = requestBody.toString();
			if (text != null)
				builder.addBinaryBody(name, text.getBytes(contentType.getCharset()));
		}
	}

}
