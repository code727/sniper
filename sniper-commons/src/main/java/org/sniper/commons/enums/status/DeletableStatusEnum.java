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
 * Create Date : 2021-7-4
 */

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 可删除的状态枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum DeletableStatusEnum {
	
	/** 未删除 */
	UNDELETE("deletable.status.undelete"),
	/** 已删除 */
	DELETED("deletable.status.deleted");
	
	private static final Map<Integer, DeletableStatusEnum> mappings = MapUtils.newHashMap(2);
	
	static {
		for (DeletableStatusEnum status : values()) {
			mappings.put(status.ordinal(), status);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;
	
	/** 消息 */
	private final String message;

	private DeletableStatusEnum(String value) {
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
	 * 判断指定的键是否匹配
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static DeletableStatusEnum resolve(int key) {
		return mappings.get(key);
	}
	
}
