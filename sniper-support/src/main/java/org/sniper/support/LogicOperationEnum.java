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
 * Create Date : 2015-1-20
 */

package org.sniper.support;


import java.util.Set;

import org.sniper.commons.enums.AbstractEnums;
import org.sniper.commons.enums.Enums;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 逻辑运算枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class LogicOperationEnum extends AbstractEnums<String, LogicOperation<Object, Object>> {
	
	/** 存放的所有枚举对象组 */
	protected static final Set<Enums<String,LogicOperation<Object, Object>>> ENUM_GROUP = CollectionUtils.newHashSet();
	
	/** 引用相等操作 */
	public static final LogicOperationEnum REFERENCE_EQUALS = new LogicOperationEnum(
			"==", new ReferenceEqualsLogicOperation());
	
	/** 值相等操作 */
	public static final LogicOperationEnum EQUALS = new LogicOperationEnum(
			"eq", new EqualsLogicOperation());
	
	/** 大于操作 */
	public static final LogicOperationEnum GREATER_THAN = new LogicOperationEnum(
			">", new GreaterThanLogicOperation());
	
	/** 大于等于操作 */
	public static final LogicOperationEnum GREATER_THAN_EQUALS = new LogicOperationEnum(
			">=", new GreaterThanEqualsLogicOperation());
	
	/** 小于操作 */
	public static final LogicOperationEnum LESS_THAN = new LogicOperationEnum(
			"<", new LessThanLogicOperation());
	
	/** 小于等于操作 */
	public static final LogicOperationEnum LESS_THAN_EQUALS = new LogicOperationEnum(
			"<=", new GreaterThanEqualsLogicOperation());
	
	static {
		ENUM_GROUP.add(REFERENCE_EQUALS);
		ENUM_GROUP.add(EQUALS);
		ENUM_GROUP.add(GREATER_THAN);
		ENUM_GROUP.add(GREATER_THAN_EQUALS);
		ENUM_GROUP.add(LESS_THAN);
		ENUM_GROUP.add(LESS_THAN_EQUALS);
	}
	
	private LogicOperationEnum(String key, LogicOperation<Object, Object> operation) {
		super(key, operation);
	}	
	
	/**
	 * 获取存放的所有逻辑运算枚举
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Set<Enums<String,LogicOperation<Object, Object>>> getAll() {
		return ENUM_GROUP; 
	}
	
	/**
	 * 根据键获取对应的枚举对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static Enums<String,LogicOperation<Object, Object>> get(String key) {
		key = StringUtils.trim(key);
		if (key != null)
			key = key.toLowerCase();
		
		for (Enums<String,LogicOperation<Object, Object>> operation : ENUM_GROUP) {
			if (ObjectUtils.equals(operation.getKey(), key))
				return operation;
		}
		return null;
	}
	
	/**
	 * 判断键对应的枚举对象是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static boolean exist(String key) {
		return get(key) != null;
	}
	
}
