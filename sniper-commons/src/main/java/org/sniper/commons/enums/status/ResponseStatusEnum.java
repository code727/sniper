/*
 * Copyright 2021 the original author or authors.
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
 * Create Date : 2021-7-12
 */

package org.sniper.commons.enums.status;

import java.text.MessageFormat;
import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;


/**
 * 响应状态枚举
 * @author  Daniele
 * @version 1.0
 */
public enum ResponseStatusEnum {
	
	/** 未知错误(-1)，作为系统兜底的错误码 */
	UNKNOWN_ERROR(-1),
	
	/** 
	 * 成功(0)</P> 
	 * 可用作{@link org.sniper.commons.response.Response}表示"响应成功"时的默认状态
	 */
	SUCCESS(0),
	
	/* 
	 * 注意：
	 * 1.状态码在[1,99]区间内的"成功"枚举项预留给后续版本扩展；
	 * 2.状态码在[100,399]区间内的"成功"枚举项直接使用{@link org.sniper.commons.enums.http.HttpStatusEnum}
	 *   的等值枚举项，不再在{@link org.sniper.commons.enums.status.ResponseStatusEnum}中定义。 
	 */
	 
	/** 
	 * 错误(400)，状态码等价于{@link org.sniper.commons.enums.http.HttpStatusEnum#BAD_REQUEST}</P> 
	 * 可用作{@link org.sniper.commons.response.Response}表示"响应错误"时的默认状态，其含义包括业务错误和系统错误等 
	 */
	ERROR(HttpStatusEnum.BAD_REQUEST.getCode()),
	
	/* 
	 * 注意：
	 * 1.状态码在[401,599]区间内的"失败"枚举项直接使用{@link org.sniper.commons.enums.http.HttpStatusEnum}
	 *   的等值枚举项，不再在{@link org.sniper.commons.enums.status.ResponseStatusEnum}中定义；
	 * 2.状态码在[600,999]区间内的"错误"枚举项预留给后续版本扩展。
	 */
	
	// 定义状态码在1000以上的"错误"枚举项
	
	
	
	/** 参数缺失 */
	PARAM_MISSING(1000),
	/** 参数必填 */
	PARAM_NEEDED(1001),
	/** 参数不合法 */
	PARAM_ILLEGAL(1002),
	/** 参数类型不匹配 */
	PARAM_TYPE_MISMATCH(1003),
	/** 参数格式错误 */
	PARAM_PATTERN_ERROR(1004),
	/** 参数校验错误 */
	PARAM_VALIDATE_ERROR(1005)
	;
		
	private static final String MESSAGE_PREFIX = "response.status.";
	private static final Map<Integer, ResponseStatusEnum> KEY_MAPPINGS = MapUtils.newHashMap(3);
	private static final Map<String, ResponseStatusEnum> NAME_MAPPINGS = MapUtils.newHashMap(3);
	
	static {
		for (ResponseStatusEnum status : values()) {
			KEY_MAPPINGS.put(status.code, status);
			NAME_MAPPINGS.put(status.name(), status);
		}
	}
	
	/** 状态码 */
	private final int code;
		
	/** 消息 */
	private final String message;

	private ResponseStatusEnum(int code) {
		this.code = code;
		this.message = MessageUtils.getClassMessage(getClass(), MESSAGE_PREFIX + code);
	}
	
	/**
	 * 获取状态码
	 * @author Daniele
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 获取枚举信息
	 * @author Daniele
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 获取格式化处理后的枚举消息
	 * @author Daniele
	 * @param params
	 * @return
	 */
	public String getMessage(Object... params) {
		return MessageFormat.format(message, params);
	}
		
	/**
	 * 判断指定的状态码是否匹配当前枚举对象
	 * @author Daniele
	 * @param code
	 * @return
	 */
	public boolean matches(int code) {
		return this.code == code;
	}

	/**
	 * 判断指定的名称是否匹配当前枚举对象
	 * @author Daniele
	 * @param name
	 * @return
	 */
	public boolean matches(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static ResponseStatusEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static ResponseStatusEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
				
}
