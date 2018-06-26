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

package org.sniper.commons.counter;

/**
 * 区间计数器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface IntervalCounter<V> extends Counter<V> {
	
	/**
	 * 获取区间步长
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param stepLength
	 * @return
	 */
	public long getStepLength();
		
	/**
	 * 获取区间内的最小值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public V getMinimal();
	
	/**
	 * 获取区间内的最大值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public V getMaximum();
	
	/**
	 * 判断当前计数是否大于区间内的最大值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean greaterThanMaximum();
	
	/**
	 * 判断当前计数是否大于等于区间内的最大值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean greaterThanEqualsMaximum();
	
	/**
	 * 判断当前计数是否小于区间内的最小值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean lessThanMinimal();
	
	/**
	 * 判断当前计数是否小于等于区间内的最小值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean lessThanEqualsMinimal();

}
