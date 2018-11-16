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

package org.sniper.support.operation.logic;


import java.util.Map;

import org.sniper.commons.enums.AbstractEnum;
import org.sniper.commons.util.MapUtils;

/**
 * 逻辑运算枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class LogicOperationEnum extends AbstractEnum<String, LogicOperation<Object, Object>> {
	
	private static final long serialVersionUID = 7115101650824301748L;

	/** 逻辑操作组 */
	private static final Map<String, LogicOperationEnum> LOGIC_OPERATIONS;
	
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
		LOGIC_OPERATIONS = MapUtils.newHashMap(6);
		LOGIC_OPERATIONS.put(REFERENCE_EQUALS.getKey(), REFERENCE_EQUALS);
		LOGIC_OPERATIONS.put(EQUALS.getKey(), EQUALS);
		LOGIC_OPERATIONS.put(GREATER_THAN.getKey(), GREATER_THAN);
		LOGIC_OPERATIONS.put(GREATER_THAN_EQUALS.getKey(), GREATER_THAN_EQUALS);
		LOGIC_OPERATIONS.put(LESS_THAN.getKey(), LESS_THAN);
		LOGIC_OPERATIONS.put(LESS_THAN_EQUALS.getKey(), LESS_THAN_EQUALS);
	}
	
	private LogicOperationEnum(String key, LogicOperation<Object, Object> operation) {
		super(key, operation);
	}	
		
	/**
	 * 将指定的操作解析成LogicOperationEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param operation
	 * @return
	 */
	public static LogicOperationEnum resolve(String operation) {
		return operation != null ? LOGIC_OPERATIONS.get(operation.trim().toLowerCase()) : null;
	}
	
	/**
	 * 将指定的操作解析成LogicOperation对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param operation
	 * @return
	 */
	public static LogicOperation<Object, Object> resolveOperation(String operation) {
		if (operation == null)
			return null;
		
		LogicOperationEnum logicOperationEnum = LOGIC_OPERATIONS.get(operation.trim().toLowerCase());
		return logicOperationEnum != null ? logicOperationEnum.getValue() : null;
	}
	
	/**
	 * 判断指定的操作是否匹配一个LogicOperationEnum对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @return
	 */
	public static boolean matches(String operation) {
		return resolve(operation) != null;
	}
	
}
