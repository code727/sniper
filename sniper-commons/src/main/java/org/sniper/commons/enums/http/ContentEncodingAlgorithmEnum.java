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
public enum ContentEncodingAlgorithmEnum {
	
	/** Lempel-Ziv coding (LZ77) 压缩算法 */
	GZIP("gzip"),
	
	/** Lempel-Ziv-Welch (LZW) 压缩算法 */
	COMPRESS("compress"),
	
	/** zlib结构和deflate压缩算法 */
	DEFLATE("deflate"),
	
	IDENTITY("identity"),
	
	/** Brotli算法 */
	BR("br");
	
	private static final Map<String, ContentEncodingAlgorithmEnum> mappings = MapUtils.newHashMap(5);
	
	/** 算法模式 */
	private final String mode;
	
	static {
		for (ContentEncodingAlgorithmEnum algorithm : values()) {
			mappings.put(algorithm.mode, algorithm);
		}
	}
	
	private ContentEncodingAlgorithmEnum(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	/**
	 * 判断指定的算法模式是否匹配一个ContentEncodingAlgorithmEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mode
	 * @return
	 */
	public boolean matches(String mode) {
		return this.mode.equalsIgnoreCase(mode);
	}

	/**
	 * 将指定的算法模式解析成ContentEncodingAlgorithmEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mode
	 * @return
	 */
	public static ContentEncodingAlgorithmEnum resolve(String mode) {
		return (mode != null ? mappings.get(mode.toLowerCase()) : null);
	}

}
