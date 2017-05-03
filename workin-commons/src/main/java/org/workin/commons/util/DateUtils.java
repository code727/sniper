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

package org.workin.commons.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.workin.commons.enums.astrology.Horoscope;

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
	
	/** 模式与日期时间格式关系映射集线程局部变量 */
//	private static final ThreadLocal<Map<String, SimpleDateFormat>> dateFormates = new ThreadLocal<Map<String,SimpleDateFormat>>();
	
	/** 全局模式与日期时间格式关系映射集 */
	private static final Map<String, SimpleDateFormat> dateFormates = MapUtils.newConcurrentHashMap();
	
	/** 计量单位与毫秒时间的映射关系 */
	private static final Map<String,Long> UM_MS = MapUtils.newHashMap();
	
	static {
		
		UM_MS.put("ms", 1L);
		UM_MS.put("msec", 1L);
		
		/* 每秒的毫秒数 */
		UM_MS.put("s", 1000L);
		UM_MS.put("sec", 1000L);
		
		/* 每分钟的毫秒数 */
		UM_MS.put("min", 60000L);
		
		/* 每小时的毫秒数 */
		UM_MS.put("h", 3600000L);
		UM_MS.put("hr", 3600000L);
		
		/* 每天的毫秒数 */
		UM_MS.put("d", 86400000L);
		UM_MS.put("day", 86400000L);
		
		/* 每周的毫秒数 */
		UM_MS.put("w", 604800000L);
		
		/* 每个小月的毫秒数 */
		UM_MS.put("m", 2592000000L);
		UM_MS.put("lm", 2592000000L);
		/* 每个大月的毫秒数 */
		UM_MS.put("bm", 2678400000L);
		
		/* 二月的毫秒数 */
		UM_MS.put("feb", 2419200000L);
		/* 闰年二月的毫秒数 */
		UM_MS.put("lyFeb", 2505600000L);
		
		/* 一个平年的毫秒数 */
		UM_MS.put("y", 31536000000L);
		/* 一个闰年的毫秒数 */
		UM_MS.put("ly", 31622400000L);
		
	}
	
	/**
	 * 根据指定的模式获取日期格式对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
//	public static SimpleDateFormat getDateFormat(String pattern) {
//		if (StringUtils.isBlank(pattern))
//			pattern = DEFAULT_DATETIME_FORMAT;
//		
//		Map<String, SimpleDateFormat> formateMap = dateFormates.get();
//		if (formateMap == null)
//			formateMap = MapUtils.newConcurrentHashMap();
//		
//		SimpleDateFormat dateFormat = formateMap.get(pattern);
//		if (dateFormat == null) {
//			dateFormat = new SimpleDateFormat(pattern);
//			formateMap.put(pattern, dateFormat);
//			dateFormates.set(formateMap);
//		}
//		
//		return dateFormat;
//	}
	
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
		AssertUtils.assertTrue(StringUtils.isNotBlank(dateString), 
				"Date string can not be null or blank.");
		return getDateFormat(pattern).parse(dateString, new ParsePosition(0));
	}
	
	/**
	 * 以默认格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @return 
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将日期转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @param pattern
	 * @return 
	 */
	public static String dateToString(Date date, String pattern) {
		AssertUtils.assertNotNull(date, "Date object can not be null.");
		return getDateFormat(pattern).format(date);
	}
	
	/**
	 * 以默认格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param time
	 * @return 
	 */
	public static String timeToString(long time) {
		return timeToString(time, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将时间数字转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param time
	 * @param pattern
	 * @return 
	 */
	public static String timeToString(long time, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time); 
		return dateToString(calendar.getTime(), StringUtils.isNotBlank(pattern) ?
				pattern : DEFAULT_DATETIME_FORMAT);			
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
		AssertUtils.assertNotNull(obj, "Object can not be null.");
		return getDateFormat(pattern).format(obj);
	}
	
	/**
	 * 以默认格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param dateString
	 * @return 
	 */
	public static long stringToMillis(String dateString) {
		return stringToMillis(dateString, DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 以指定格式将字符串转换成时间毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
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
		return calendar.getTimeInMillis()/1000;
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
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param date
	 * @return 
	 */
	public static boolean isToday(Date date) {
		return isSameDay(new Date(), date);
	}
	
	/**
	 * 计算两日期间隔的毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
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
	public static long getIntervalSecond(Date when, Date then) {
		return getIntervalMillis(when, then) / UM_MS.get("sec");
	}
	
	/**
	 * 计算两日期间隔的分钟数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalMinute(Date when, Date then) {
		return getIntervalMillis(when, then) / UM_MS.get("min");
	}
	
	/**
	 * 计算两日期间隔的小时数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalHour(Date when, Date then) {
		return getIntervalMillis(when, then) / UM_MS.get("hr");
	}
	
	/**
	 * 计算两日期间隔的天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalDay(Date when, Date then) {
		return getIntervalMillis(when, then) / UM_MS.get("day");
	}
	
	/**
	 * 计算两日期间隔的周数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static long getIntervalWeek(Date when, Date then) {
		return getIntervalMillis(when, then) / UM_MS.get("w");
	}
	
	/**
	 * 计算两日期间隔的月数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	@Deprecated
	public static long getIntervalMonth(Date when, Date then) {
		return getIntervalYear(when, then) * 12;
	}
	
	/**
	 * 计算两日期间隔的年数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param when
	 * @param then
	 * @return
	 */
	public static int getIntervalYear(Date when, Date then) {
		AssertUtils.assertTrue(when != null && then != null, "Date must not be null.");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(when);
		
		int whenYear = calendar.get(Calendar.YEAR);
		int whenMonth = calendar.get(Calendar.MONTH) + 1;
		int whenDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		calendar.setTime(then);
		int thenYear = calendar.get(Calendar.YEAR);
		int thenMonth = calendar.get(Calendar.MONTH) + 1;
		int thenDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		int year = 0;
		if (when.before(then)) {
			year = thenYear - whenYear;
			if (thenMonth <= whenMonth) {
				if (whenMonth == thenMonth) {
					if (thenDayOfMonth < whenDayOfMonth) 
						year--;
				} else 
					year--;
			}
		} else {
			year = whenYear - thenYear;
			if (whenMonth <= thenMonth) {
				if (whenMonth == thenMonth) {
					if (whenDayOfMonth < thenDayOfMonth) 
						year--;
				} else 
					year--;
			}
		}
		return year;
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
	 * 获取计量单位对应的毫秒数
	 * @author <a href="mailto:code727@gmail.com">杜斌(Daniele)</a> 
	 * @param um
	 * @return 
	 */
	public static long unitMillis(String um) {
		Long ms = UM_MS.get(um);
		return ms != null ? ms : UM_MS.get("ms");
	}
	
	/**
	 * 将单位时间换算成毫秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param um 时间的单位
	 * @return
	 */
	public static long getMillis(long time, String um) {
		return time * unitMillis(um);
	}
	
	/**
	 * 将单位时间换算成秒
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param time 时间
	 * @param um 时间的单位
	 * @return
	 */
	public static long getSecond(long time, String um) {
		return getMillis(time, um) / unitMillis("sec");
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
	public static Date addMilliseconds(Date date, int amount) {
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
	public static Date yesterday(){
		return addDays(new Date(), -1);
	}
	
	/**
	 * 明天的日期
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static Date tomorrow(){
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
	 * 计算两日期间经历有多少个闰年
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
		
		int startYear = Integer.valueOf(dateToString(start, DEFAULT_YEAR_FORMAT));
		int endYear = Integer.valueOf(dateToString(end, DEFAULT_YEAR_FORMAT));
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
	public static Date[] getLeapYears(Date start, Date end) {
		if (start == null)
			return isLeapYear(end) ? new Date[] { end } : new Date[] {};
		if (end == null)
			return isLeapYear(start) ? new Date[] { start } : new Date[] {};
			
		int startYear = Integer.valueOf(dateToString(start, DEFAULT_YEAR_FORMAT));
		int endYear = Integer.valueOf(dateToString(end, DEFAULT_YEAR_FORMAT));
		if (startYear == endYear)
			return isLeapYear(startYear) ? new Date[] { start } : new Date[] {};
		
		int offset = Math.abs(startYear - endYear);
		int year = Math.min(startYear, endYear);
		List<Date> leapYears = CollectionUtils.newArrayList();
		do {
			if (isLeapYear(year))
				leapYears.add(stringToDate(String.valueOf(year), DEFAULT_YEAR_FORMAT));
			
			year++;
			offset--;
		} while (offset > -1);
		
		return leapYears.toArray(new Date[] {});
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
		int maxDays = maxDaysOfMonth(date);
		day = NumberUtils.minLimit(NumberUtils.maxLimit(day, maxDays), 1);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, NumberUtils.rangeLimit(day, 1, maxDays));
		
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期所在月的最大天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static int maxDaysOfMonth() {
		return maxDaysOfMonth(new Date());
	}
	
	/**
	 * 获取指定日期所在月的最大天数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param date
	 * @return
	 */
	public static int maxDaysOfMonth(Date date) {
		AssertUtils.assertNotNull(date, "Date object can not be null");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将日期设置为当月第一天
		calendar.set(Calendar.DATE, 1);
		// 日期回滚一天，也就是最后一天
		calendar.roll(Calendar.DATE, -1);
	    return calendar.get(Calendar.DATE);  
	}
		
}
