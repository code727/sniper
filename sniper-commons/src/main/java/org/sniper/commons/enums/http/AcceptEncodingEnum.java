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
 * Create Date : 2017-8-15
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * Accept-Encoding请求头编码算法枚举
 * @author  Daniele
 * @version 1.0
 */
public enum AcceptEncodingEnum {
	
	AES128GCM("aes128gcm"),
	BR("br"),
	BZIP("bzip"), 
	BZIP2("bzip2"),
	COMPRESS("compress"),
	DEFLATE("deflate"),
	EXI("exi"),
	GZIP("gzip"),
	IDENTITY("identity"),
	PACK200_GZIP("pack200-gzip"),
	X_COMPRESS("x-compress"),
	X_GZIP("x-gzip"),
	ANY("*")
	;
	
	private static final Map<String, AcceptEncodingEnum> mappings = MapUtils.newHashMap(13);
	
	/** 算法 */
	private final String algorithm;
	
	static {
		for (AcceptEncodingEnum acceptEncoding : values()) {
			mappings.put(acceptEncoding.algorithm, acceptEncoding);
		}
	}
	
	private AcceptEncodingEnum(String algorithm) {
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
	public static AcceptEncodingEnum resolve(String algorithm) {
		return algorithm != null ? mappings.get(algorithm.toLowerCase()) : null;
	}
		
}
