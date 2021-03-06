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
 * Create Date : 2018-5-14
 */

package org.sniper.support.counter;

import org.sniper.commons.util.AssertUtils;

/**
 * 区间计数器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractIntervalCounter<V> extends AbstractCounter<V> implements IntervalCounter<V> {
		
	/** 区间步长 */
	protected long stepSize;
	
	/** 当前区间内的最小值 */
	protected V minimal;
	
	/** 当前区间内的最大值 */
	protected V maximum;
	
	protected AbstractIntervalCounter(V start) {
		this(start, 1L);
	}
		
	protected AbstractIntervalCounter(V start, long stepSize) {
		super(start);
		checkStepSize(stepSize);
		this.stepSize = stepSize;
	}
	
	@Override
	public void setStepSize(long stepSize) {
		checkStepSize(stepSize);
		this.stepSize = stepSize;
	}
	
	public long getStepSize() {
		return stepSize;
	}
		
	@Override
	public V getMinimal() {
		return minimal;
	}

	@Override
	public V getMaximum() {
		return maximum;
	}
	
	/**
	 * 检查区间步长的合法性
	 * @author Daniele 
	 * @param stepSize
	 */
	private void checkStepSize(long stepSize) {
		AssertUtils.assertTrue(stepSize > 0, "Counter interval step size must greater than 0");
	}
	
}
