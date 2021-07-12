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

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * Geo空间元素集抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractGeoLocations<M> implements GeoLocations<M> {

	private static final long serialVersionUID = 5162871313301342121L;
	
	protected final Map<M, GeoPoint> memberLocations;
	
	public AbstractGeoLocations() {
		this(16);
	}
	
	public AbstractGeoLocations(int initialCapacity) {
		this.memberLocations = MapUtils.newLinkedHashMap(initialCapacity);
	}

	@Override
	public GeoPoint get(M member) {
		return this.memberLocations.get(member);
	}

	@Override
	public Map<M, GeoPoint> getMemberLocations() {
		return MapUtils.newUnmodifiableMap(this.memberLocations);
	}

	@Override
	public GeoPoint remove(M member) {
		return this.memberLocations.remove(member);
	}

	@Override
	public int size() {
		return this.memberLocations.size();
	}

	@Override
	public boolean isEmpty() {
		return this.memberLocations.isEmpty();
	}
	
	@Override
	public String toString() {
		return this.memberLocations.toString();
	}

}
