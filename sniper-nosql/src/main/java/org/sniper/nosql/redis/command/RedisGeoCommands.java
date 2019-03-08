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
 * Create Date : 2019-2-21
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;

import org.sniper.nosql.redis.enums.GeoDistanceUnit;
import org.sniper.nosql.redis.model.geo.GeoCircle;
import org.sniper.nosql.redis.model.geo.GeoDistance;
import org.sniper.nosql.redis.model.geo.GeoLocations;
import org.sniper.nosql.redis.model.geo.GeoPoint;
import org.sniper.nosql.redis.model.geo.GeoRadiusResult;
import org.sniper.nosql.redis.option.GeoRadiusOption;

/**
 * Redis地理位置命令行接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface RedisGeoCommands {
	
	/**
	 * 在当前库中执行geoAdd命令，添加一个空间元素(坐标点+成员名称)后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param point
	 * @return
	 */
	public <K, M> Long geoAdd(K key, M member, GeoPoint point);
	
	/**
	 * 在当前库中执行geoAdd命令，添加一个空间元素(坐标点+成员名称)并设置键的过期时间后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param point
	 * @param expireSeconds
	 * @return
	 */
	public <K, M> Long geoAdd(K key, M member, GeoPoint point, long expireSeconds);
	
	/**
	 * 在指定库中执行geoAdd命令，添加一个空间元素(坐标点+成员名称)后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param point
	 * @return
	 */
	public <K, M> Long geoAdd(String dbName, K key, M member, GeoPoint point);
	
	/**
	 * 在指定库中执行geoAdd命令，添加一个空间元素(坐标点+成员名称)并设置键的过期时间后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param point
	 * @param expireSeconds
	 * @return
	 */
	public <K, M> Long geoAdd(String dbName, K key, M member, GeoPoint point, long expireSeconds);
	
	/**
	 * 在当前库中执行geoAdd命令，添加多个空间元素(坐标点+成员名称)后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locations
	 * @return
	 */
	public <K, M> Long geoAdd(K key, GeoLocations<M> locations);
	
	/**
	 * 在当前库中执行geoAdd命令，添加多个空间元素(坐标点+成员名称)并设置键的过期时间后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param locations
	 * @param expireSeconds
	 * @return
	 */
	public <K, M> Long geoAdd(K key, GeoLocations<M> locations, long expireSeconds);
	
	/**
	 * 在指定库中执行geoAdd命令，添加多个空间元素(坐标点+成员名称)后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param locations
	 * @return
	 */
	public <K, M> Long geoAdd(String dbName, K key, GeoLocations<M> locations);
	
	/**
	 * 在指定库中执行geoAdd命令，添加多个空间元素(坐标点+成员名称)并设置键的过期时间后返回添加成功的个数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param locations
	 * @param expireSeconds
	 * @return
	 */
	public <K, M> Long geoAdd(String dbName, K key, GeoLocations<M> locations, long expireSeconds);
	
	/**
	 * 在当前库中执行geoPos命令，获取指定键成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, M> GeoPoint geoPos(K key, M member);
	
	/**
	 * 在指定库中执行geoPos命令，获取指定键成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @return
	 */
	public <K, M> GeoPoint geoPos(String dbName, K key, M member);
	
	/**
	 * 在当前库中执行geoPos命令，获取指定键多个成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, M> GeoLocations<M> geoPos(K key, M[] members);
	
	/**
	 * 在指定库中执行geoPos命令，获取指定键多个成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, M> GeoLocations<M> geoPos(String dbName, K key, M[] members);
	
	/**
	 * 在当前库中执行geoPos命令，获取指定键多个成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, M> GeoLocations<M> geoPos(K key, Collection<M> members);
	
	/**
	 * 在指定库中执行geoPos命令，获取指定键多个成员的地理位置坐标点
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param members
	 * @return
	 */
	public <K, M> GeoLocations<M> geoPos(String dbName, K key, Collection<M> members);
	
	/**
	 * 在当前库中执行geoDist命令，获取两个成员位置之间的距离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member1
	 * @param member2
	 * @return
	 */
	public <K, M> GeoDistance geoDist(K key, M member1, M member2);
	
	/**
	 * 在当前库中执行geoDist命令，获取两个成员位置之间的距离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member1
	 * @param member2
	 * @param unit
	 * @return
	 */
	public <K, M> GeoDistance geoDist(K key, M member1, M member2, GeoDistanceUnit unit);
	
	/**
	 * 在指定库中执行geoDist命令，获取两个成员位置之间的距离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member1
	 * @param member2
	 * @return
	 */
	public <K, M> GeoDistance geoDistIn(String dbName, K key, M member1, M member2);
	
	/**
	 * 在指定库中执行geoDist命令，获取两个成员位置之间的距离
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member1
	 * @param member2
	 * @param unit
	 * @return
	 */
	public <K, M> GeoDistance geoDist(String dbName, K key, M member1, M member2, GeoDistanceUnit unit);
	
	/**
	 * 在当前库中执行geoRadius命令，获取与中心距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param circle
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadius(K key, GeoCircle circle);
	
	/**
	 * 在当前库中执行geoRadius命令，获取与中心距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param circle
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadius(K key, GeoCircle circle, GeoRadiusOption option);
	
	/**
	 * 在指定库中执行geoRadius命令，获取与中心距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param circle
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadius(String dbName, K key, GeoCircle circle);
	
	/**
	 * 在指定库中执行geoRadius命令，获取与中心距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param circle
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadius(String dbName, K key, GeoCircle circle, GeoRadiusOption option);
	
	/**
	 * 在当前库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param radius
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(K key, M member, double radius);
	
	/**
	 * 在当前库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param radius
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(K key, M member, GeoDistance radius);
	
	/**
	 * 在当前库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param radius
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(K key, M member, double radius, GeoRadiusOption option);
	
	/**
	 * 在当前库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param key
	 * @param member
	 * @param radius
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(K key, M member, GeoDistance radius, GeoRadiusOption option);
	
	/**
	 * 在指定库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param radius
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(String dbName, K key, M member, double radius);
	
	/**
	 * 在指定库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param radius
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(String dbName, K key, M member, GeoDistance radius);
	
	/**
	 * 在指定库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param radius
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(String dbName, K key, M member, double radius, GeoRadiusOption option);
	
	/**
	 * 在指定库中执行geoRadiusByMember命令，获取与成员距离不超过给定最大距离的所有位置元素
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param dbName
	 * @param key
	 * @param member
	 * @param radius
	 * @param option
	 * @return
	 */
	public <K, M> GeoRadiusResult<M> geoRadiusByMember(String dbName, K key, M member, GeoDistance radius, GeoRadiusOption option);
	
}
