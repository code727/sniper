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

/**
 * 过期时间对象
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ExpirationTime extends AbstractTimeBean {
	
	/** 过期时间计数 */
	protected long expireTime;
	
	public ExpirationTime() {
		this(0);
	}
	
	public ExpirationTime(long expireTime) {
		this(expireTime, null);
	}
		
	public ExpirationTime(long expireTime, TimeUnit timeUnit) {
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
