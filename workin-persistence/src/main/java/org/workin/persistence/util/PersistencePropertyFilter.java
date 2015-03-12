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

package org.workin.persistence.util;

/**
 * @description 持久化属性过滤器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface PersistencePropertyFilter {
	
	/**
	 * @description 获取属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getName();
	
	/**
	 * @description 设置属性名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name 属性名
	 */
	public void setName(String name);
	
	/**
	 * @description 获取属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Object getValue();
	
	/**
	 * @description 设置属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value 属性值
	 */
	public void setValue(Object value);
	
	/**
	 * @description 获取当前属性与值之间的运算符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Operator getOperator();
	
	/** 
	 * @description 设置当前属性与值之间的运算符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param operator 运算符字符串
	 */
	public void setOperator(String operator);
	
	/**
	 * @description 设置当前属性与值之间的运算符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param operator 运算符枚举
	 */
	public void setOperator(Operator operator);
	
	/**
	 * @description 获取当前属性与下一个属性间的关系谓词
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Predicate getPredicate();
	
	/**
	 * @description 设置当前属性与下一个属性间的关系谓词
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param predicate 谓词枚举
	 */
	public void setPredicate(Predicate predicate);
	
	/** 
	 * @description 设置当前属性与下一个属性间的关系谓词
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param predicate 谓词字符串
	 */
	public void setPredicate(String predicate);
	
	/**
	 * @description 转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String toString();

}
