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
 * Create Date : 2017-8-23
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * HTTP Content-Encoding编码类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ContentEncodingEnum implements Enumerable<Integer> {
	
	/** Brotli压缩算法  */
	BR("br", "http.content-encoding.br"),
	
	/** compress压缩算法 */
	COMPRESS("compress", "http.content-encoding.compress"),
	
	/** deflate压缩算法（zlib结构）*/
	DEFLATE("deflate", "http.content-encoding.deflate"),
	
	/** gzip压缩算法 */
	GZIP("gzip", "http.content-encoding.gzip"),
	
	/** 不对实体进行编码 */
	IDENTITY("identity", "http.content-encoding.identity")
	;
	
	private static final Map<Integer, ContentEncodingEnum> KEY_MAPPINGS = MapUtils.newHashMap(5);
	private static final Map<String, ContentEncodingEnum> TYPE_AND_NAME_MAPPINGS = MapUtils.newHashMap(5);
	
	/** 键 */
	private final int key;
	
	/** 编码类型 */
	private final String type;
	
	/** 消息 */
	private final String message;
	
	static {
		for (ContentEncodingEnum encoding : values()) {
			KEY_MAPPINGS.put(encoding.key, encoding);
			TYPE_AND_NAME_MAPPINGS.put(encoding.type.toUpperCase(), encoding);
			TYPE_AND_NAME_MAPPINGS.put(encoding.name(), encoding);
		}
	}
		
	private ContentEncodingEnum(String type, String message) {
		this.key = ordinal();
		this.type = type;
		this.message = MessageUtils.getClassMessage(getClass(), message);
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
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}

	/**
	 * 判断指定的类型或名称是否匹配当前枚举
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	@Override
	public boolean matches(String typeOrName) {
		return this.type.equalsIgnoreCase(typeOrName) || this.name().equalsIgnoreCase(typeOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static ContentEncodingEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的类型或名称解析成枚举对象
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	public static ContentEncodingEnum resolve(String typeOrName) {
		return typeOrName != null ? TYPE_AND_NAME_MAPPINGS.get(typeOrName.toUpperCase()) : null;
	}

}
