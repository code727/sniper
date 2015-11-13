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
 * Create Date : 2015-11-13
 */

package org.workin.support.parameter.adapter;

import java.util.List;
import java.util.Map;

import org.workin.support.parameter.Parameter;

/**
 * @description 可适配的参数配接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface AdaptableParameter<K, V> extends Parameter<K, V>, Adaptable<K> {
	
	/**
	 * @description 设置参数名适配器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param nameAdapter
	 */
	public void setNameAdapter(NameAdapter<K> nameAdapter);
	
	/**
	 * @description 获取参数名适配器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public NameAdapter<K> getNameAdapter();
	
	/**
	 * @description 获取适配参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param adaptationName
	 * @return
	 */
	public V getAdaptationValue(K adaptationName);
	
	/**
	 * @description 获取指定类型的适配参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param adaptationName
	 * @param clazz
	 * @return
	 */
	public <V1> V1 getAdaptationValue(K adaptationName, Class<V1> clazz);
	
	/**
	 * @description 获取所有适配参数映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<K, V> getAdaptationParameters();
	
	/**
	 * @description 获取所有适配参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<V> getAdaptationValues();
	
	/**
	 * @description 获取原始参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param originalName
	 * @return
	 */
	public V getOriginalValue(K originalName);
	
	/**
	 * @description 获取指定类型的原始参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param originalName
	 * @param clazz
	 * @return
	 */
	public <V1> V1 getOriginalValue(K originalName, Class<V1> clazz);
	
	/**
	 * @description 获取所有原始参数映射集
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Map<K, V> getOriginalParameters();
		
	/**
	 * @description 获取所有原始参数值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public List<V> getOriginalValues();
	
}
