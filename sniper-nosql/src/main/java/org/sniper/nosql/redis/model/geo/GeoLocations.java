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
import java.util.Map;

/**
 * Geo空间元素集接口
 * @author  Daniele
 * @version 1.0
 */
public interface GeoLocations<M> extends Serializable {
	
	/**
	 * 添加地理位置坐标点成员
	 * @author Daniele 
	 * @param member
	 * @param point
	 */
	public void add(M member, GeoPoint point);
	
	/**
	 * 根据成员名获取地理位置坐标点
	 * @author Daniele 
	 * @param member
	 * @return
	 */
	public GeoPoint get(M member);

	/**
	 * 获取所有成员的地理位置坐标点
	 * @author Daniele 
	 * @return
	 */
	public Map<M, GeoPoint> getMemberLocations();
	
	/**
	 * 根据成员名删除对应的地理位置坐标点，并返回已删除的坐标点
	 * @author Daniele 
	 * @param member
	 * @return
	 */
	public GeoPoint remove(M member);
	
	/**
	 * 获取空间元素的个数
	 * @author Daniele 
	 * @return
	 */
	public int size();
	
	/**
	 * 判断是否为空
	 * @author Daniele 
	 * @return
	 */
	public boolean isEmpty();
}
