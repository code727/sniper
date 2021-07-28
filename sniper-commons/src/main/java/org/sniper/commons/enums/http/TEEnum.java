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
 * Create Date : 2017年9月13日
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * 传输编码(TE)类型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum TEEnum implements Enumerable<Integer> {
	
	/** compress编码 */
	COMPRESS("http.te.compress"),
	
	/** deflate编码 */
	DEFLATE("http.te.deflate"),
	
	/** gzip编码 */
	GZIP("http.te.gzip"),
	
	/** trailers编码 */
	TRAILERS("http.te.trailers")
	;
	
	private static final Map<Integer, TEEnum> KEY_MAPPINGS = MapUtils.newHashMap(4);
	private static final Map<String, TEEnum> NAME_MAPPINGS = MapUtils.newHashMap(4);
	
	/** 键 */
	private final int key;
	
	/** 编码类型 */
	private final String type;
	
	/** 消息 */
	private final String message;
	
	static {
		for (TEEnum te : values()) {
			KEY_MAPPINGS.put(te.key, te);
			NAME_MAPPINGS.put(te.name(), te);
		}
	}
	
	private TEEnum(String message) {
		this.key = ordinal();
		// 编码类型即为枚举对象名称的小写字符串
		this.type = name().toLowerCase();
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
	public boolean match(String typeOrName) {
		// 由于编码类型即为枚举对象名称的小写字符串，因此这里只用type字段比较即可
		return this.type.equalsIgnoreCase(typeOrName);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static TEEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的类型或名称解析成枚举对象
	 * @author Daniele 
	 * @param typeOrName
	 * @return
	 */
	public static TEEnum resolve(String typeOrName) {
		return typeOrName != null ? NAME_MAPPINGS.get(typeOrName.toUpperCase()) : null;
	}
	
}
