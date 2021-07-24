/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-3-26
 */

package org.sniper.nosql.redis.spring;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sniper.beans.PropertyUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.command.RedisSupport;
import org.sniper.nosql.redis.enums.GeoDistanceUnit;
import org.sniper.nosql.redis.enums.ListPosition;
import org.sniper.nosql.redis.enums.ZStoreAggregate;
import org.sniper.nosql.redis.model.geo.GeoCircle;
import org.sniper.nosql.redis.model.geo.GeoDistance;
import org.sniper.nosql.redis.model.geo.GeoLocations;
import org.sniper.nosql.redis.model.geo.GeoPoint;
import org.sniper.nosql.redis.model.geo.GeoRadiusLocation;
import org.sniper.nosql.redis.model.geo.GeoRadiusResult;
import org.sniper.nosql.redis.model.geo.NullableGeoLocations;
import org.sniper.nosql.redis.model.zset.DefaultZSetTuple;
import org.sniper.nosql.redis.model.zset.ZSetTuple;
import org.sniper.nosql.redis.option.GeoRadiusOption;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.SortOptional;
import org.sniper.nosql.redis.serializer.SpringRedisSerializerProxy;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.TypedSerializer;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.DefaultSortParameters;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * SpringRedis支持类
 * @author  Daniele
 * @version 1.0
 */
public abstract class SpringRedisSupport extends RedisSupport {
	
	private RedisTemplate<?, ?> redisTemplate;
	
	public RedisTemplate<?, ?> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<?, ?> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	protected void checkProperties() {
		if (this.redisTemplate == null)
			throw new IllegalArgumentException("Property 'RedisTemplate' must not be null");
	}
		
	@Override
	protected void initializeDefaultDbIndex() throws Exception {
		RedisConnectionFactory connectionFactory = this.redisTemplate.getConnectionFactory();
		Field dbIndex = ReflectionUtils.getField(connectionFactory, "dbIndex");
		if (dbIndex != null)
			this.defaultDbIndex = ReflectionUtils.getFieldValue(connectionFactory, dbIndex);
		else
			this.defaultDbIndex = 0;
	}

	@Override
	protected void initializeGlobalSerializers() {
		super.initializeGlobalSerializers();
		
		if (getGlobalKeySerializer() == null) {
			RedisSerializer<?> redisKeySerializer = this.redisTemplate.getKeySerializer();
			setGlobalKeySerializer(redisKeySerializer != null 
					? new SpringRedisSerializerProxy(redisKeySerializer) : getDefaultSerializer());
		}
		
		if (getGlobalValueSerializer() == null) {
			RedisSerializer<?> redisValueSerializer = this.redisTemplate.getValueSerializer();
			setGlobalValueSerializer(redisValueSerializer != null 
					? new SpringRedisSerializerProxy(redisValueSerializer) : getDefaultSerializer());
		}
		
		if (getGlobalHashKeySerializer() == null) {
			RedisSerializer<?> redisHashKeySerializer = this.redisTemplate.getHashKeySerializer();
			setGlobalHashKeySerializer(redisHashKeySerializer != null
					? new SpringRedisSerializerProxy(redisHashKeySerializer) : getDefaultSerializer());
		}
		
		if (getGlobalHashValueSerializer() == null) {
			RedisSerializer<?> redisHashValueSerializer = this.redisTemplate.getHashValueSerializer();
			setGlobalHashValueSerializer(redisHashValueSerializer != null
					? new SpringRedisSerializerProxy(redisHashValueSerializer) : getDefaultSerializer());
		}
	}
	
	/**
	 * 选择并连接库
	 * @author Daniele 
	 * @param connection
	 * @param dbName
	 * @return
	 */
	protected RedisRepository select(RedisConnection connection, String dbName) {
		if (repositoryManager == null)
			return null;
		
		RedisRepository redisRepository = repositoryManager.getRepository(dbName);
		if (redisRepository != null) {
			int dbIndex = redisRepository.getDbIndex();
			if (dbIndex != this.defaultDbIndex && !isCluster())
				// 非集群环境下select命令才能被执行
				connection.select(dbIndex);
		}
		
		return redisRepository;
	}
			
