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
 * Create Date : 2017-5-22
 */

package org.sniper.http.enums;

/**
 * HTTP MimeType 枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum MimeTypeEnum {
	
	APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
	APPLICATION_ATOM_XML("application/atom+xml"),
	APPLICATION_JSON("application/json"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_SVG_XML("application/svg+xml"),
	APPLICATION_XHTML_XML("application/xhtml+xml"),
	APPLICATION_XML("application/xml"),
	MULTIPART_FORM_DATA("multipart/form-data"),
	TEXT_HTML("text/html"),
	TEXT_PLAIN("text/plain"),
	TEXT_XML("text/xml");
	
	/** 全称*/
	private String type;
	
	private MimeTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}