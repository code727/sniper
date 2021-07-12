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
 * Create Date : 2017-4-21
 */

package org.sniper.support.interval;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.NumberUtils;

/**
 * 日期区间
 * @author  Daniele
 * @version 1.0
 */
public class DateInterval extends AbstractInterval<Date> {
	
	/** 日期格式 */
	private String dateFormat;
	
	/** calendar域 */
	private int calendarField = Calendar.DAY_OF_YEAR;
	
	public DateInterval() {
		super();
	}
	
	public DateInterval(Date minimal, Date maximum) {
		super(minimal, maximum);
	}
	
	public DateInterval(Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
		super(minimal, maximum, leftClose, rightClose);
	}
	
	@Override
	protected void init(Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
		if (minimal.getTime() == maximum.getTime() && !isClose()) {
			/* 如果最小和最大值相等，则要求区间只能是一个全闭合的单元素集，例如:[2015-01-01,2015-01-01]，
			 * 而类似于(2015-01-01,2015-01-01],[2015-01-01,2015-01-01),(2015-01-01,2015-01-01)这样半开或全开区间将抛出如下异常 */
			throw new IllegalArgumentException("Minimal must not be equals maximum when current is not a close interval:"
					+ toString(minimal, maximum, leftClose, rightClose));
		}
					
		if (minimal.after(maximum))	{
			/* 最小值比最大值大，则相互交换 */
			this.minimal = maximum;
			this.maximum = minimal;
		} else {
			this.minimal = minimal;
			this.maximum = maximum;
		}
	}
	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public int getCalendarField() {
		return calendarField;
	}

	public void setCalendarField(int calendarField) {
		this.calendarField = calendarField;
	}
	
	@Override
	public boolean contains(Object value) {
		if (value == null)
			return isMinusInfinity() || isPositiveInfinity();
		
		Date date = DateUtils.objectToDate(value, dateFormat);
		if (date == null)
			return false;
		
		if (isMinusInfinity()) 
			/* 如果当前区间为正负无穷大，则表示它能包含任意数字，返回true
			 * 否则在负无穷区间内判断当前数字与最大值的关系*/
			return isPositiveInfinity() ? true : (date.before(maximum) || date.equals(maximum) && isRightClose());
		
		if (isPositiveInfinity())
			// 如果只是正无穷大，则在正无穷区间内判断当前数字与最小值的关系*/
			return date.after(minimal) || date.equals(minimal) && isLeftClose();
		
		return date.after(minimal) && date.before(maximum) || (date.equals(minimal) && isLeftClose())
				|| (date.equals(maximum) && isRightClose());
	}
	
	@Override
	public boolean contains(Interval<Date> interval) {
		if (interval == null)
			return isMinusInfinity() || isPositiveInfinity();
		
		if (interval == this)
			return true;
		
		if (isMinusInfinity()) {
			// 如果当前区间为正负无穷大，则表示它能包含任意区间，直接返回true
			if (isPositiveInfinity()) 
				return true;
				
			// 比较最大值与当前负无穷区间最大值的关系
			return interval.getMaximum().before(maximum) || interval.getMaximum().equals(maximum) && isRightClose();
		}
		
		if (isPositiveInfinity())
			// 比较最小值与当前正无穷区间最小值的关系
			return interval.getMinimal().after(minimal) || interval.getMinimal().equals(minimal) && isLeftClose();
				
		// 指定区间在当前区间的最小或最大值范围外时返回false 
		if (interval.getMinimal().before(minimal) || interval.getMaximum().after(maximum))
			return false;
		
		// 指定区间在当前的最小或最大值范围内时返回true 
		if (interval.getMinimal().after(minimal) && interval.getMaximum().before(maximum))
			return true;
		
		/* 剩余情况：
		 * 1.最小值相等时，当前区间不是左闭合而指定的区间为左闭合时返回false，
		 *   例如当前区间(2015-01-01,2015-01-07]接受2015-01-02至2015-01-07的日期，而指定区间[2015-01-01,2015-01-07]可以包含2015-01-01，因此它不完全包含在当前区间内 
		 * 2.最大值相等时，当前区间不是右闭合而指定的区间为右闭合时返回false，
		 *   例如当前区间[2015-01-01,2015-01-07)接受2015-01-01至2015-01-06的日期，而指定区间[2015-01-01,2015-01-07]可以包含2015-01-07，因此它不完全包含在当前区间内 */
		return !isLeftClose() && interval.isLeftClose() || !isRightClose() && interval.isRightClose() ? false : true;
	}
		
