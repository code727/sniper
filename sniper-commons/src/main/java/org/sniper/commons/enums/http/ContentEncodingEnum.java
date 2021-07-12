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

import org.sniper.commons.util.MapUtils;

/**
 * Content-Encoding编码转换算法枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ContentEncodingEnum {
	
	/** gzip压缩算法 */
	GZIP("gzip"),
	
	/** compress压缩算法 */
	COMPRESS("compress"),
	
	/** deflate压缩算法（zlib结构）*/
	DEFLATE("deflate"),
	
	/** 不对实体进行编码 */
	IDENTITY("identity"),
	
	/** Brotli压缩算法  */
	BR("br");
	
	private static final Map<String, ContentEncodingEnum> mappings = MapUtils.newHashMap(5);
	
	/** 算法名称 */
	private final String algorithm;
	
	static {
		for (ContentEncodingEnum encoding : values()) {
			mappings.put(encoding.algorithm, encoding);
		}
	}
	
	private ContentEncodingEnum(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * 判断指定的算法是否匹配当前枚举
	 * @author Daniele 
	 * @param algorithm
	 * @return
	 */
	public boolean matches(String algorithm) {
		return this.algorithm.equalsIgnoreCase(algorithm);
	}

	/**
	 * 将指定的算法解析成枚举对象
	 * @author Daniele 
	 * @param algorithm
	 * @return
	 */
	public static ContentEncodingEnum resolve(String algorithm) {
		return algorithm != null ? mappings.get(algorithm.toLowerCase()) : null;
	}

}