	/**
	 * 设置键的过期时间
	 * @author Daniele 
	 * @param connection
	 * @param keyByte
	 * @param expireTime
	 */
	protected void setExpireTime(RedisConnection connection, byte[] keyByte, long expireTime) {
		openPipeline(connection);
		connection.expire(keyByte, expireTime);
		/* 注意：当管道关闭失败时，会引起上面的过期设置无效，
		 * 因此该方法与其他Redis命令组合在一起使用时，并不能保证这一系列组合的原子性 */
		closePipeline(connection);
	}
	
	/**
	 * 批量设置多个键的过期时间
	 * @author Daniele 
	 * @param connection
	 * @param keyBytes
	 * @param expireTime
	 */
	protected void batchSetExpireTime(RedisConnection connection, Set<byte[]> keyBytes, long expireTime) {
		openPipeline(connection);
		for (byte[] keyByte : keyBytes) {
			connection.expire(keyByte, expireTime);
		}
		/* 注意：当管道关闭失败时，会引起上面的过期设置无效，
		 * 因此该方法与其他Redis命令组合在一起使用时，并不能保证这一系列组合的原子性 */
		closePipeline(connection);
	}
	
	/**
	 * 将ListPosition枚举转换为Spring的列表位置枚举
	 * @author Daniele 
	 * @param position
	 * @return
	 */
	protected Position toPosition(ListPosition position) {
		return position == ListPosition.BEFORE ? Position.BEFORE : Position.AFTER;
	}
	
	/**
	 * 将有序集合聚合方式枚举转换为Spring的Aggregate对象
	 * @author Daniele 
	 * @param aggregate
	 * @return
	 */
	protected Aggregate toAggregate(ZStoreAggregate aggregate) {
		return aggregate != null ? Aggregate.valueOf(aggregate.name()) : Aggregate.MAX;
	}
	
	/**
	 * 将地理位置坐标点转换为Spring的Point对象
	 * @author Daniele 
	 * @param point
	 * @return
	 */
	protected Point toPoint(GeoPoint point) {
		return new Point(point.getLongitude(), point.getLatitude());
	}
	
	/**
	 * 将Spring的Point对象转换为地理位置坐标点
	 * @author Daniele 
	 * @param point
	 * @return
	 */
	protected GeoPoint toGeoPoint(Point point) {
		return point != null ? new GeoPoint(point.getX(), point.getY()) : null;
	}
	
	/**
	 * 将多个成员对应的Point对象转换为地理位置坐标点
	 * @author Daniele 
	 * @param members
	 * @param points
	 * @return
	 */
	protected <M> GeoLocations<M> toGeoLocations(M[] members, List<Point> points) {
		GeoLocations<M> locations = new NullableGeoLocations<M>(members.length);
		if (CollectionUtils.isEmpty(points)) {
			for (M member : members) {
				locations.add(member, null);
			}
		} else {
			for (int i = 0; i < members.length; i++) {
				locations.add(members[i], toGeoPoint(points.get(i)));
			}
		}
		
		return locations;
	}
	
	/**
	 * 将排序可选项转换为Spring的SortParameters对象
	 * @author Daniele 
	 * @param optional
	 */
	protected SortParameters toSortParameters(SortOptional optional) {
		if (optional == null) 
			return null;
			
		Limit limit = optional.getLimit();
		org.sniper.nosql.redis.enums.Order order = optional.getOrder();
		
		SortParameters.Range range = (limit != null ? new SortParameters.Range(limit.getOffset(), limit.getCount()) : null);
		Order sortOrder = (order != null ? Order.valueOf(order.name()) : null);
		return new DefaultSortParameters(optional.getBy(), range, optional.getGets(), sortOrder, optional.isAlpha());
	}
	
