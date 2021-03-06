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
 * Create Date : 2017-8-22
 */

package org.sniper.http.headers;

import java.io.Serializable;

import org.sniper.commons.enums.http.ContentDispositionEnum;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;

/**
 * HTTP Content-Disposition消息头对象
 * @author  Daniele
 * @version 1.0
 */
public class ContentDisposition implements Serializable {
	
	private static final long serialVersionUID = 6265208558861975843L;

	/** Content-Disposition类型枚举  */
	private ContentDispositionEnum dispositionType;
	
	/** 表单字段名 */
	private String name;
	
	/** 传送文件的初始名称 */
	private String filename;
	
	public ContentDisposition() {
		this((String) null);
	}
	
	public ContentDisposition(ContentDispositionEnum dispositionType) {
		this(dispositionType, null);
	}
	
	public ContentDisposition(String filename) {
		this((String) null, filename);
	}
	
	public ContentDisposition(String name, String filename) {
		this(ContentDispositionEnum.INLINE, name, filename);
	}
	
	public ContentDisposition(ContentDispositionEnum dispositionType, String filename) {
		this(dispositionType, null, filename);
	}
	
	public ContentDisposition(ContentDispositionEnum dispositionType, String name, String filename) {
		setDispositionType(dispositionType);
		setName(filename);
		setFilename(filename);
	}

	public ContentDispositionEnum getDispositionType() {
		return dispositionType;
	}

	public void setDispositionType(ContentDispositionEnum dispositionType) {
		AssertUtils.assertNotNull(dispositionType, "Content disposition type must not be null");
		this.dispositionType = dispositionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(dispositionType.getType());
		
		if (StringUtils.isNotBlank(name)) 
			builder.append(";name=").append(StringUtils.DOUBLE_QUOTES).append(name).append(StringUtils.DOUBLE_QUOTES);
		
		if (StringUtils.isNotBlank(filename)) 
			builder.append(";filename=").append(StringUtils.DOUBLE_QUOTES).append(filename).append(StringUtils.DOUBLE_QUOTES);
		
		return builder.toString();
	}
	
}
