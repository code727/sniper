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

import java.io.IOException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.sniper.commons.util.StringUtils;
import org.springframework.core.io.Resource;

/**
 * org.springframework.core.io.Resource资源类型的Multipart请求体处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class SpringResourceMultipartBodyHandler extends AbstractSourceMultipartBodyHandler {
	
	public SpringResourceMultipartBodyHandler() {
		super(Resource.class, Resource[].class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception{
		if (requestBody instanceof Resource)
			addBodyByResource(builder, name, (Resource) requestBody, contentType);
		else
			addBodyByResources(builder, name, (Resource[]) requestBody, contentType);
	}
	
	/**
	 * 添加org.springframework.core.io.Resource请求体
	 * @author Daniele 
	 * @param builder
	 * @param name
	 * @param resource
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByResource(MultipartEntityBuilder builder, String name, Resource resource,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isBlank(name))
			name = SINGLE_FILE_PARAMETER_NAME;
		
		logger.debug("Add spring resource file request body [name:{},value:{}]", name, resource);
		builder.addBinaryBody(name, resource.getInputStream(), contentType, resource.getFilename());
	}
	
	/**
	 * 添加多个org.springframework.core.io.Resource请求体
	 * @author Daniele 
	 * @param builder
	 * @param name
	 * @param resources
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByResources(MultipartEntityBuilder builder, String name, Resource[] resources,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isBlank(name))
			name = MULTI_FILE_PARAMETER_NAME;
		
		logger.debug("Add spring resource files request body [name:{},value:{}]", name, resources);
		for (Resource resource : resources) {
			builder.addBinaryBody(name, resource.getInputStream(), contentType, resource.getFilename());
		}
	}

}
