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

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * HTTP Content-Disposition消息头类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ContentDispositionEnum implements Enumerable<Integer> {
	
	/** 内联类型 */
	INLINE("inline", "http.content-disposition.inline"),
	
	/** 附件类型 */
	ATTACHMENT("attachment", "http.content-disposition.attachment"),
	
	/** 表单数据 */
	FORM_DATA("form-data", "http.content-disposition.form-data");
	
	private static final Map<Integer, ContentDispositionEnum> KEY_MAPPINGS = MapUtils.newHashMap(9);
	private static final Map<String, ContentDispositionEnum> TYPE_AND_NAME_MAPPINGS = MapUtils.newHashMap(11);
	
	/** 键 */
	private final int key;
	
	/** 编码类型 */
	private final String type;
	
	/** 消息 */
	private final String message;
	
	static {
		for (ContentDispositionEnum contentDisposition : values()) {
			KEY_MAPPINGS.put(contentDisposition.key, contentDisposition);
			TYPE_AND_NAME_MAPPINGS.put(contentDisposition.type.toUpperCase(), contentDisposition);
			TYPE_AND_NAME_MAPPINGS.put(contentDisposition.name(), contentDisposition);
		}
	}
		
	private ContentDispositionEnum(String type, String message) {
		this.key = ordinal();
		this.type = type;
		this.message = message;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean match(Integer key) {
		return key != null && this.key == key.intValue();
	}

	/**
	 * 判断指定的类型或名称是否匹配当前枚举
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	@Override
	public boolean match(String typeOrName) {
		return this.type.equalsIgnoreCase(typeOrName) || this.name().equalsIgnoreCase(typeOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static ContentDispositionEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的类型或名称解析成枚举对象
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	public static ContentDispositionEnum resolve(String typeOrName) {
		return typeOrName != null ? TYPE_AND_NAME_MAPPINGS.get(typeOrName.toUpperCase()) : null;
	}
			
}
