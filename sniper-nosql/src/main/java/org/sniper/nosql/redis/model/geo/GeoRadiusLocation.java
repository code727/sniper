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
 * Create Date : 2019-3-4
 */

package org.sniper.nosql.redis.model.geo;

import java.io.Serializable;

import org.sniper.commons.util.StringUtils;

/**
 * Geo半径位置实现类
 * @author  Daniele
 * @version 1.0
 */
public class GeoRadiusLocation<M> implements Serializable {

	private static final long serialVersionUID = -8765295686853835473L;
	
	private final M member;
	
	/** 成员位置与中心点之间的距离 */
	private GeoDistance centerDistance;
	
	/** 成员位置经过原始 geohash编码后的有序集合分值(52位有符号整数形式) */
	private Long geoHash;
	
	/** 成员位置的经维度 */
	private GeoPoint point;
	
	public GeoRadiusLocation(M member) {
		this.member = member;
	}

	public GeoDistance getCenterDistance() {
		return centerDistance;
	}

	public void setCenterDistance(GeoDistance centerDistance) {
		this.centerDistance = centerDistance;
	}
	
	public Long getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(Long geoHash) {
		this.geoHash = geoHash;
	}

	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	public M getMember() {
		return member;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (this.member != null) 
			builder.append("member:").append(this.member);
		
		if (this.centerDistance != null) {
			if (builder.length() > 0)
				builder.append(StringUtils.COMMA);
			
			builder.append("centerDistance:").append(this.centerDistance);
		}
			
		if (this.geoHash != null) {
			if (builder.length() > 0)
				builder.append(StringUtils.COMMA);
			
			builder.append("geoHash:").append(this.geoHash);
		}
		
		if (this.point != null) {
			if (builder.length() > 0)
				builder.append(StringUtils.COMMA);
			
			builder.append("point:").append(this.point);
		}
			
		return builder.insert(0, StringUtils.LEFT_BRACE).append(StringUtils.RIGHT_BRACE).toString();
	}
	
}
