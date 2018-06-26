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
 * 过期计时器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface ExpiredTimer {
	
	/**
	 * 获取过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public long getExpireTime();
	
	/**
	 * 设置过期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param expireTime
	 */
	public void setExpireTime(long expireTime);
	
	/**
	 * 获取过期时间单位
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public TimeUnit getTimeUnit();
	
	/**
	 * 设置过期时间单位
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param timeUnit
	 */
	public void setTimeUnit(TimeUnit timeUnit);
	
}
