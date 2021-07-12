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

package org.sniper.nosql.redis.option;

import java.io.Serializable;
import java.util.Set;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.nosql.redis.enums.Order;

/**
 * GeoRadius命令行选项
 * @author  Daniele
 * @version 1.0
 */
public class GeoRadiusOption implements Serializable {
	
	private static final long serialVersionUID = -7960100076722043791L;

	/**
	 * 附加信息枚举类
	 * @author  Daniele
	 * @version 1.0
	 */
	public enum Attach {
		/** 在返回位置元素的同时，将位置元素与中心之间的距离也一并返回。 距离的单位和用户给定的范围单位保持一致 */
		WITHDIST,
		/** 将位置元素的经度和维度也一并返回 */
		WITHCOORD,
		/** 以52位有符号整数的形式，返回位置元素经过原始geohash编码的有序集合分值。 这个选项主要用于底层应用或者调试， 实际中的作用并不大 */
		WITHHASH,
	}
	
	/** 附加信息 */
	private final Set<Attach> attachs;
	
	/** 排序方式 */
	private Order order;
	
	/** 获取结果的个数(前面count个) */
	private long count;
	
	public GeoRadiusOption() {
		this.attachs = CollectionUtils.newLinkedHashSet(3);
	}
	
	/**
	 * 添加WITHDIST附加信息
	 * @author Daniele 
	 * @return
	 */
	public GeoRadiusOption withDist() {
		this.attachs.add(Attach.WITHDIST);
		return this;
	}
	
	/**
	 * 添加WITHCOORD附加信息
	 * @author Daniele 
	 * @return
	 */
	public GeoRadiusOption withCoord() {
		this.attachs.add(Attach.WITHCOORD);
		return this;
	}
	
	/**
	 * 添加WITHHASH附加信息
	 * @author Daniele 
	 * @return
	 */
	public GeoRadiusOption withHash() {
		this.attachs.add(Attach.WITHHASH);
		return this;
	}
	
	/**
	 * 判断是否包含有WITHCOORD选项
	 * @author Daniele 
	 * @return
	 */
	public boolean includeCoordinates() {
		return this.attachs.contains(Attach.WITHCOORD);
	}
	
	/**
	 * 判断是否包含有WITHDIST选项
	 * @author Daniele 
	 * @return
	 */
	public boolean includeDistance() {
		return this.attachs.contains(Attach.WITHDIST);
	}
	
	/**
	 * 判断是否包含有WITHHASH选项
	 * @author Daniele 
	 * @return
	 */
	public boolean includeHash() {
		return this.attachs.contains(Attach.WITHHASH);
	}
	
	public Set<Attach> getAttachs() {
		return attachs;
	}

	public Order getOrder() {
		return order;
	}

	public GeoRadiusOption setOrder(Order order) {
		this.order = order;
		return this;
	}

	public long getCount() {
		return count;
	}

	public GeoRadiusOption setCount(long count) {
		AssertUtils.assertTrue(count > 0, "Count must greater than 0");
		this.count = count;
		return this;
	}
}
