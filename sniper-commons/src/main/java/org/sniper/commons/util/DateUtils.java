/*
 * Copyright 2014 the original author or authors.
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
 * Create Date : 2014-9-28
 */

package org.sniper.commons.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.sniper.commons.enums.astrology.Horoscope;

/**
 * 日期时间工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0.0
 */
public class DateUtils {
	
	/** 默认的年格式 */
	public static final String DEFAULT_YEAR_FORMAT = "yyyy";
	
	/** 默认的日期格式 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/** 默认的日期时间格式 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/** 默认的日期时间(带毫秒)格式 */
	public static final String DEFAULT_DATETIME_PLUS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
	
	/** 格林尼治日期时间格式组 */
	public static final String[] GMT_DATETIME_FORMATS = new String[] {
			"EEE, dd MMM yyyy HH:mm:ss zzz",
			"EEE, dd-MMM-yy HH:mm:ss zzz",
			"EEE MMM dd HH:mm:ss yyyy"
	};
	
	/** 星期一字符串 */
	public static final String MONDAY = "Monday";
	
	/** 星期二字符串 */
	public static final String TUESDAY = "Tuesday";
	
	/** 星期三字符串 */
	public static final String WEDNESDAY = "Wednesday";
	
	/** 星期四字符串 */
	public static final String THURSDAY = "Thursday";
	
	/** 星期五字符串 */
	public static final String FRIDAY = "Friday";
	
	/** 星期六字符串 */
	public static final String SATURDAY = "Saturday";
	
	/** 星期日字符串 */
	public static final String SUNDAY = "Sunday";
		
	/** 全局键与日期时间格式关系映射集 */
	private static final Map<String, SimpleDateFormat> dateFormates = MapUtils.newConcurrentHashMap();
	
	/**
	 * 根据指定的模式获取日期格式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(String pattern) {
		if (StringUtils.isBlank(pattern))
			pattern = DEFAULT_DATETIME_FORMAT;
		
		SimpleDateFormat dateFormat = dateFormates.get(pattern);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern);
			dateFormates.put(pattern, dateFormat);
		}
		
		return dateFormat;
	}
	
	/**
	 * 获取工具默认支持的格林尼治日期时间格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static SimpleDateFormat getGMTDateFormat() {
		return getGMTDateFormat(0);
	}
	
	/**
	 * 根据索引获取工具所支持的格林尼治日期时间
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @return
	 */
	public static SimpleDateFormat getGMTDateFormat(int index) {
		return getGMTDateFormat(index, null);
	}
	
	/**
	 * 根据Locale对象获取工具默认支持的格林尼治日期时间格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param locale
	 * @return
	 */
	public static SimpleDateFormat getGMTDateFormat(Locale locale) {
		return getGMTDateFormat(0, locale);
	}
	
	/**
	 * 根据索引和Locale对象获取工具所支持的格林尼治日期时间格式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param index
	 * @param locale
	 * @return
	 */
	public static SimpleDateFormat getGMTDateFormat(int index, Locale locale) {
		if (locale == null)
			locale = Locale.US;
		
		String pattern = GMT_DATETIME_FORMATS[NumberUtils.rangeLimit(index, 0, GMT_DATETIME_FORMATS.length - 1)];
		String key = pattern + StringUtils.UNDER_LINE + locale;
		
		SimpleDateFormat dateFormat = dateFormates.get(key);
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(pattern, locale);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			dateFormates.put(key, dateFormat);
		}
		
