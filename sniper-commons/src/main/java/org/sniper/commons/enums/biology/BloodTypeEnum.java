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
 * Create Date : 2021-7-6
 */

package org.sniper.commons.enums.biology;

import java.util.Map;

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.MapUtils;

/**
 * 血型枚举
 * @author  Daniele
 * @version 1.0
 */
public enum BloodTypeEnum implements Enumerable<Integer> {
	
	/** A型 */
	A("blood.type.a"),
	/** B型 */
	B("blood.type.b"),
	/** O型 */
	O("blood.type.o"),
	/** AB型 */
	AB("blood.type.ab"),
	/** Rh型 */
	RH("blood.type.rh"),
	/** 预留，其它（包括MN型、Ss型和Qg型等） */
	OTHER("blood.type.other");
	
	private static final Map<Integer, BloodTypeEnum> KEY_MAPPINGS = MapUtils.newHashMap(6);
	
	private static final Map<String, BloodTypeEnum> NAME_MAPPINGS = MapUtils.newHashMap(6);
	
	static {
		for (BloodTypeEnum bloodType: values()) {
			KEY_MAPPINGS.put(bloodType.key, bloodType);
			NAME_MAPPINGS.put(bloodType.name(), bloodType);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 消息 */
	private final String message;  
	
	private BloodTypeEnum(String message) {
		this.key = ordinal();
		this.message = message;
	}
	
	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean match(Integer key) {
		return key != null && this.key == key.intValue();
	}
	
	@Override
	public boolean match(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	public static BloodTypeEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static BloodTypeEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
}
