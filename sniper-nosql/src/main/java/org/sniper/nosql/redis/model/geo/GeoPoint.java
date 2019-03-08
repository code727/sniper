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
 * Create Date : 2019-2-22
 */

package org.sniper.nosql.redis.model.geo;

import java.io.Serializable;
import java.util.Locale;

/**
 * 地理位置坐标点
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GeoPoint implements Serializable {
	
	private static final long serialVersionUID = -4002209846344905814L;

	/** 经度(x) */
	private double longitude;
	
	/** 维度(y) */
	private double latitude;
	
	public GeoPoint() {}
	
	/**
	 * 构造地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param longitude 经度(x)
	 * @param latitude 维度(y)
	 */
	public GeoPoint(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * 获取经度(x)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 设置经度(x)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 获取维度(y)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 设置维度(y)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ENGLISH, "[x=%f, y=%f]", longitude, latitude);
	}

}
