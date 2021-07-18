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
 * Create Date : 2021-7-19
 */

package org.sniper.commons.enums.logic;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 排序模式枚举
 * @author  Daniele
 * @version 1.0
 */
public enum OrderEnum implements Enumerable<Integer> {
	
	ASC("order.mode.asc", 1),
	
	DESC("order.desc.asc", -1)
	;
	
	private static final Map<Integer, OrderEnum> KEY_MAPPINGS = MapUtils.newHashMap(2);
	
	private static final Map<String, OrderEnum> NAME_MAPPINGS = MapUtils.newHashMap(2);
	
	static {
		for (OrderEnum order: values()) {
			KEY_MAPPINGS.put(order.key, order);
			NAME_MAPPINGS.put(order.name(), order);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  
	
	/** SQL排序模式 */
	private final String sqlMode;
	
	/** MongoDB排序模式 */
	private final int mongoMode;
	
	private OrderEnum(String message, int mongoMode) {
		this.key = ordinal();
		this.message = MessageUtils.getClassMessage(getClass(), message);
		this.sqlMode = name();
		this.mongoMode = mongoMode;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public String getSqlMode() {
		return sqlMode;
	}

	public int getMongoMode() {
		return mongoMode;
	}

	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	@Override
	public boolean matches(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	public static OrderEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static OrderEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
	
}
