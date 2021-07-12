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

/**
 * 区间计数器接口
 * @author  Daniele
 * @version 1.0
 */
public interface IntervalCounter<V> extends Counter<V> {
	
	/**
	 * 设置区间步长
	 * @author Daniele 
	 * @param stepSize
	 */
	public void setStepSize(long stepSize);
	
	/**
	 * 获取区间步长
	 * @author Daniele 
	 * @return
	 */
	public long getStepSize();
	
	/**
	 * 获取区间内的最小值
	 * @author Daniele 
	 * @return
	 */
	public V getMinimal();
	
	/**
	 * 获取区间内的最大值
	 * @author Daniele 
	 * @return
	 */
	public V getMaximum();
	
	/**
	 * 获取在有效区间内可进行累加的长度
	 * @author Daniele 
	 * @return
	 */
	public long incrementSize();
	
	/**
	 * 获取在有效区间内可进行累减的长度
	 * @author Daniele 
	 * @return
	 */
	public long decrementSize();
}
