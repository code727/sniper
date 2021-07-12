/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-11-20
 */

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 在线离线/线上线下状态枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum O2OStatus {
	
	/** 线下 */
	OFFLINE("o2o.status.offline"),
	/** 线上 */
	ONLINE("o2o.status.online");
	
	private static final Map<Integer, O2OStatus> mappings = MapUtils.newHashMap(2);
	
	static {
		for (O2OStatus status : values()) {
			mappings.put(status.ordinal(), status);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;
	
	/** 消息 */
	private final String message;

	private O2OStatus(String value) {
		this.key = ordinal();
		this.value = value;
		this.message = MessageUtils.getClassMessage(getClass(), value);
	}
	
	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的键是否匹配一个O2OStatus对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成O2OStatus对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static O2OStatus resolve(int key) {
		return mappings.get(key);
	}
	
	public static void main(String[] args) {
		System.out.println(O2OStatus.OFFLINE.getMessage());
	}
	
}
