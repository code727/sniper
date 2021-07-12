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
 * 时间度量接口
 * @author  Daniele
 * @version 1.0
 */
public interface TimeMeasurement {
	
	/**
	 * 获取计时单位
	 * @author Daniele 
	 * @return
	 */
	public TimeUnit getTimeUnit();
	
	/**
	 * 设置计时单位
	 * @author Daniele 
	 * @param timeUnit
	 */
	public void setTimeUnit(TimeUnit timeUnit);
	
	/**
	 * 将当前时间计数转换成纳秒
	 * @author Daniele 
	 * @return
	 */
	public long toNanos();
	
	/**
	 * 将当前时间计数转换成微秒
	 * @author Daniele 
	 * @return
	 */
	public long toMicros();
	
	/**
	 * 将当前时间计数转换成毫秒
	 * @author Daniele 
	 * @return
	 */
	public long toMillis();
	
	/**
	 * 将当前时间计数转换成秒
	 * @author Daniele 
	 * @return
	 */
	public long toSeconds();
	
	/**
	 * 将当前时间计数转换成分钟
	 * @author Daniele 
	 * @return
	 */
	public long toMinutes();
	
	/**
	 * 将当前时间计数转换成小时
	 * @author Daniele 
	 * @return
	 */
	public long toHours();
	
	/**
	 * 将当前时间计数转换成天计数
	 * @author Daniele 
	 * @return
	 */
	public long toDays();

}
