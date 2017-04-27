/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-4-20
 */

package org.workin.commons.interval;

/**
 * 区间接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface Interval<T> {
	
	/**
	 * 设置区间名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * 获取区间名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getName();
	
	/**
	 * 获取区间最小值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public T getMinimal();
	
	/**
	 * 获取区间最大值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public T getMaximum();
	
	/**
	 * 是否为左闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isLeftClose();
	
	/**
	 * 是否为右闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isRightClose();
	
	/**
	 * 是否为全闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isClose();
	
	/**
	 * 是否为左开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isLeftOpen();
	
	/**
	 * 是否为右开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isRightOpen();
	
	/**
	 * 是否是否为全开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isOpen();
	
	/**
	 * 是否为负无穷大
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isMinusInfinity();
	
	/**
	 * 是否为正无穷大
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isPositiveInfinity();
	
	/**
	 * 判断指定的值是否包含在当前区间内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param number
	 * @return
	 */
	public boolean contains(Object value);
	
	/**
	 * 判断指定区间是否完全包含在当前区间内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param interval
	 * @return
	 */
	public boolean contains(Interval<T> interval);
	
	/**
	 * 将整个区间产生指定值的偏移
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param offset
	 */
	public void offset(Object offset);
	
//	/**
//	 * 
//	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
//	 * @param interval
//	 */
//	public void offset(Interval<T> interval);
	
}
