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
 * Create Date : 2018-6-26
 */

package org.sniper.support.timer;

import java.util.concurrent.TimeUnit;

/**
 * 可过期的对象
 * @author  Daniele
 * @version 1.0
 */
public class ExpirableBean extends AbstractTimeMeasurement {
	
	/** 过期时间计数 */
	protected long expireTime;
	
	public ExpirableBean() {
		this(0);
	}
	
	public ExpirableBean(long expireTime) {
		this(expireTime, null);
	}
		
	public ExpirableBean(long expireTime, TimeUnit timeUnit) {
		super(timeUnit);
		this.expireTime = expireTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public long toNanos() {
		return timeUnit.toNanos(expireTime);
	}

	@Override
	public long toMicros() {
		return timeUnit.toMicros(expireTime);
	}

	@Override
	public long toMillis() {
		return timeUnit.toMillis(expireTime);
	}

	@Override
	public long toSeconds() {
		return timeUnit.toSeconds(expireTime);
	}

	@Override
	public long toMinutes() {
		return timeUnit.toMinutes(expireTime);
	}

	@Override
	public long toHours() {
		return timeUnit.toHours(expireTime);
	}

	@Override
	public long toDays() {
		return timeUnit.toDays(expireTime);
	}
			
}
