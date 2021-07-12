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

import org.sniper.commons.util.MapUtils;

/**
 * 逻辑运算枚举
 * @author  Daniele
 * @version 1.0
 */
public enum LogicOperationEnum {
	
	/** 引用相等操作 */
	REFERENCE_EQUALS("==", new ReferenceEqualsLogicOperation()),
	
	/** 值相等操作 */
	EQUALS("eq", new EqualsLogicOperation()),
			
	/** 大于操作 */
	GREATER_THAN(">", new GreaterThanLogicOperation()),
			
	/** 大于等于操作 */
	GREATER_THAN_EQUALS(">=", new GreaterThanEqualsLogicOperation()),
			
	/** 小于操作 */
	LESS_THAN("<", new LessThanLogicOperation()),
			
	/** 小于等于操作 */
	LESS_THAN_EQUALS("<=", new GreaterThanEqualsLogicOperation());
	
	private static final Map<String, LogicOperationEnum> mappings = MapUtils.newHashMap(6);
	
	static {
		for (LogicOperationEnum logicOperation : values()) {
			mappings.put(logicOperation.sign, logicOperation);
		}
	}
			
	/** 运算符号 */
	private final String sign;
	
	/** 逻辑运算接口 */
	private final LogicOperation<Object, Object> operation;
	
	private LogicOperationEnum(String sign, LogicOperation<Object, Object> operation) {
		this.sign = sign;
		this.operation = operation;
	}	
	
	public String getSign() {
		return sign;
	}

	public LogicOperation<Object, Object> getOperation() {
		return operation;
	}
	
	/**
	 * 判断指定的运算符号是否匹配一个LogicOperationEnum对象
	 * @author Daniele 
	 * @param sign
	 * @return
	 */
	public boolean matches(String sign) {
		return sign != null && this.sign.equalsIgnoreCase(sign.trim());
	}

	/**
	 * 将指定的运算符号解析成LogicOperationEnum对象
	 * @author Daniele 
	 * @param sign
	 * @return
	 */
	public static LogicOperationEnum resolve(String sign) {
		return sign != null ? mappings.get(sign.trim().toLowerCase()) : null;
	}
			
}
