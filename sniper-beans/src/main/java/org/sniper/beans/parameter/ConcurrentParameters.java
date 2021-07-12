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

package org.sniper.beans.parameter;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * 并发参数实现类
 * @author  Daniele
 * @version 1.0
 */
public class ConcurrentParameters<K, V> extends DefaultParameters<K, V> {
	
	public ConcurrentParameters() {
		super();
	}
	
	public ConcurrentParameters(Parameters<K, V> parameters) {
		super(parameters);
	}
	
	public ConcurrentParameters(Map<K, V> parameterItems) {
		super(parameterItems);
	}
	
	/**
	 * 重写父类方法，强制将参数项存储到ConcurrentHashMap中
	 * @author Daniele 
	 * @param parameterItems
	 */
	@Override
	public void setItems(Map<K, V> items) {
		this.items = MapUtils.newConcurrentHashMap(items);
	}
						
}
