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

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * GeoRadius命令行返回的空间元素集实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class GeoRadiusResult<M> {
	
	protected final Map<M, GeoRadiusLocation<M>> memberRadiusLocations;
	
	public GeoRadiusResult() {
		this(16);
	}
	
	public GeoRadiusResult(int initialCapacity) {
		this.memberRadiusLocations = MapUtils.newLinkedHashMap(initialCapacity);
	}
	
	public void add(M member, GeoRadiusLocation<M> location) {
		this.memberRadiusLocations.put(member, location != null ? location : new GeoRadiusLocation<M>(member));
	}
	
	public GeoRadiusLocation<M> get(M member) {
		return this.memberRadiusLocations.get(member);
	}

	public Map<M, GeoRadiusLocation<M>> getMemberRadiusLocations() {
		return MapUtils.newUnmodifiableMap(memberRadiusLocations);
	}
	
	/**
	 * 获取指定成员位置与中心点之间的距离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param member
	 * @return
	 */
	public GeoDistance getCenterDistance(M member) {
		GeoRadiusLocation<M> radiusLocation = this.get(member);
		return radiusLocation != null ? radiusLocation.getCenterDistance() : null;
	}
	
	/**
	 * 获取指定成员位置经过原始 geohash编码后的有序集合分值(52位有符号整数形式)
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param member
	 * @return
	 */
	public Long getGeoHash(M member) {
		GeoRadiusLocation<M> radiusLocation = this.get(member);
		return radiusLocation != null ? radiusLocation.getGeoHash() : null;
	}
	
	/**
	 * 获取指定成员位置的经维度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param member
	 * @return
	 */
	public GeoPoint getPoint(M member) {
		GeoRadiusLocation<M> radiusLocation = this.get(member);
		return radiusLocation != null ? radiusLocation.getPoint() : null;
	}
	
	@Override
	public String toString() {
		return this.memberRadiusLocations.values().toString();
	}

}