	@Override
	public Interval<Date> offset(Object offset) {
		BigDecimal decimal = NumberUtils.toBigDecimal(offset);
		
		if (!NumberUtils.equals(decimal, 0)) {
			if (!this.isMinusInfinity())
				this.minimal = DateUtils.add(this.minimal, this.calendarField, decimal.intValue());
			
			if (!this.isPositiveInfinity())
				this.maximum = DateUtils.add(this.maximum, this.calendarField, decimal.intValue());
		}
		
		return this;
	}
		
	@Override
	public Interval<Date> offset(Interval<Number> interval) {
		if (interval != null) {
			
			// 当前区间不为负无穷时参与计算
			if (!isMinusInfinity()) {
				this.leftClose = interval.isLeftClose();
				/* 偏移区间为负无穷时，则当前区间也为负无穷，即最小值为null。
				 * 否则最小值为calendarField范围内将最小date增加偏移区间minimal个偏移 */
				if (interval.isMinusInfinity())
					this.minimal = null;
				else
					this.minimal = DateUtils.add(this.minimal, calendarField, interval.getMinimal().intValue());
			}
			
			// 当前区间不为正无穷时参与计算
			if (!isPositiveInfinity()) {
				this.rightClose = interval.isRightClose();
				
				/* 偏移区间为正无穷时，则当前区间也为正无穷，即最大值为null。
				 * 否则最大值为calendarField范围内将最大date增加偏移区间maximum个偏移 */
				if (interval.isPositiveInfinity())
					this.maximum = null;
				else
					this.maximum = DateUtils.add(this.maximum, calendarField, interval.getMaximum().intValue());
			}
		}
		
		return this;
	}
		
	/**
	 * 创建一个左开区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newLeftOpen(Date minimal, Date maximum) {
		return new DateInterval(minimal, maximum, false, true);
	}
		
	/**
	 * 创建一个右开区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newRightOpen(Date minimal, Date maximum) {
		return new DateInterval(minimal, maximum, true, false);
	}
		
	/**
	 * 创建一个开区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newOpen(Date minimal, Date maximum) {
		return new DateInterval(minimal, maximum, false, false);
	}
		
	/**
	 * 创建一个左闭合区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newLeftClose(Date minimal, Date maximum) {
		// 相当于创建一个右开区间
		return newRightOpen(minimal, maximum);
	}
		
	/**
	 * 创建一个右闭合区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newRightClose(Date minimal, Date maximum) {
		// 相当于创建一个左开区间
		return newLeftOpen(minimal, maximum);
	}
		
	/**
	 * 创建一个闭合区间
	 * @author Daniele 
	 * @param minimal
	 * @param maximum
	 * @return
	 */
	public static DateInterval newClose(Date minimal, Date maximum) {
		return new DateInterval(minimal, maximum, true, true);
	}
	
	@Override
	protected String toString(Date minimal, Date maximum, boolean leftClose, boolean rightClose) {
		return new StringBuilder().append(leftClose ? "[" : "(")
				.append(minimal != null ? DateUtils.dateToString(minimal, dateFormat) : "-∞").append(",")
				.append(maximum != null ? DateUtils.dateToString(maximum, dateFormat) : "+∞")
				.append(rightClose ? "]" : ")").toString();
	}
			
}
