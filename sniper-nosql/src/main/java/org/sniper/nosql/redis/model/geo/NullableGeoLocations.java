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
 * Create Date : 2019-2-25
 */

package org.sniper.nosql.redis.model.geo;

/**
 * 可为空的Geo空间元素集实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class NullableGeoLocations<M> extends AbstractGeoLocations<M> {
	
	private static final long serialVersionUID = -1391863203761115691L;

	public NullableGeoLocations() {
		super();
	}
	
	public NullableGeoLocations(int initialCapacity) {
		super(initialCapacity);
	}
	
	@Override
	public void add(M member, GeoPoint point) {
		this.memberLocations.put(member, point);
	}
	
}