		return dateFormat;
	}
	
	/**
	 * 以默认格式将字符串转换成Date对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @return
	 */
	public static Date stringToDate(String dateString){
		return stringToDate(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定的格式将字符串转换成Date对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String dateString, String pattern) {
//		AssertUtils.assertTrue(StringUtils.isNotBlank(dateString), "Date string can not be null or blank.");
		if (StringUtils.isBlank(dateString))
			return null;
				
		return getDateFormat(pattern).parse(dateString, new ParsePosition(0));
	}
	
	/**
	 * 以默认格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param date
	 * @return 
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param date
	 * @param pattern
	 * @return 
	 */
	public static String dateToString(Date date, String pattern) {
//		AssertUtils.assertNotNull(date, "Date object can not be null.");
		if (date == null)
			return null;
		
		return getDateFormat(pattern).format(date);
	}
	
	/**
	 * 以默认格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param time
	 * @return 
	 */
	public static String timeToString(long time) {
		return timeToString(time, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param time
	 * @param pattern
	 * @return 
	 */
	public static String timeToString(long time, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time); 
		return dateToString(calendar.getTime(), StringUtils.isNotBlank(pattern) ? pattern : DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 将时间数字转换成日期对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time
	 * @return
	 */
	public static Date timeToDate(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time); 
		return calendar.getTime();
	}
	
	/**
	 * 将Object对象转换成日期对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @return
	 */
	public static Date objectToDate(Object object) {
		return objectToDate(object, null);
	}
	
	/**
	 * 将Object对象转换成日期对象，当Object对象为字符串时，将以指定格式转换
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param object
	 * @param pattern
	 * @return
	 */
	public static Date objectToDate(Object object, String pattern) {
		if (object == null)
			return null;
		
		Date date = null;
		if (object instanceof Date) {
			date = (Date) object;
		} else if (object instanceof Calendar) {
			date = ((Calendar) object).getTime();
		} else if (object instanceof Number) {
			date = timeToDate(Long.valueOf(object.toString()));
		} else {
			date = stringToDate(object.toString(), pattern);
		}
		
		return date;
	}
			
	/**
	 * 以默认格式将对象转换成日期字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		return objectToString(obj, null);
	}
	
	/**
	 * 以指定格式将对象转换成日期字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param obj
	 * @param pattern
	 * @return
	 */
	public static String objectToString(Object obj, String pattern) {
		if (obj == null)
			return null;
		
		return getDateFormat(pattern).format(obj);
	}
	
	/**
	 * 以默认格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param dateString
	 * @return 
	 */
	public static long stringToMillis(String dateString) {
		return stringToMillis(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param dateString
	 * @param pattern
	 * @return 
	 */
	public static long stringToMillis(String dateString, String pattern) {
		Date date = stringToDate(dateString, pattern);
		return date != null ? date.getTime() : 0L;
	}
	
	/**
	 * 以默认格式将字符串转换成Unix时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @return
	 */
	public static long stringToUnixTimestamp(String dateString) {
		return stringToUnixTimestamp(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将字符串转换成Unix时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static long stringToUnixTimestamp(String dateString, String pattern) {
		return dateToUnixTimestamp(stringToDate(dateString, pattern));
	}
	
	/**
	 * 将指定日期转换成Unix时间戳
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static long dateToUnixTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis() / 1000;
	}
	
	/**
	 * 判断是否为同一天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static boolean isSameDay(Calendar c1, Calendar c2) {
		if (c1 == null || c2 == null)
			return false;
		
		return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
				c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR));
	}
	
	/**
	 * 判断两个日期是否为同一天
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param now
	 * @param then
	 * @return 
	 */
	public static boolean isSameDay(Date now, Date then) {
		if (now == null || then == null)
			return false;
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(now);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(then);
        return isSameDay(c1, c2);
	}
	
	/**
	 * 判断指定的日期是否为今天
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param date
	 * @return 
	 */
	public static boolean isToday(Date date) {
		return isSameDay(new Date(), date);
	}
	
	/**
	 * 根据生日计算出距今的年龄
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param birthday
	 * @return
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar calendar = Calendar.getInstance();
		if (birthday == null || calendar.before(birthday)) 
			return 0;

		int yearNow = calendar.get(Calendar.YEAR);
		int monthNow = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(birthday);
		int yearBirth = calendar.get(Calendar.YEAR);
		int monthBirth = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) 
					age--;
			} else {
				age--;
			}
		}
		
		return age;
	}
	
	/**
	 * 根据指定的日期获取对应的星座
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param birthday
	 * @return 星座类型,从0至11依次为白羊座至双鱼座
	 */
	public static Horoscope getHoroscope(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		//  将月和日拼接成小数的形式，格式[月].[日]
		double d = Double.valueOf(new StringBuffer(ObjectUtils.toString(month)).append(".").append(day).toString());
		Horoscope horoscope = null;
		if (d >= 3.21 && d <= 4.19 ) {
			horoscope = Horoscope.ARIES;
		} else if (d >= 4.20 && d <= 5.20) {
			horoscope = Horoscope.TAURUS;
		} else if (d >= 5.21 && d <= 6.21) {
			horoscope = Horoscope.GEMINI;
		} else if (d >= 6.22 && d <= 7.22) {
			horoscope = Horoscope.CANCER;
		} else if (d >= 7.23 && d <= 8.22) {
			horoscope = Horoscope.LEO;
		} else if (d >= 8.23 && d <= 9.22) {
			horoscope = Horoscope.VIRGO;
		} else if (d >= 9.23 && d <= 10.23) {
			horoscope = Horoscope.LIBRA;
		} else if (d >= 10.24 && d <= 11.22) {
			horoscope = Horoscope.ACRAB;
		} else if (d >= 11.23 && d <= 12.21) {
			horoscope = Horoscope.SAGITTARIUS;
		} else if ((d >= 12.22 && d <= 12.31) || (d >= 1.01 && d <= 1.19)) {
			horoscope = Horoscope.CAPRICORN;
		} else if (d >= 1.20 && d <= 2.18) {
			horoscope = Horoscope.AQUARIUS;
		} else if (d >= 2.19 && d <= 3.20) {
			horoscope = Horoscope.PISCES;
		}
		
		return horoscope;
	}
	
	/**
	 * 判断指定的日期是否为一个周末(每周最后一天)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		return isWeekend(date, Calendar.MONDAY);
	}
	
	/**
	 * 判断指定的日期是否为一个周末(每周最后一天)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param firstDayOfWeek 周的星期几为第一天
	 * @return
	 */
	public static boolean isWeekend(Date date, int firstDayOfWeek) {
		if (date == null)
			return false;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.setTime(date);
		
		int day = calendar.getFirstDayOfWeek();
		calendar.set(Calendar.DAY_OF_WEEK, day + 6);
		return isSameDay(calendar.getTime(), date);
	}
				
	/**
	 * 累加到指定日期第几年以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的年数
	 * @return
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}
	
	/**
	 * 累加到指定日期第几个月以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的月数
	 * @return
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}
	
	/**
	 * 累加到指定日期第几天以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的天数
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_YEAR, amount);
	}
	
	/**
	 * 累加到指定日期第几小时以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的小时数
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}
	
	/**
	 * 累加到指定日期第几分钟以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的分钟数
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }
	
	/**
	 * 累加到指定日期第几秒以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的秒数
	 * @return
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}
	
	/**
	 * 累加到指定日期第几毫秒以前或以后的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param amount 距离指定日期之前(负数)或之后(正数)的毫秒数
	 * @return
	 */
	public static Date addMillis(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}
	
	/**
	 * 在calendarField范围内将date增加amount个偏移
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param calendarField
	 * @param amount 距离指定日期之前(负数)或之后(正数)的偏移量
	 * @return
	 */
	public static Date add(Date date, int calendarField, int amount) {
		AssertUtils.assertNotNull(date, "Date object can not be null");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarField, amount);
		return calendar.getTime();
	}
	
	/**
	 * 昨天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date yesterday() {
		return addDays(new Date(), -1);
	}
	
	/**
	 * 明天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date tomorrow() {
		return addDays(new Date(), 1);
	}
	
	/**
	 * 判断指定日期是否为闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(Date date) {
		if (date == null)
			return false;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return isLeapYear(calendar.get(Calendar.YEAR));
	}
	
	/**
	 * 判断指定数字是否为闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 计算两日期间有多少个闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getLeapYearCount(Date start, Date end) {
		if (start == null)
			return isLeapYear(end) ? 1 : 0;
		
		if (end == null)
			return isLeapYear(start) ? 1 : 0;
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(start);
		int startYear = calendar.get(Calendar.YEAR);
		
		calendar.setTime(end);
		int endYear = calendar.get(Calendar.YEAR);
		
		if (startYear == endYear)
			return isLeapYear(startYear) ? 1 : 0;
		
		int leapYears = 0;
		int offset = Math.abs(startYear - endYear);
		int year = Math.min(startYear, endYear);
		do {
			if (isLeapYear(year++)) 
				leapYears++;
			
			offset--;
		} while (offset > -1);
		
		return leapYears;
	}
		
	/**
	 * 获取两日期间经历的所有闰年
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> getLeapYears(Date start, Date end) {
		List<Integer> leapYears = CollectionUtils.newArrayList();
		
		if (start == null) {
			if (end != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				int endYear = calendar.get(Calendar.YEAR);
				if (isLeapYear(endYear))
					leapYears.add(endYear);
			}
			
			return leapYears;
		}
		
		if (end == null) {
			if (start != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(start);
				int startYear = calendar.get(Calendar.YEAR);
				if (isLeapYear(startYear))
					leapYears.add(startYear);
			}
			
			return leapYears;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int startYear = calendar.get(Calendar.YEAR);
		
		calendar.setTime(end);
		int endYear = calendar.get(Calendar.YEAR);
						
		if (startYear == endYear) {
			if (isLeapYear(startYear))
				leapYears.add(startYear);
		} else {
			
			int offset = Math.abs(startYear - endYear);
			int year = Math.min(startYear, endYear);
			do {
				if (isLeapYear(year))
					leapYears.add(year);
				
				year++;
				offset--;
			} while (offset > -1);
		}
		
		return leapYears;
	}
	
	/**
	 * 获取当前日期这周内的每一天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static List<Date> everyDayOfWeek() {
		return everyDayOfWeek(Calendar.MONDAY);
	}
	
	/**
	 * 获取当前日期这周内的每一天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static List<Date> everyDayOfWeek(int firstDayOfWeek) {
		return everyDayOfWeek(new Date(), firstDayOfWeek);
	}
	
	/**
	 * 获取指定日期这周内的每一天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param now
	 * @return
	 */
	public static List<Date> everyDayOfWeek(Date date) {
		return everyDayOfWeek(date, Calendar.MONDAY);
	}
	
	/**
	 * 获取指定日期这周内的每一天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static List<Date> everyDayOfWeek(Date date, int firstDayOfWeek) {
		AssertUtils.assertNotNull(date, "Date object can not be null");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.setTime(date);
		
		int day = calendar.getFirstDayOfWeek();
		int initialCapacity = 7;
		
		List<Date> list = CollectionUtils.newArrayList(initialCapacity);
		for (int i = 0; i < initialCapacity; i++) {
			calendar.set(Calendar.DAY_OF_WEEK, day++);
			list.add(calendar.getTime());
		}
		
		return list;
	}
	
	/**
	 * 获当前日期是星期几
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static String dayOfWeek() {
		return dayOfWeek(new Date());
	}
	
	/**
	 * 获取指定的日期是星期几
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param now
	 * @return
	 */
	public static String dayOfWeek(Date now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		String result = null;
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
			case 1:
				result = SUNDAY;
				break;
			case 2:
				result = MONDAY;
				break;
			case 3:
				result = TUESDAY;
				break;
			case 4:
				result = WEDNESDAY;
				break;
			case 5:
				result = THURSDAY;
				break;
			case 6:
				result = FRIDAY;
				break;
			case 7:
				result = SATURDAY;
				break;
		}
		return result;
	}
	
	/**
	 * 获取当前日期所在周内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date firstDayOfWeek() {
		return firstDayOfWeek(new Date());
	}
	
	/**
	 * 获取当前日期所在周内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static Date firstDayOfWeek(int firstDayOfWeek) {
		return firstDayOfWeek(new Date(), firstDayOfWeek);
	}
	
	/**
	 * 获取当前日期所在周内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfWeek(Date date) {
		return firstDayOfWeek(date, Calendar.MONDAY);
	}
	
	/**
	 * 获取当前日期所在周内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param firstDayOfWeek
	 * @return
	 */
	public static Date firstDayOfWeek(Date date, int firstDayOfWeek) {
		return dayOfWeek(date, 1, firstDayOfWeek);
	}
	
	/**
	 * 获取当前日期所在周内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date lastDayOfWeek() {
		return lastDayOfWeek(new Date());
	}
	
	/**
	 * 获取当前日期所在周内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static Date lastDayOfWeek(int firstDayOfWeek) {
		return lastDayOfWeek(new Date(), firstDayOfWeek);
	}
	
	/**
	 * 获取当前日期所在周内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfWeek(Date date) {
		return lastDayOfWeek(date, Calendar.MONDAY);
	}
	
	/**
	 * 获取当前日期所在周内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param firstDayOfWeek
	 * @return
	 */
	public static Date lastDayOfWeek(Date date, int firstDayOfWeek) {
		return dayOfWeek(date, 7, firstDayOfWeek);
	}
		
	/**
	 * 获取当前日期所在周内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param day
	 * @return
	 */
	public static Date dayOfWeek(int day) {
		return dayOfWeek(new Date(), day);
	}
	
	/**
	 * 获取当前日期所在周内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param day
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static Date dayOfWeek(int day, int firstDayOfWeek) {
		return dayOfWeek(new Date(), day, firstDayOfWeek);
	}
	
	/**
	 * 获取指定日期所在周内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dayOfWeek(Date date, int day) {
		return dayOfWeek(date, day, Calendar.MONDAY);
	}
	
	/**
	 * 获取指定日期所在周内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param day
	 * @param firstDayOfWeek 星期几为每周第一天
	 * @return
	 */
	public static Date dayOfWeek(Date date, int day, int firstDayOfWeek) {
		AssertUtils.assertNotNull(date, "Date object can not be null"); 
		AssertUtils.assertTrue(firstDayOfWeek >= 1 && firstDayOfWeek <= 7,
				"First day of week parameter [" + firstDayOfWeek + "] can not out of range [1 - 7]");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.setTime(date);
		
		day = NumberUtils.rangeLimit(firstDayOfWeek + day - 1, firstDayOfWeek, 8);
		calendar.set(Calendar.DAY_OF_WEEK, day);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期所在月内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date firstDayOfMonth() {
		return firstDayOfMonth(new Date());
	}
		
	/**
	 * 获取当前日期所在月内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfMonth(Date date) {
		return dayOfMonth(date, 1);
	}
	
	/**
	 * 获取当前日期所在月内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth() {
		return lastDayOfMonth(new Date());
	}
		
	/**
	 * 获取指定日期所在月内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfMonth(Date date) {
		return dayOfMonth(date, 31);
	}
		
	/**
	 * 获取当前日期所在月内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param day
	 * @return
	 */
	public static Date dayOfMonth(int day) {
		return dayOfMonth(new Date(), day);
	}
	
	/**
	 * 获取指定日期所在月内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dayOfMonth(Date date, int day) {
		int totalDays = totalDaysOfMonth(date);
		day = NumberUtils.minLimit(NumberUtils.maxLimit(day, totalDays), 1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, NumberUtils.rangeLimit(day, 1, totalDays));
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当年日期所在年内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date firstDayOfYear() {
		return firstDayOfYear(new Date());
	}
	
	/**
	 * 获取指定日期所在年内第一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date firstDayOfYear(Date date) {
		return dayOfYear(date, 1);
	}
	
	/**
	 * 获取当年日期所在年内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date lastDayOfYear() {
		return lastDayOfYear(new Date());
	}
	
	/**
	 * 获取指定日期所在年内最后一天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static Date lastDayOfYear(Date date) {
		return dayOfYear(date, 366);
	}
		
	/**
	 * 获取当前日期所在年内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param day
	 * @return
	 */
	public static Date dayOfYear(int day) {
		return dayOfYear(new Date(), day);
	}
	
	/**
	 * 获取指定日期所在年内第几天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date dayOfYear(Date date, int day) {
		int totalDays = totalDaysOfYear(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, NumberUtils.rangeLimit(day, 1, totalDays));
		
		return calendar.getTime();
	}
		
	/**
	 * 获取当前日期所在月的总天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static int totalDaysOfMonth() {
		return totalDaysOfMonth(new Date());
	}
	
	/**
	 * 获取指定日期所在月的总天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static int totalDaysOfMonth(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将日期设置为当月第一天
		calendar.set(Calendar.DATE, 1);
		// 日期回滚一天，也就是最后一天
		calendar.roll(Calendar.DATE, -1);
	    return calendar.get(Calendar.DATE);  
	}
	
	/**
	 * 获取当前日期所在年的总天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static int totalDaysOfYear() {
		return totalDaysOfYear(new Date());
	}
	
	/**
	 * 获取指定日期所在年的总天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static int totalDaysOfYear(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null");
		return isLeapYear(date) ? 366 : 365;
	}
	
	/**
	 * 将单位时间换算成纳秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toNanos(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toNanos(time) : time;
	}
	
	/**
	 * 将单位时间换算成微秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	public static long toMicros(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toMicros(time) : time;
	}
	
	/**
	 * 将单位时间换算成毫秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toMillis(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toMillis(time) : time;
	}
	
	/**
	 * 将单位时间换算成秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toSeconds(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toSeconds(time) : time;
	}
	
	/**
	 * 将单位时间换算成分钟
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toMinutes(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toMinutes(time) : time;
	}
	
	/**
	 * 将单位时间换算成小时
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toHours(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toHours(time) : time;
	}
	
	/**
	 * 将单位时间转换成天
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param timeUnit 时间单位
	 * @return
	 */
	public static long toDays(long time, TimeUnit timeUnit) {
		return timeUnit != null ? timeUnit.toDays(time) : time;
	}
	
	/**
	 * 计算两日期间隔的纳秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalNanos(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toNanos(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的微秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalMicros(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toMicros(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(sniper)</a> 
	 * @param when
	 * @param then
	 * @return 
	 */
	public static long getIntervalMillis(Date when, Date then) {
		AssertUtils.assertTrue(when != null && then != null, "Date must not be null.");
		return Math.abs(then.getTime() - when.getTime());
	}
	
	/**
	 * 计算两日期间隔的秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalSeconds(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toSeconds(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的分钟数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalMinutes(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toMinutes(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的小时数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalHours(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toHours(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalDays(Date when, Date then) {
		return TimeUnit.MILLISECONDS.toDays(getIntervalMillis(when, then));
	}
	
	/**
	 * 计算两日期间隔的周数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalWeeks(Date when, Date then) {
		return getIntervalDays(when, then) / 7;
	}
		
			
}
