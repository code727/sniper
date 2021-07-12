/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-12-18
 */

package org.sniper.beans.expression;

import org.sniper.commons.util.StringUtils;

/**
 * 属性处理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface PropertyHandler<T> {
	
	public static final String MAPPED_START = StringUtils.LEFT_PARENTHESES;
	public static final String MAPPED_END = StringUtils.RIGHT_PARENTHESES;
	
	public static final String INDEXED_START = StringUtils.LEFT_BRACKET;
	public static final String INDEXED_END   = StringUtils.RIGHT_BRACKET;
	
	/**
	 * 判断当前处理器是否支持对指定对象和属性的处理
	 * @author Daniele 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public boolean support(Object obj, String propertyName);
	
	/**
	 * 在指定对象中获取属性值
	 * @author Daniele 
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public <V> V getPropertyValue(T obj, String propertyName) throws Exception;
	
	/**
	 * 在指定对象中获取已构建的属性值
	 * @author Daniele 
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public <V> V getConstructedPropertyValue(T obj, String propertyName) throws Exception;
	
	/**
	 * 在指定对象中设置属性成员的值
	 * @author Daniele 
	 * @param obj
	 * @param propertyName
	 * @param propertyType
	 * @param propertyValue
	 * @throws Exception
	 */
	public void setPropertyValue(T obj, String propertyName, Class<?> propertyType, Object propertyValue) throws Exception;

}
