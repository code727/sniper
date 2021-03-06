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

import java.util.concurrent.atomic.AtomicLong;

import org.sniper.commons.util.AssertUtils;

/**
 * 原子长整型区间计数器实现类
 * @author  Daniele
 * @version 1.0
 */
public class AtomicLongIntervalCounter extends AbstractIntervalCounter<Long> {
	
	/** 原子值，所有的加减操作都在此值的基础上进行 */
	private AtomicLong atomicValue;
	
	public AtomicLongIntervalCounter() {
		this(Long.MAX_VALUE);
	}

	public AtomicLongIntervalCounter(long stepSize) {
		this(0L, stepSize);
	}
	
	public AtomicLongIntervalCounter(Long start, long stepSize) {
		super(start, stepSize);
		allocate();
	}
	
	@Override
	public void setStart(Long start) {
		super.setStart(start);
		allocate();
	}
	
	@Override
	public void setStepSize(long stepSize) {
		super.setStepSize(stepSize);
		adjustRange();
	}
	
	/**
	 * 区间分配，当起始值发生变更时，需要调用此方法重置当前值并且重新计算区间范围
	 * @author Daniele
	 */
	private void allocate() {
		if (this.atomicValue == null || this.atomicValue.get() != this.start) {
			// 重置当前值为起始值
			this.atomicValue = new AtomicLong(this.start);
		}
		adjustRange();
	}
	
	/**
	 * 调整区间范围，当起始值或区间步长发生变更时，需要调用此方法重新计算区间范围
	 * @author Daniele
	 */
	private void adjustRange() {
		/* 以Start元素作为中间点，计算出有效区间的最小值和最大值 */
		this.minimal = this.start - this.stepSize;
		this.maximum = this.start + this.stepSize;
		
		long currentValue = this.atomicValue.get();
		// 断言区间调整后，计数器的当前值应该还在此区间内，否则调整失败(区间步长由大变小时有可能失败)
		AssertUtils.assertTrue(currentValue >= minimal && currentValue <= maximum, String.format(
				"Counter interval range adjustment failed, currentValue '[%d]' outside interval range[{%d},{%d}]", 
				currentValue, minimal, maximum));
	}
					
	@Override
	public Long increment() {
		long next = get() + 1;
		if (next > maximum)
			return null;
		
		return this.atomicValue.incrementAndGet();
	}
	
	@Override
	public Long increment(Long value) {
		long next = get() + value;
		if (next > maximum || next < minimal)
			return null;
		
		return this.atomicValue.addAndGet(value);
	}
	
	@Override
	public Long decrement() {
		long previous = get() - 1;
		if (previous < minimal)
			return null;
		
		return this.atomicValue.decrementAndGet();
	}

	@Override
	public Long decrement(Long value) {
		long previous = get() - value;
		if (previous < minimal || previous > maximum)
			return null;
		
		return this.atomicValue.addAndGet(-value);
	}
	
	@Override
	public Long get() {
		return this.atomicValue.get();
	}
		
	@Override
	public String toString() {
		return String.format("{\"start\":%s,\"stepSize\":%s,\"currentValue\":%s,\"minimal\":%s,\"maximum\":%s}", 
				start, stepSize, atomicValue, minimal, maximum);
	}

	@Override
	public long incrementSize() {
		return Math.abs(getMaximum() - get());
	}

	@Override
	public long decrementSize() {
		return Math.abs(get() - getMinimal());
	}
			
}
