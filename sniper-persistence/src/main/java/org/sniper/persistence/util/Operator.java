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
 * Create Date : 2015-2-12
 */

package org.sniper.persistence.util;

import java.util.Set;

import org.sniper.commons.enums.AbstractEnums;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 运算符枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Operator extends AbstractEnums<String, String> {
	
	/** 存放的所有运算枚举对象组 */
	protected static final Set<Operator> ENUM_GROUP = CollectionUtils.newLinkedHashSet();
	
	/** 相等运算符 */
	public static final Operator EQ = new Operator("EQ", " = ");
	
	/** 小于运算符 */
	public static final Operator LT = new Operator("LT", " < ");
	
	/** 小于等于运算符 */
	public static final Operator LE = new Operator("LE", " <= ");
	
	/** 大于运算符 */
	public static final Operator GT = new Operator("GT", " > ");
	
	/** 大于等于运算符 */
	public static final Operator GE = new Operator("GE", " >= ");
	
	/** LIKE运算符，完全匹配模式"LIKE %value% */
	public static final Operator LIKE = new Operator("LIKE", " LIKE %{value}%");
	
	/** LIKE运算符，左侧匹配模式"LIKE %value */
	public static final Operator LLIKE = new Operator("LLIKE", " LIKE %{value}");
	
	/** LIKE运算符，右侧匹配模式"LIKE value% */
	public static final Operator RLIKE = new Operator("RLIKE", " LIKE {value}%");
	
	/** 所有的运算符名 */
	public static final String ALL_OPERATOR_NAME;
	
	protected Operator(String name, String value) {
		super(name, value);
	}
	
	static {
		ENUM_GROUP.add(EQ);
		ENUM_GROUP.add(LT);
		ENUM_GROUP.add(LE);
		ENUM_GROUP.add(GT);
		ENUM_GROUP.add(GE);
		ENUM_GROUP.add(LIKE);
		ENUM_GROUP.add(LLIKE);
		ENUM_GROUP.add(RLIKE);
		
		StringBuilder builder = new StringBuilder();
		for (Operator operator : ENUM_GROUP) 
			builder.append(operator.getKey()).append(",");
				
		builder.deleteCharAt(builder.lastIndexOf(","));
		ALL_OPERATOR_NAME = builder.toString();
	}
	
	/**
	 * 获取存放的所有运算符枚举
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Set<Operator> getAll() {
		return ENUM_GROUP; 
	}
	
	/**
	 * 根据名称获取对应的枚举对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public static Operator get(String name) {
		name = StringUtils.trim(name);
		if (name != null)
			name = name.toUpperCase();
		
		for (Operator operator : ENUM_GROUP) {
			if (ObjectUtils.equals(operator.getKey(), name))
				return operator;
		}
		return null;
	}
	
	/**
	 * 判断名称对应的枚举对象是否存在
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public static boolean exist(String name) {
		return get(name) != null;
	}
		
}