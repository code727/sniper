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
 * Create Date : 2021年7月4日
 */

package org.sniper.commons.enums.status;

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;

/**
 * 删除状态
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum DeleteStatus {
	
	/** 未删除 */
	UNDELETE("delete.status.undelete"),
	/** 已删除 */
	DELETED("delete.status.deleted");
	
	private static final Map<Integer, DeleteStatus> mappings = MapUtils.newHashMap(2);
	
	static {
		for (DeleteStatus status : values()) {
			mappings.put(status.ordinal(), status);
		}
	}
	
	/** 键 */
	private final int key;
	
	/** 值 */
	private final String value;

	private DeleteStatus(String value) {
		this.key = ordinal();
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getMessage() {
		return MessageUtils.getClassMessage(this.getClass(), this.value);
	}
	
	/**
	 * 判断指定的键是否匹配一个EntityStatus对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 将指定的键解析成EntityStatus对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static DeleteStatus resolve(int key) {
		return mappings.get(key);
	}

}
