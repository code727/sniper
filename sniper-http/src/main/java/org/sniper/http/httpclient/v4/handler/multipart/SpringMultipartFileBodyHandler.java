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
import org.springframework.web.multipart.MultipartFile;

/**
 * org.springframework.web.multipart.MultipartFile资源类型的Multipart请求体处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SpringMultipartFileBodyHandler extends AbstractSourceMultipartBodyHandler {
	
	public SpringMultipartFileBodyHandler() {
		super(MultipartFile.class, MultipartFile[].class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception{
		if (requestBody instanceof MultipartFile)
			addBodyByMultipartFile(builder, name, (MultipartFile) requestBody, contentType);
		else
			addBodyByMultipartFiles(builder, name, (MultipartFile[]) requestBody, contentType);
	}
	
	/**
	 * 添加org.springframework.web.multipart.MultipartFile请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param multipartFile
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByMultipartFile(MultipartEntityBuilder builder, String name, MultipartFile multipartFile,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isBlank(name)) {
			name = multipartFile.getName();
			if (StringUtils.isBlank(name))
				name = SINGLE_FILE_PARAMETER_NAME;
		}
		
		logger.info("Add spring multipart file request body [name:{},value:{}]", name, multipartFile);
		builder.addBinaryBody(name, multipartFile.getInputStream(), contentType, multipartFile.getOriginalFilename());
	}
	
	/**
	 * 添加多个org.springframework.web.multipart.MultipartFile请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param multipartFiles
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByMultipartFiles(MultipartEntityBuilder builder, String name, MultipartFile[] multipartFiles,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isNotBlank(name)) {
			logger.info("Add spring multipart files request body [name:{},value:{}]", name, multipartFiles);
			for (MultipartFile multipartFile : multipartFiles) {
				builder.addBinaryBody(name, multipartFile.getInputStream(), contentType, multipartFile.getOriginalFilename());
			}
		} else {
			for (MultipartFile multipartFile : multipartFiles) {
				/* 从multipartFile对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
				name = multipartFile.getName();
				if (StringUtils.isBlank(name))
					name = MULTI_FILE_PARAMETER_NAME;
				
				logger.info("Add spring multipart files request body [name:{},value:{}]", name, multipartFile);
				builder.addBinaryBody(name, multipartFile.getInputStream(), contentType, multipartFile.getOriginalFilename());
			}
		}
	}
	
}