	/**
	 * 将Geo空间元素集转换为Spring坐标点与成员之间的映射集
	 * @author Daniele 
	 * @param locations
	 * @return
	 */
	protected <M> Map<byte[], Point> toMemberPointMap(GeoLocations<M> locations) {
		Serializer serializer = getDefaultSerializer();
		
		Set<Entry<M, GeoPoint>> locationEntry = locations.getMemberLocations().entrySet();
		Map<byte[], Point> memberCoordinateMap = MapUtils.newHashMap(locationEntry.size());
		for (Entry<M, GeoPoint> location : locationEntry) {
			GeoPoint point = location.getValue();
			memberCoordinateMap.put(serializer.serialize(location.getKey()), point != null ? 
					new Point(point.getLongitude(), point.getLatitude()) : null);
		}
		
		return memberCoordinateMap;
	}
	
	/**
	 * 将GEO距离单位枚举对象转换为Spring的度量(Metric)对象
	 * @author Daniele 
	 * @param unit
	 * @return
	 */
	protected Metric toMetric(GeoDistanceUnit unit) {
		for (DistanceUnit distanceUnit : DistanceUnit.values()) {
			if (distanceUnit.getAbbreviation().equals(unit.getAbbreviation()))
				return distanceUnit;
		}
		
		return null;
	}
	
	/**
	 * 将Spring的度量(Metric)对象转换为GEO距离单位枚举
	 * @author Daniele 
	 * @param metric
	 * @return
	 */
	protected GeoDistanceUnit toGeoDistanceUnit(Metric metric) {
		return GeoDistanceUnit.resolve(metric.getAbbreviation());
	}
	
	/**
	 * 将地理位置坐标环转换为Spring的Circle对象
	 * @author Daniele 
	 * @param geocircle
	 * @return
	 */
	protected Circle toCircle(GeoCircle geocircle) {
		return new Circle(toPoint(geocircle.getCenter()), toDistance(geocircle.getRadius()));
	}

	/**
	 * 将距离对象转换为Spring的Distance对象
	 * @author Daniele 
	 * @param geoDistance
	 * @return
	 */
	protected Distance toDistance(GeoDistance geoDistance) {
		return new Distance(geoDistance.getValue(), toMetric(geoDistance.getUnit()));
	}
	
	/**
	 * 将指定单位的距离值字节数组转换为GEO距离对象
	 * @author Daniele 
	 * @param distanceByte
	 * @param unit
	 * @return
	 */
	protected GeoDistance toGeoDistance(byte[] distanceByte, GeoDistanceUnit unit) {
		String distance = stringSerializer.deserialize(distanceByte);
		return StringUtils.isNotBlank(distance) ? new GeoDistance(Double.parseDouble(distance), unit) : null;
	}
	
	/**
	 * 将指定的Spring距离对象转换为GEO距离对象
	 * @author Daniele 
	 * @param distance
	 * @return
	 */
	protected GeoDistance toGeoDistance(Distance distance) {
		if (distance == null)
			return null;
		
		return new GeoDistance(distance.getValue(), toGeoDistanceUnit(distance.getMetric()));
	}
	
