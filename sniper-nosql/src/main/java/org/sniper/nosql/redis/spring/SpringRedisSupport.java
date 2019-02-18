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

import org.sniper.beans.PropertyConverter;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ReflectionUtils;
import org.sniper.nosql.redis.RedisRepository;
import org.sniper.nosql.redis.command.RedisSupport;
import org.sniper.nosql.redis.enums.ListPosition;
import org.sniper.nosql.redis.enums.ZStoreAggregate;
import org.sniper.nosql.redis.model.DefaultZSetTuple;
import org.sniper.nosql.redis.model.ZSetTuple;
import org.sniper.nosql.redis.option.Limit;
import org.sniper.nosql.redis.option.SortOptional;
import org.sniper.nosql.redis.serializer.SpringRedisSerializerProxy;
import org.sniper.serialization.Serializer;
import org.sniper.serialization.TypedSerializer;
import org.springframework.data.redis.connection.DefaultSortParameters;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisListCommands.Position;
import org.springframework.data.redis.connection.RedisZSetCommands.Aggregate;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * SpringRedis支持类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
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
					? new SpringRedisSerializerProxy(redisKeySerializer) : getGlobalDefaultSerializer());
		}
		
		if (getGlobalValueSerializer() == null) {
			RedisSerializer<?> redisValueSerializer = this.redisTemplate.getValueSerializer();
			setGlobalValueSerializer(redisValueSerializer != null 
					? new SpringRedisSerializerProxy(redisValueSerializer) : getGlobalDefaultSerializer());
		}
		
		if (getGlobalHashKeySerializer() == null) {
			RedisSerializer<?> redisHashKeySerializer = this.redisTemplate.getHashKeySerializer();
			setGlobalHashKeySerializer(redisHashKeySerializer != null
					? new SpringRedisSerializerProxy(redisHashKeySerializer) : getGlobalDefaultSerializer());
		}
		
		if (getGlobalHashValueSerializer() == null) {
			RedisSerializer<?> redisHashValueSerializer = this.redisTemplate.getHashValueSerializer();
			setGlobalHashValueSerializer(redisHashValueSerializer != null
					? new SpringRedisSerializerProxy(redisHashValueSerializer) : getGlobalDefaultSerializer());
		}
	}
	
	/**
	 * 选择并连接库
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param position
	 * @return
	 */
	protected Position toPosition(ListPosition position) {
		return position == ListPosition.BEFORE ? Position.BEFORE : Position.AFTER;
	}
	
	/**
	 * 将有序集合聚合方式枚举转换为Spring的Aggregate对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param aggregate
	 * @return
	 */
	protected Aggregate toAggregate(ZStoreAggregate aggregate) {
		return aggregate != null ? Aggregate.valueOf(aggregate.name()) : Aggregate.MAX;
	}
	
	/**
	 * 将排序可选项转换为Spring的SortParameters对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * 序列化带排名值的成员
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
					V member = PropertyConverter.converte(propertyEditor, valueSerializer.deserialize(tuple.getValue()));
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
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param connection
	 */
	private void openPipeline(RedisConnection connection) {
		if (!connection.isPipelined())
			connection.openPipeline();
	}
	
	/**
	 * 发送管道命令后关闭，返回管道连接期间若干命令执行后一系列的结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
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
