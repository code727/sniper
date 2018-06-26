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

package org.sniper.commons.timer;

import java.util.concurrent.TimeUnit;

import org.sniper.commons.util.AssertUtils;

/**
 * 默认的过期计时器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultExpiredTimer implements ExpiredTimer, TimeConverter {
	
	/** 过期时间计数，其值小于等于0时表示永不过期 */
	protected long expireTime;
	
	/** 过期时间单位 */
	protected TimeUnit timeUnit;
	
	public DefaultExpiredTimer() {
		this(0);
	}
	
	public DefaultExpiredTimer(long expireTime) {
		this(expireTime, TimeUnit.SECONDS);
	}
		
	public DefaultExpiredTimer(long expireTime, TimeUnit timeUnit) {
		AssertUtils.assertNotNull(timeUnit, "Expire time unit must not be null");
		this.expireTime = expireTime;
		this.timeUnit = timeUnit;
	}

	@Override
	public long getExpireTime() {
		return expireTime;
	}

	@Override
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	@Override
	public void setTimeUnit(TimeUnit timeUnit) {
		AssertUtils.assertNotNull(timeUnit, "Expire time unit must not be null");
		this.timeUnit = timeUnit;
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
