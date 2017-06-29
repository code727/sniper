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
 * Create Date : 2015-2-26
 */

package org.sniper.beans;

import java.util.Map;

/**
 * Bean反射器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface BeanReflector {
	
	/**
	 * 获取指定对象中表达式所指向的最后一个属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @return
	 */
	public String getterName(Object bean, String expression);
	
	/**
	 * 获取指定对象中表达式所指向的最后一个属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @return
	 */
	public String setterName(Object bean, String expression);
	
	/**
	 * 获取指定对象中表达式所指向的最后一个属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @param parameterType setter方法参数类型
	 * @return
	 */
	public String setterName(Object bean, String expression, Class<?> parameterType);
	
	/**
	 * 获取指定对象中表达式所指向的最后一个属性对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public Object get(Object bean, String expression) throws Exception;
	
	/**
	 * 设置指定对象中表达式所指向的最后一个属性对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @param parameterValue
	 */
	public void set(Object bean, String expression, Object parameterValue) throws Exception;
	
	/**
	 * 设置指定对象中表达式所指向的最后一个属性对应的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param expression
	 * @param parameterType
	 * @param parameterValue
	 */
	public void set(Object bean, String expression, Class<?> parameterType, Object parameterValue) throws Exception;
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例，
	 * 				并调用各表达式所指向的最后一个属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param expressionValues
	 * @return
	 * @throws Exception
	 */
	public <T, V> T create(String className, Map<String, V> expressionValues) throws Exception;
	
	/**
	 * 创建Bean实例，并调用各表达式所指向的最后一个属性对应的setter方法赋值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param expressionValues
	 * @return
	 * @throws Exception
	 */
	public <T, V> T create(Class<T> clazz, Map<String, V> expressionValues) throws Exception;
	
}
