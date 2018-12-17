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

package org.sniper.beans.reflector;

import java.util.Map;

/**
 * Bean反射器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface BeanReflector {
	
	/**
	 * 检索对象内指定属性对应的getter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @return
	 */
	public String findGetterName(Object obj, String propertyName);
	
	/**
	 * 检索对象内指定属性对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @return
	 */
	public String findSetterName(Object obj, String propertyName);
	
	/**
	 * 检索对象内指定属性和参数类型对应的setter方法名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj bean对象或class类型
	 * @param propertyName
	 * @param parameterType setter方法参数类型
	 * @return
	 */
	public String findSetterName(Object obj, String propertyName, Class<?> parameterType);
	
	/**
	 * 获取对象内指定的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public <V> V getPropertyValue(Object bean, String propertyName) throws Exception;
	
	/**
	 * 设置对象内指定的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @param parameterValue
	 */
	public void setPropertyValue(Object bean, String propertyName, Object parameterValue) throws Exception;
	
	/**
	 * 设置对象内指定的属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param bean
	 * @param propertyName
	 * @param parameterType
	 * @param parameterValue
	 */
	public void setPropertyValue(Object bean, String propertyName, Class<?> parameterType, Object parameterValue) throws Exception;
	
	/**
	 * 调用全限定名对应类的构造函数创建Bean实例，并在创建时设置Bean实例的各个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param className
	 * @param properties 
	 * @return
	 * @throws Exception
	 */
	public <T, V> T create(String className, Map<String, V> properties) throws Exception;
	
	/**
	 * 创建Bean实例，并在创建时设置Bean实例的各个属性值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public <T, V> T create(Class<T> clazz, Map<String, V> properties) throws Exception;
	
}
