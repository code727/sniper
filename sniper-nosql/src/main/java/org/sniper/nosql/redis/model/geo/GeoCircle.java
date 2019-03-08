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
 * Create Date : 2019-2-28
 */

package org.sniper.nosql.redis.model.geo;

import java.io.Serializable;

import org.sniper.commons.util.AssertUtils;

/**
 * 地理位置坐标环
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GeoCircle implements Serializable {

	private static final long serialVersionUID = -3252606407574421254L;
	
	/** 中心点 */
	private final GeoPoint center;
	
	/** 限定的距离范围 */
	private final GeoDistance radius;
	
	public GeoCircle(GeoPoint center, GeoDistance radius) {
		AssertUtils.assertNotNull(center, "Center point must not be null");
		AssertUtils.assertNotNull(radius, "Radius distance must not be null");
		AssertUtils.assertTrue(radius.getValue() > 0, String.format(
				"Radius value '%s' must greater than 0", radius.getValue()));
		
		this.center = center;
		this.radius = radius;
	}
	
	public GeoCircle(GeoPoint center, double radius) {
		this(center, new GeoDistance(radius));
	}
	
	public GeoCircle(double longitudeCenter, double latitudeCenter, double radius) {
		this(new GeoPoint(longitudeCenter, latitudeCenter), new GeoDistance(radius));
	}

	public GeoPoint getCenter() {
		return center;
	}

	public GeoDistance getRadius() {
		return radius;
	}
	
	@Override
	public String toString() {
		return String.format("{center=%s, radius=%s}", center, radius);
	}

}
