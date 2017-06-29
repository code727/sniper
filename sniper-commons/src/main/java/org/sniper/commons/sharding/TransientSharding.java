/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-4-18
 */

package org.sniper.commons.sharding;

/**
 * 瞬时分片实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TransientSharding implements Sharding {
	
	/** 瞬时路由对象 */
	private transient Route route;
	
	public TransientSharding() {
		this(null);
	}
	
	public TransientSharding(Route route) {
		this.route = route;
	}

	@Override
	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public Route getRoute() {
		return route;
	}
	
}
