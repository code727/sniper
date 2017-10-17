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

import org.apache.commons.fileupload.FileItem;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.sniper.commons.util.StringUtils;

/**
 * org.apache.commons.fileupload.FileItem资源类型的Multipart请求体处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ApacheFileItemMultipartBodyHandler extends AbstractSourceMultipartBodyHandler {
	
	public ApacheFileItemMultipartBodyHandler() {
		super(FileItem.class, FileItem[].class);
	}

	@Override
	public void addBodyPart(MultipartEntityBuilder builder, String name, Object requestBody, ContentType contentType) throws Exception{
		if (requestBody instanceof FileItem)
			addBodyByFileItem(builder, name, (FileItem) requestBody, contentType);
		else
			addBodyByFileItems(builder, name, (FileItem[]) requestBody, contentType);
	}
	
	/**
	 * 添加org.apache.commons.fileupload.FileItem请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param fileItem
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByFileItem(MultipartEntityBuilder builder, String name, FileItem fileItem,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isBlank(name)) {
			name = fileItem.getFieldName();
			if (StringUtils.isBlank(name))
				name = SINGLE_FILE_PARAMETER_NAME;
		}
		
		logger.info("Add file item request body [name:{},value:{}]", name, fileItem);
		builder.addBinaryBody(name, fileItem.getInputStream(), contentType, fileItem.getName());
	}
	
	/**
	 * 添加多个org.apache.commons.fileupload.FileItem请求体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param name
	 * @param fileItems
	 * @param contentType
	 * @throws IOException
	 */
	protected void addBodyByFileItems(MultipartEntityBuilder builder, String name, FileItem[] fileItems,
			ContentType contentType) throws IOException {
		
		if (StringUtils.isNotBlank(name)) {
			logger.info("Add file items request body [name:{},value:{}]", name, fileItems);
			for (FileItem fileItem : fileItems) {
				builder.addBinaryBody(name, fileItem.getInputStream(), contentType, fileItem.getName());
			}
		} else {
			for (FileItem fileItem : fileItems) {
				/* 从fileItem对象中获取域名，设置为文件参数名，未获取到时设置为全局默认的参数名 */
				name = fileItem.getFieldName();
				if (StringUtils.isBlank(name))
					name = MULTI_FILE_PARAMETER_NAME;
				
				logger.info("Add file items request body [name:{},value:{}]", name, fileItem);
				builder.addBinaryBody(name, fileItem.getInputStream(), contentType, fileItem.getName());
			}
		}
	}

}
