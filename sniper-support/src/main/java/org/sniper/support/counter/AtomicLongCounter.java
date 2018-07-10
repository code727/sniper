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

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;

import org.sniper.commons.util.NumberUtils;

/**
 * 原子长整型计数器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AtomicLongCounter extends AbstractCounter<Long> {
	
	/** 原子值，所有的加减操作都在此进行 */
	private AtomicLong atomicValue;
	
	public AtomicLongCounter() {
		this(null);
	}
	
	public AtomicLongCounter(Long start) {
		super(start != null ? start : 0L);
		this.atomicValue = new AtomicLong(this.start);
	}
	
	@Override
	public Long increment() {
		return this.atomicValue.incrementAndGet();
	}

	@Override
	public Long increment(Long value) {
		return this.atomicValue.addAndGet(value);
	}

	@Override
	public Long decrement() {
		return this.atomicValue.decrementAndGet();
	}

	@Override
	public Long decrement(Long value) {
		return this.atomicValue.addAndGet(NumberUtils.unAbs(value));  
	}

	@Override
	public Long get() {
		return this.atomicValue.get();
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("[start={0},currentValue={1}]", start, atomicValue);
	}
		
}
