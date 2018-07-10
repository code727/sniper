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

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;

import org.sniper.commons.util.NumberUtils;

/**
 * 原子长整型区间计数器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AtomicLongIntervalCounter extends AbstractIntervalCounter<Long> {
		
	/** 原子值，所有的加减操作都在此值的基础上进行 */
	private AtomicLong atomicValue;
	
	public AtomicLongIntervalCounter() {
		this(Long.MAX_VALUE);
	}

	public AtomicLongIntervalCounter(long stepLength) {
		this(null, stepLength);
	}
	
	public AtomicLongIntervalCounter(Long start, long stepLength) {
		super(start != null ? start : 0L, stepLength);
		allocateInterval();
	}
	
	@Override
	public void setStart(Long start) {
		super.setStart(start);
		allocateInterval();
	}
	
	/**
	 * 分配区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	private void allocateInterval() {
		this.atomicValue = new AtomicLong(this.start);
		/* 以Start元素作为中间点，计算出有效区间的最小值和最大值 */
		this.minimal = this.start - this.stepLength;
		this.maximum = this.start + this.stepLength;
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
		
		return this.atomicValue.addAndGet(NumberUtils.unAbs(value));
	}
	
	@Override
	public Long get() {
		return this.atomicValue.get();
	}
	
	@Override
	public boolean greaterThanMaximum() {
		return get() > maximum;
	}
	
	@Override
	public boolean greaterThanEqualsMaximum() {
		return get() >= maximum;
	}

	@Override
	public boolean lessThanMinimal() {
		return get() < minimal;
	}

	@Override
	public boolean lessThanEqualsMinimal() {
		return get() <= minimal;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("[start={0},stepLength={1},currentValue={2},minimal={3},maximum={4}]", 
				start, stepLength, atomicValue, minimal, maximum);
	}
		
}
