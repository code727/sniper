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
 * Create Date : 2017-10-13
 */

package org.sniper.http.httpclient.v4.handler.multipart;

import java.io.InputStream;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.sniper.commons.util.StringUtils;

/**
 * IO输入流资源类型的Multipart请求体处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class InputStreamMultipartBodyHandler extends AbstractSourceMultipartBodyHandler {
	
	public InputStreamMultipartBodyHandler() {
		super(InputStream.class, InputStream[].class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception {
		if (requestBody instanceof InputStream)
			addBodyByInputStream(builder, name, (InputStream) requestBody, contentType);
		else
			addBodyByInputStreams(builder, name, (InputStream[]) requestBody, contentType);
	}
	
	/**
	 * 添加本地文件请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param file
	 * @param contentType
	 */
	protected void addBodyByInputStream(MultipartEntityBuilder builder, String name, InputStream inputStream, ContentType contentType) {
		if (StringUtils.isBlank(name))
			name = SINGLE_FILE_PARAMETER_NAME;
			
		logger.debug("Add input stream request body [name:{},value:{}]", name, inputStream);
		builder.addPart(name, new InputStreamBody(inputStream, contentType, StringUtils.EMPTY));
	}
	
	/**
	 * 添加多个本地文件请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param files
	 * @param contentType
	 */
	protected void addBodyByInputStreams(MultipartEntityBuilder builder, String name, InputStream[] inputStreams, ContentType contentType) {
		if (StringUtils.isBlank(name))
			name = MULTI_FILE_PARAMETER_NAME;
		
		logger.debug("Add input streams request body [name:{},value:{}]", name, inputStreams);
		for (InputStream inputStream : inputStreams) {
			builder.addPart(name, new InputStreamBody(inputStream, contentType, StringUtils.EMPTY));
		}
	}

}
