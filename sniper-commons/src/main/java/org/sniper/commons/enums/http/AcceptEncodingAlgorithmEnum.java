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
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum AcceptEncodingAlgorithmEnum {
	
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
	ANY("*");
	
	private static final Map<String, AcceptEncodingAlgorithmEnum> mappings = MapUtils.newHashMap(13);
	
	/** 算法模式 */
	private final String mode;
	
	static {
		for (AcceptEncodingAlgorithmEnum acceptEncoding : values()) {
			mappings.put(acceptEncoding.name(), acceptEncoding);
		}
	}
	
	private AcceptEncodingAlgorithmEnum(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}

	/**
	 * 判断指定的算法模式是否匹配一个AcceptEncodingAlgorithmEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mode
	 * @return
	 */
	public boolean matches(String mode) {
		return this.mode.equalsIgnoreCase(mode);
	}

	/**
	 * 将指定的算法模式解析成AcceptEncodingAlgorithmEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param mode
	 * @return
	 */
	public static AcceptEncodingAlgorithmEnum resolve(String mode) {
		return (mode != null ? mappings.get(mode.toLowerCase()) : null);
	}
	
}
