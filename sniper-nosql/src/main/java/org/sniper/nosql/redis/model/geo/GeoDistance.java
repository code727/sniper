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

package org.sniper.nosql.redis.model.geo;

import java.io.Serializable;

import org.sniper.nosql.redis.enums.GeoDistanceUnit;

/**
 * GEO距离对象
 * @author  Daniele
 * @version 1.0
 */
public class GeoDistance implements Serializable {
	
	private static final long serialVersionUID = 8506445751001318058L;

	/** 距离值 */
	private final double value;
	
	/** 距离单位 */
	private final GeoDistanceUnit unit;
	
	public GeoDistance(double value) {
		this(value, null);
	}
	
	public GeoDistance(double value, GeoDistanceUnit unit) {
		this.value = value;
		this.unit = (unit != null ? unit : GeoDistanceUnit.METERS);
	}

	public double getValue() {
		return value;
	}

	public GeoDistanceUnit getUnit() {
		return unit;
	}
	
	/**
	 * 将当前值转换为单位为"米"的值
	 * @author Daniele 
	 * @return
	 */
	public double toMeters() {
		return this.unit.toMeters(this.value);
	}
	
	/**
	 * 将当前值转换为单位为"千米"的值
	 * @author Daniele 
	 * @return
	 */
	public double toKilometers() {
		return this.unit.toKilometers(this.value);
	}
	
	/**
	 * 当前值转换为单位为"英里"的值
	 * @author Daniele 
	 * @return
	 */
	public double toMiles() {
		return this.unit.toMiles(this.value);
	}
	
	/**
	 * 当前值转换为单位为"英尺"的值
	 * @author Daniele 
	 * @return
	 */
	public double toFeet() {
		return this.unit.toFeet(this.value);
	}
	
	@Override
	public String toString() {
		return this.value + this.unit.getAbbreviation();
	}
}
