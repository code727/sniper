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

import org.workin.commons.util.StringUtils;

/**
 * 抽象区间
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractInterval<T> implements Interval<T> {
	
	/** 名称 */
	protected String name;
	
	/** 最小值 */
	protected T minimal;
	
	/** 最大值 */
	protected T maximum;
	
	/** 是否左闭合 */
	private boolean leftClose;
	
	/** 是否右闭合 */
	private boolean rightClose;
	
	/** 是否负无穷 */
	private boolean minusInfinity;
	
	/** 是否正无穷 */
	private boolean positiveInfinity;
	
	/**
	 * 构建正负无穷的开区间(-∞,+∞)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	protected AbstractInterval() {
		this(null);
	}
	
	/**
	 * 构建指定名称的正负无穷的开区间(-∞,+∞)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 */
	protected AbstractInterval(String name) {
		this(name, null, null);
	}
	
	/**
	 * 构建闭合区间[minimal,maximum]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 */
	protected AbstractInterval(T minimal, T maximum) {
		this(null, minimal, maximum);
	}
	
	/**
	 * 构建指定名称的闭合区间[minimal,maximum]
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param minimal
	 * @param maximum
	 */
	protected AbstractInterval(String name, T minimal, T maximum) {
		this(name, minimal, maximum, true, true);
	}
	
	/**
	 * 构建区间，在构建时设置左/右是否为闭合的
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @param leftClose
	 * @param rightClose
	 */
	protected AbstractInterval(T minimal, T maximum, boolean leftClose, boolean rightClose) {
		this(null, minimal, maximum, leftClose, rightClose);
	}
	
	/**
	 * 构建指定名称的区间，并在构建时设置左/右是否为闭合的
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param minimal
	 * @param maximum
	 * @param leftClose
	 * @param rightClose
	 */
	protected AbstractInterval(String name, T minimal, T maximum, boolean leftClose, boolean rightClose) {
		
		/* 最小值为空，表示区间为负无穷的
		 * 因此需要将左闭合区间标志强制设置为false,负无穷标志设置为true 
		 * 未来可能修改为当leftClose为true时抛异常*/
		if (minimal == null) {
			leftClose = false;
			minusInfinity = true;
		}
		
		/* 最小值为空，表示区间为正无穷的
		 * 因此需要将右闭合区间标志强制设置为false,正无穷标志为true 
		 * 未来可能修改为当rightClose为true时抛异常*/
		if (maximum == null) {
			rightClose = false;
			positiveInfinity = true;
		}
		
		this.name = name;
		this.leftClose = leftClose;
		this.rightClose = rightClose;
		
		/* 整个区间有一边是无穷的，说明最小值和最大值至少有一个为空，直接完成设置即可 
		 * 否则应该在自定义初始化方法中进行更为具体的设置 */
		if (minusInfinity || positiveInfinity) {
			this.minimal = minimal;
			this.maximum = maximum;
		} else 
			init(name, minimal, maximum, this.leftClose, this.rightClose);
	}
	
	/**
	 * 构造时执行的初始化方法
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @param leftClose
	 * @param rightClose
	 */
	protected abstract void init(String name, T minimal, T maximum, boolean leftClose, boolean rightClose);
		
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public T getMinimal() {
		return minimal;
	}

	@Override
	public T getMaximum() {
		return maximum;
	}

	@Override
	public boolean isLeftClose() {
		return leftClose;
	}

	@Override
	public boolean isRightClose() {
		return rightClose;
	}

	@Override
	public boolean isClose() {
		return isLeftClose() && isRightClose();
	}

	@Override
	public boolean isLeftOpen() {
		return !leftClose;
	}

	@Override
	public boolean isRightOpen() {
		return !rightClose;
	}

	@Override
	public boolean isOpen() {
		return isLeftOpen() && isRightOpen();
	}
	
	@Override
	public boolean isMinusInfinity() {
		return minimal == null || minusInfinity;
	}

	@Override
	public boolean isPositiveInfinity() {
		return maximum == null || positiveInfinity;
	}
	
	/**
	 * 区间表达式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @param minimal
	 * @param maximum
	 * @param leftClose
	 * @param rightClose
	 * @return
	 */
	protected String expression(String name, T minimal, T maximum, boolean leftClose, boolean rightClose) {
		return new StringBuilder(StringUtils.isNotBlank(name) ? name : "")
				.append(leftClose ? "[" : "(").append(minimal != null ? minimal : "-∞").append(",")
				.append(maximum != null ? maximum : "+∞").append(rightClose ? "]" : ")").toString();
	}
		
	@Override
	public String toString() {
		return expression(name, minimal, maximum, leftClose, rightClose);
	}

}