	/**
	 * 将GeoRadius命令行选项转换为Spring的GeoRadiusCommandArgs对象
	 * @author Daniele 
	 * @param option
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	protected GeoRadiusCommandArgs toGeoRadiusCommandArgs(GeoRadiusOption option) {
		GeoRadiusCommandArgs args = GeoRadiusCommandArgs.newGeoRadiusArgs(); 
		
		if (option.includeCoordinates())
			args.includeCoordinates();
		
		if (option.includeDistance())
			args.includeDistance();
		
		org.sniper.nosql.redis.enums.Order order = option.getOrder();
		if (order != null) {
			if (Order.ASC.equals(order))
				args.sortAscending();
			
			if (Order.DESC.equals(order))
				args.sortDescending();
		}
		
		long count = option.getCount();
		if (count > 0)
			args.limit(count);
		
		return args;
	}
	
	/**
	 * 将Spring的GEO结果转换为GeoRadius命令行返回的空间元素集
	 * @author Daniele 
	 * @param dbName
	 * @param geoResults
	 * @return
	 */
	protected <M> GeoRadiusResult<M> toGeoRadiusResult(String dbName, GeoResults<GeoLocation<byte[]>> geoResults) {
		List<GeoResult<GeoLocation<byte[]>>> results;
		if (geoResults == null || CollectionUtils.isEmpty(results = geoResults.getContent()))
			return null;
		
		Serializer valueSerializer = selectValueSerializer(dbName);
		GeoRadiusResult<M> radiusResult = new GeoRadiusResult<M>();
		for(GeoResult<GeoLocation<byte[]>> result : results) {
			GeoLocation<byte[]> content = result.getContent();
			GeoRadiusLocation<M> location = new GeoRadiusLocation<M>(valueSerializer.deserialize(content.getName()));
			
			location.setPoint(toGeoPoint(content.getPoint()));
			location.setCenterDistance(toGeoDistance(result.getDistance()));
//			location.setGeoHash(null);
			
			radiusResult.add(location.getMember(), location);
		}
		
		return radiusResult;
	}
	
	/**
	 * 序列化带排名值的成员
	 * @author Daniele 
	 * @param dbName
	 * @param scoreMembers
	 * @return
	 */
	protected <V> Set<Tuple> serializeScoreMembers(String dbName, Map<V, Double> scoreMembers) {
		Serializer valueSerializer = selectValueSerializer(dbName);
		
		Set<Tuple> tuples = CollectionUtils.newLinkedHashSet();
		Set<Entry<V, Double>> entrySet = scoreMembers.entrySet();
		for (Entry<V, Double> entry : entrySet) {
			V member = entry.getKey();
			Double score = entry.getValue();
			if (member != null && score != null)
				tuples.add(new DefaultTuple(valueSerializer.serialize(member), score));
		}
		
		return tuples;
	}
	
	/**
	 * 反序列Spring的元组结果集
	 * @author Daniele 
	 * @param dbName
	 * @param tuples
	 * @param valueType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <V> Set<ZSetTuple<V>> deserializeTuples(String dbName, Set<Tuple> tuples, Class<V> valueType) {
		if (CollectionUtils.isEmpty(tuples))
			return null;
		
		Serializer valueSerializer = selectValueSerializer(dbName);
		Set<ZSetTuple<V>> zSetTuples = CollectionUtils.newLinkedHashSet();
		if (valueSerializer.isTypedSerializer()) {
			TypedSerializer typedValueSerializer = (TypedSerializer) valueSerializer;
			for (Tuple tuple : tuples) {
				zSetTuples.add(new DefaultZSetTuple<V>(tuple.getScore(),
						typedValueSerializer.deserialize(tuple.getValue(), valueType)));
			}
		} else {
			PropertyEditor propertyEditor = getPropertyConverter().find(valueType);
			if (propertyEditor != null) {
				for (Tuple tuple : tuples) {
					V member = PropertyUtils.converte(propertyEditor, valueSerializer.deserialize(tuple.getValue()));
					zSetTuples.add(new DefaultZSetTuple<V>(tuple.getScore(), member));
				}
			} else {
				for (Tuple tuple : tuples) {
					zSetTuples.add(new DefaultZSetTuple<V>(tuple.getScore(),
							(V) valueSerializer.deserialize(tuple.getValue())));
				}
			}
		}
		
		return zSetTuples;
	}
	
	/**
	 * 打开管道
	 * @author Daniele 
	 * @param connection
	 */
	private void openPipeline(RedisConnection connection) {
		if (!connection.isPipelined())
			connection.openPipeline();
	}
	
	/**
	 * 发送管道命令后关闭，返回管道连接期间若干命令执行后一系列的结果
	 * @author Daniele 
	 * @param connection
	 */
	private List<Object> closePipeline(RedisConnection connection) {
		try {
			return connection.closePipeline();
		} catch (Exception e) {
			logger.error("Redis pipeline closing error, cause:{}", e.getMessage());
			return null;
		}
	}

}
