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

import java.io.File;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.sniper.commons.util.StringUtils;

/**
 * 本地文件资源类型的Multipart请求体处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class FileMultipartBodyHandler extends AbstractSourceMultipartBodyHandler {
	
	public FileMultipartBodyHandler() {
		super(File.class, File[].class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception {
		if (requestBody instanceof File)
			addBodyByFile(builder, name, (File) requestBody, contentType);
		else
			addBodyByFiles(builder, name, (File[]) requestBody, contentType);
	}
	
	/**
	 * 添加本地文件请求体
	 * @author Daniele 
	 * @param builder
	 * @param name
	 * @param file
	 * @param contentType
	 */
	protected void addBodyByFile(MultipartEntityBuilder builder, String name, File file, ContentType contentType) {
		if (StringUtils.isBlank(name))
			name = SINGLE_FILE_PARAMETER_NAME;
		
		logger.debug("Add file request body [name:{},value:{}]", name, file);
		builder.addPart(name, new FileBody(file, contentType, file.getName()));
	}
	
	/**
	 * 添加多个本地文件请求体
	 * @author Daniele 
	 * @param builder
	 * @param name
	 * @param files
	 * @param contentType
	 */
	protected void addBodyByFiles(MultipartEntityBuilder builder, String name, File[] files, ContentType contentType) {
		if (StringUtils.isBlank(name))
			name = MULTI_FILE_PARAMETER_NAME;
		
		logger.debug("Add files request body [name:{},value:{}]", name, files);
		for (File file : files ) {
			builder.addPart(name, new FileBody(file, contentType, file.getName()));
		}
	}

}
