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

import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.support.constant.AbstractConstant;

/**
 * 运算符常量
 * @author  Daniele
 * @version 1.0
 */
public final class Operator extends AbstractConstant<String, String> {
	
	private static final long serialVersionUID = 7578105868961133349L;

	/** 存放所有运算符常量的映射集 */
	private static final Map<String, Operator> mappings;
	
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
		
	static {
		mappings = MapUtils.newHashMap(createMapping(Operator.class));
	}
		
	private Operator(String key, String value) {
		super(key, value);
	}
		
	@Override
	public boolean matches(String key) {
		return this.key.equalsIgnoreCase(key);
	}
	
	/**
	 * 根据键解析出一个运算符
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static Operator resolve(String key) {
		return (key != null ? mappings.get(key.toUpperCase()) : null);
	}
						
}