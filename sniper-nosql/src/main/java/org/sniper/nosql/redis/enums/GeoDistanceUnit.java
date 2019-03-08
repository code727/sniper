/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-2-27
 */

package org.sniper.nosql.redis.enums;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * GEO距离单位枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum GeoDistanceUnit {
	
	/** 米 */
	METERS(6378137, "m") {
		
		@Override
		public double toMeters(double value) { return value; }
		
		@Override
		public double toKilometers(double value) { return value * 0.001; }
			
		@Override
		public double toMiles(double value) { return value * 0.0006214; }
			
		@Override
		public double toFeet(double value) { return value * 3.2808399; }
		
	},
	
	/** 公里 */
	KILOMETERS(6378.137, "km") {
		
		@Override
		public double toMeters(double value) { return value * 1000; }
		
		@Override
		public double toKilometers(double value) { return value; }
		
		@Override
		public double toMiles(double value) { return value * 0.6213712; }
		
		@Override
		public double toFeet(double value) { return value * 3280.839895; }
		
	}, 
	
	/** 英里 */
	MILES(3963.191, "mi") {
		
		@Override
		public double toMeters(double value) { return value * 1609.344; }
		
		@Override
		public double toKilometers(double value) { return value * 1.609344; }
		
		@Override
		public double toMiles(double value) { return value; }
		
		@Override
		public double toFeet(double value) { return value * 5280; }
		
	}, 
	
	/** 英尺 */
	FEET(20925646.325, "ft") {
		
		@Override
		public double toMeters(double value) { return value * 0.3048; }
		
		@Override
		public double toKilometers(double value) { return value * 0.0003048; }
		
		@Override
		public double toMiles(double value) { return value * 0.0001894; }
		
		@Override
		public double toFeet(double value) { return value; }
		
	};
	
	private static final Map<String, GeoDistanceUnit> mappings = MapUtils.newHashMap(4);
	
	static {
		for (GeoDistanceUnit unit : values()) {
			mappings.put(unit.abbreviation, unit);
		}
	}
	
	/** 地球的赤道半径 */
	private final double radius;
	
	/** 缩写 */
	private final String abbreviation;

	private GeoDistanceUnit(double radius, String abbreviation) {
		this.radius = radius;
		this.abbreviation = abbreviation;
	}

	public double getRadius() {
		return radius;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
	
	/**
	 * 将指定的值转换为单位为"米"的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public double toMeters(double value) {
		throw new AbstractMethodError();
	}
	
	/**
	 * 将指定的值转换为单位为"千米"的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public double toKilometers(double value) {
		throw new AbstractMethodError();
	}
	
	/**
	 * 将指定的值转换为单位为"英里"的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public double toMiles(double value) {
		throw new AbstractMethodError();
	}
	
	/**
	 * 将指定的值转换为单位为"英尺"的值
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public double toFeet(double value) {
		throw new AbstractMethodError();
	}
	
	/**
	 * 判断指定的缩写是否匹配一个DistanceUnit对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param abbreviation
	 * @return
	 */
	public boolean matches(String abbreviation) {
		return this.abbreviation.equalsIgnoreCase(abbreviation);
	}
	
	/**
	 * 将指定的缩写解析成DistanceUnit对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param abbreviation
	 * @return
	 */
	public static GeoDistanceUnit resolve(String abbreviation) {
		return abbreviation != null ? mappings.get(abbreviation.toLowerCase()) : null;
	}

}
