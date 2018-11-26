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
 * Content-Encoding实体头内容编码转换算法枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum ContentEncodingEnum {
	
	/** Lempel-Ziv coding (LZ77) 压缩算法 */
	GZIP("gzip"),
	
	/** Lempel-Ziv-Welch (LZW) 压缩算法 */
	COMPRESS("compress"),
	
	/** zlib结构和deflate压缩算法 */
	DEFLATE("deflate"),
	
	IDENTITY("identity"),
	
	/** Brotli算法 */
	BR("br");
	
	private static final Map<String, ContentEncodingEnum> mappings = MapUtils.newHashMap(5);
	
	static {
		for (ContentEncodingEnum algorithm : values()) {
			mappings.put(algorithm.algorithm, algorithm);
		}
	}
	
	/** 编码转换算法名称 */
	private String algorithm;
	
	private ContentEncodingEnum(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public String getEncoding() {
		return algorithm;
	}

	/**
	 * 将指定的算法名称解析成ContentEncodingEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param algorithm
	 * @return
	 */
	public static ContentEncodingEnum resolve(String algorithm) {
		return (algorithm != null ? mappings.get(algorithm.toLowerCase()) : null);
	}

	/**
	 * 判断指定的算法名称是否匹配一个ContentEncodingEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param algorithm
	 * @return
	 */
	public boolean matches(String algorithm) {
		return this.algorithm.equalsIgnoreCase(algorithm);
	}

}
