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
 * Create Date : 2015-1-26
 */

package org.sniper.context;

/**
 * 上下文接口
 * @author  Daniele
 * @version 1.0
 */
public interface Context extends ConfigurableContext {
	
	/**
	 * 获取上下文默认属性值
	 * @author Daniele 
	 * @return
	 */
	public <V> V getAttribute();
	
	/**
	 * 根据名称获取上下文属性值
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public <K, V> V getAttribute(K name);
	
	/**
	 * 设置上下文默认属性值
	 * @author Daniele 
	 * @param value
	 */
	public <V> void setAttribute(V value);
	
	/**
	 * 设置上下文属性值
	 * @author Daniele 
	 * @param name
	 * @param value
	 */
	public <K, V> void setAttribute(K name, V value);
	
	/**
	 * 删除上下文默认属性
	 * @author Daniele 
	 * @return
	 */
	public <V> V removeAttribute();
	
	/**
	 * 根据名称删除上下文属性
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public <K, V> V removeAttribute(K name);
	
	/**
	 * 清除上下文
	 * @author Daniele
	 */
	public void clear();

}
