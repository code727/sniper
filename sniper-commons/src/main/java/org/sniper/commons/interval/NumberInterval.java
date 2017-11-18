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
 * Create Date : 2017-4-19
 */

package org.sniper.commons.interval;

import java.math.BigDecimal;

import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.RegexUtils;

/**
 * 数字区间
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NumberInterval extends AbstractInterval<Number> {
	
	public NumberInterval() {
		super();
	}
	
	public NumberInterval(Number minimal, Number maximum) {
		super(minimal, maximum);
	}
	
	public NumberInterval(Number minimal, Number maximum, boolean leftClose, boolean rightClose) {
		super(minimal, maximum, leftClose, rightClose);
	}
	
	@Override
	protected void init(Number minimal, Number maximum, boolean leftClose, boolean rightClose) {
		if (NumberUtils.equals(minimal, maximum) && !isClose()) {
			/* 如果最小和最大值相等，则要求区间只能是一个全闭合的单元素集，例如:[10,10]，
			 * 而类似于(10,10],[10,10),(10,10)这样半开或全开区间将抛出如下异常 */
			throw new IllegalArgumentException("Minimal must not be equals maximum when current is not a close interval:" 
					+ toString(minimal, maximum, leftClose, rightClose));
		}
		
		if (NumberUtils.greaterThan(minimal, maximum)) {
			/* 最小值比最大值大，则相互交换 */
			this.minimal = maximum;
			this.maximum = minimal;
		} else {
			this.minimal = minimal;
			this.maximum = maximum;
		}
	}
	
	/**
	 * 判断指定数字是否包含在当前区间内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public boolean contains(Object value) {
		if (value == null)
			return isMinusInfinity() || isPositiveInfinity();
		
		if (!(value instanceof Number) && !RegexUtils.isNumber(value.toString()))
			return false;
		
		BigDecimal valueDecimal;
		if (value instanceof BigDecimal) 
			valueDecimal = (BigDecimal) value;
		else
			valueDecimal = new BigDecimal(value.toString());
		
		if (isMinusInfinity()) 
			/* 如果当前区间为正负无穷大，则表示它能包含任意数字，返回true
			 * 否则在负无穷区间内判断当前数字与最大值的关系*/
			return isPositiveInfinity() ? true : (NumberUtils.lessThan(valueDecimal, maximum) 
					|| NumberUtils.equals(valueDecimal, maximum) && isRightClose());
		
		if (isPositiveInfinity())	
			// 如果只是正无穷大，则在正无穷区间内判断当前数字与最小值的关系*/
			return NumberUtils.greaterThan(valueDecimal, minimal)
					|| NumberUtils.equals(valueDecimal, minimal) && isLeftClose();
			
		return (NumberUtils.greaterThan(valueDecimal, minimal) && NumberUtils.lessThan(valueDecimal, maximum)
				|| (NumberUtils.equals(valueDecimal, minimal) && isLeftClose())
				|| (NumberUtils.equals(valueDecimal, maximum) && isRightClose()));
	}
		
	/**
	 * 判断指定区间是否完全包含在当前区间内
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param interval
	 * @return
	 */
	public boolean contains(Interval<Number> interval) {
		if (interval == null)
			return isMinusInfinity() || isPositiveInfinity();
		
		if (interval == this)
			return true;
		
		if (isMinusInfinity()) {
			// 如果当前区间为正负无穷大，则表示它能包含任意区间，直接返回true
			if (isPositiveInfinity()) 
				return true;
				
			// 比较最大值与当前负无穷区间最大值的关系
			return NumberUtils.lessThan(interval.getMaximum(), maximum)
					|| NumberUtils.equals(interval.getMaximum(), maximum) && isRightClose();
		}
		
		if (isPositiveInfinity())
			// 比较最小值与当前正无穷区间最小值的关系
			return NumberUtils.greaterThan(interval.getMinimal(), minimal)
					|| NumberUtils.equals(interval.getMinimal(), minimal) && isLeftClose();
		
		// 指定区间在当前区间的最小或最大值范围外时返回false 
		if (NumberUtils.lessThan(interval.getMinimal(), minimal)
				|| NumberUtils.greaterThan(interval.getMaximum(), maximum))
			return false;
		
		// 指定区间在当前的最小或最大值范围内时返回true 
		if (NumberUtils.greaterThan(interval.getMinimal(), minimal)
				&& NumberUtils.lessThan(interval.getMaximum(), maximum))
			return true;
		
		/* 剩余情况：
		 * 1.最小值相等时，当前区间不是左闭合而指定的区间为左闭合时返回false，
		 *   例如当前区间(0,9]接受1-9的整数，而指定区间[0,9]可以包含0，因此它不完全包含在当前区间内 
		 * 2.最大值相等时，当前区间不是右闭合而指定的区间为右闭合时返回false，
		 *   例如当前区间[0,9)接受0-8的整数，而指定区间[0,9]可以包含9，因此它不完全包含在当前区间内 */
		return !isLeftClose() && interval.isLeftClose() || !isRightClose() && interval.isRightClose() ? false : true;
	}
			
	@Override
	public Interval<Number> offset(Object offset) {		
		BigDecimal decimal = NumberUtils.toBigDecimal(offset);
		if (!NumberUtils.equals(decimal, 0)) {
			if (!this.isMinusInfinity())
				this.minimal = NumberUtils.add(this.minimal, decimal);
			
			if (!this.isPositiveInfinity())
				this.maximum = NumberUtils.add(this.maximum, decimal);
		}
		
		return this;
	}
	
	@Override
	public Interval<Number> offset(Interval<Number> interval) {
		if (interval != null) {
			this.leftClose = interval.isLeftClose();
			this.rightClose = interval.isRightClose();
			
			if (this.isMinusInfinity() || interval.isMinusInfinity()) 
				this.minimal = interval.getMinimal();
			else 
				this.minimal = NumberUtils.add(this.minimal, interval.getMinimal());
			
			if (this.isPositiveInfinity() || interval.isPositiveInfinity()) 
				this.maximum = interval.getMaximum();
			else
				this.maximum = NumberUtils.add(this.maximum, interval.getMaximum());
		}
		
		return this;
	}
			
	/**
	 * 创建一个左开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newLeftOpen(Number minimal, Number maximum) {
		return new NumberInterval(minimal, maximum, false, true);
	}
		
	/**
	 * 创建一个右开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newRightOpen(Number minimal, Number maximum) {
		return new NumberInterval(minimal, maximum, true, false);
	}
		
	/**
	 * 创建一个开区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newOpen(Number minimal, Number maximum) {
		return new NumberInterval(minimal, maximum, false, false);
	}
		
	/**
	 * 创建一个左闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newLeftClose(Number minimal, Number maximum) {
		// 相当于创建一个右开区间
		return newRightOpen(minimal, maximum);
	}
	
	/**
	 * 创建一个右闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newRightClose(Number minimal, Number maximum) {
		// 相当于创建一个左开区间
		return newLeftOpen(minimal, maximum);
	}
		
	/**
	 * 创建一个闭合区间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static NumberInterval newClose(Number minimal, Number maximum) {
		return new NumberInterval(minimal, maximum, true, true);
	}
		
}
