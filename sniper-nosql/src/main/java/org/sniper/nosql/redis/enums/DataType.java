/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-1-16
 */

package org.sniper.nosql.redis.enums;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * 数据类型枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum DataType {
	
	/** 无类型 */
	NONE("none"), 
	/** 字符串类型 */
	STRING("string"),
	/** 列表类型 */
	LIST("list"), 
	/** 集合类型 */
	SET("set"), 
	/** 有序集合类型 */
	ZSET("zset"), 
	/** 哈希类型 */
	HASH("hash");

	private static final Map<String, DataType> codeLookup = MapUtils.newHashMap(6);

	static {
		for (DataType type : values()) {
			codeLookup.put(type.code, type);
		}
	}

	/** 类型编码 */
	private final String code;

	private DataType(String name) {
		this.code = name;
	}

	public String code() {
		return code;
	}
	
	/**
	 * 判断指定的编码是否匹配一个数据类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public boolean matches(String code) {
		return this.code.equalsIgnoreCase(code);
	}

	/**
	 * 根据编码解析出数据类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @return
	 */
	public static DataType resolve(String code) {
		return (code != null ? codeLookup.get(code.toLowerCase()) : null);
	}

}
