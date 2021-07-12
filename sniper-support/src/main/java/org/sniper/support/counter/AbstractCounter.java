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
 * Create Date : 2018-5-17
 */

package org.sniper.support.counter;

import org.sniper.commons.util.AssertUtils;

/**
 * 计算器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractCounter<V> implements Counter<V> {
	
	/** 起始值 */
	protected V start;
	
	protected AbstractCounter(V start) {
		checkStart(start);
		this.start = start;
	}
	
	@Override
	public V getStart() {
		return start;
	}

	@Override
	public void setStart(V start) {
		checkStart(start);
		this.start = start;
	}
	
	/**
	 * 检查起始值的合法性
	 * @author Daniele 
	 * @param start
	 */
	private void checkStart(V start) {
		AssertUtils.assertNotNull(start, "Counter start value must not be null");
	}
	
}
