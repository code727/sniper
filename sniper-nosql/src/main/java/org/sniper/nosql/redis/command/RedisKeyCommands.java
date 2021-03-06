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
 * Create Date : 2015-3-30
 */

package org.sniper.nosql.redis.command;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.sniper.nosql.redis.enums.DataType;
import org.sniper.nosql.redis.model.xscan.IndexedScanResult;
import org.sniper.nosql.redis.option.ScanOption;
import org.sniper.nosql.redis.option.SortOptional;

/**
 * Redis键命令接口
 * @author  Daniele
 * @version 1.0
 */
public interface RedisKeyCommands {
	
	/**
	 * 在当前库中执行keys命令，获取所有的键
	 * @author Daniele 
	 * @return
	 */
	public <K> Set<K> keys();
	
	/**
	 * 在当前库中执行keys命令，获取所有指定类型的键
	 * @author Daniele 
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keys(Class<K> keyType);
	
	/**
	 * 在指定库中执行keys命令，获取所有的键
	 * @author Daniele 
	 * @param dbName
	 * @return
	 */
	public <K> Set<K> keys(String dbName);
	
	/**
	 * 在指定库中执行keys命令，获取所有指定类型的键
	 * @author Daniele 
	 * @param dbName
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keys(String dbName, Class<K> keyType);
	
	/**
	 * 在当前库中执行keys命令，获取所有指定模式的键
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keysByPattern(String pattern);
	
	/**
	 * 在当前库中执行keys命令，获取所有指定模式和类型的键
	 * @author Daniele 
	 * @param pattern
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keysByPattern(String pattern, Class<K> keyType);
	
	/**
	 * 在指定库中执行keys命令，获取所有指定模式的键
	 * @author Daniele 
	 * @param dbName
	 * @param pattern
	 * @return
	 */
	public <K> Set<K> keysByPattern(String dbName, String pattern);
	
	/**
	 * 在指定库中执行keys命令，获取所有指定模式和类型的键
	 * @author Daniele 
	 * @param dbName
	 * @param pattern
	 * @param keyType
	 * @return
	 */
	public <K> Set<K> keysByPattern(String dbName, String pattern, Class<K> keyType);
	
	/**
	 * 在当前库中随机获取一个键
	 * @author Daniele 
	 * @return
	 */
	public <K> K randomKey();
	
	/**
	 * 在当前库中随机获取一个指定类型的键
	 * @author Daniele 
	 * @param keyType
	 * @return
	 */
	public <K> K randomKey(Class<K> keyType);
	
	/**
	 * 在指定库中随机获取一个键
	 * @author Daniele 
	 * @param dbName
	 * @return
	 */
	public <K> K randomKey(String dbName);
	
	/**
	 * 在指定库中随机获取一个指定类型的键
	 * @author Daniele 
	 * @param dbName
	 * @param keyType
	 * @return
	 */
	public <K> K randomKey(String dbName, Class<K> keyType);
		
	/**
	 * 删除当前库指定的键
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Long del(K key);
	
	/**
	 * 删除指定库的键
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long del(String dbName, K key);
	
	/**
	 * 删除当前库指定的多个键
	 * @author Daniele 
	 * @param keys
	 * @return
	 */
	public <K> Long del(Collection<K> keys);
	
	/**
	 * 删除指定库多个键
	 * @author Daniele 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long del(String dbName, Collection<K> keys);
	
	/**
	 * 删除当前库指定的多个键
	 * @author Daniele 
	 * @param keys
	 * @return
	 */
	public <K> Long del(K[] keys);
	
	/**
	 * 删除指定库多个键
	 * @author Daniele 
	 * @param dbName
	 * @param keys
	 * @return
	 */
	public <K> Long del(String dbName, K[] keys);
	
	/**
	 * 判断当前库中指定键是否存在
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(K key);
	
	/**
	 * 判断指定库中的键是否存在
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Boolean exists(String dbName, K key);
			
	/**
	 * 设置当前库中指定键的过期秒数
	 * @author Daniele 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(K key, long seconds);
	
	/**
	 * 设置指定库中键的过期秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param seconds
	 * @return
	 */
	public <K> Boolean expire(String dbName, K key, long seconds);
	
	/**
	 * 设置当前库中指定键的过期时间戳</P>
	 * 注意：参数unixTimestamp为Unix时间戳，即"毫秒时间戳/1000"后的值，
	 * 因此该命令只能达到"秒级过期"控制。如果要进行"毫秒级过期"控制，请使用pExpireAt命令
	 * @author Daniele 
	 * @param key
	 * @param unixTimestamp Unix时间戳
	 * @return
	 */
	public <K> Boolean expireAt(K key, long unixTimestamp); 
	
	/**
	 * 设置指定库中键的过期时间戳</P>
	 * 注意：参数unixTimestamp为Unix时间戳，即"毫秒时间戳/1000"后的值，
	 * 因此该命令只能满足"秒级过期"控制。如果要进行"毫秒级过期"控制，请使用pExpireAt命令
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param unixTimestamp Unix时间戳
	 * @return
	 */
	public <K> Boolean expireAt(String dbName, K key, long unixTimestamp); 
	
	/**
	 * 设置当前库中指定键的过期日期
	 * @author Daniele 
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(K key, Date date); 
	
	/**
	 * 设置指定库中键的过期日期
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean expireAt(String dbName, K key, Date date); 
	
	/**
	 * 设置当前库中指定键的过期毫秒数
	 * @author Daniele 
	 * @param key
	 * @param millis
	 * @return
	 */
	public <K> Boolean pExpire(K key, long millis);
	
	/**
	 * 设置指定库中键的过期毫秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param millis
	 * @return
	 */
	public <K> Boolean pExpire(String dbName, K key, long millis);
	
	/**
	 * 设置当前库中指定键的过期时间戳
	 * @author Daniele 
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean pExpireAt(K key, long timestamp); 
	
	/**
	 * 设置指定库中键的过期时间戳
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public <K> Boolean pExpireAt(String dbName, K key, long timestamp); 
	
	/**
	 * 设置当前库中指定键的过期日期
	 * @author Daniele 
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean pExpireAt(K key, Date date); 
	
	/**
	 * 设置指定库中键的过期时间日期
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param date
	 * @return
	 */
	public <K> Boolean pExpireAt(String dbName, K key, Date date);
	
	/**
	 * 在当前库中清除键的过期时间
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Boolean persist(K key); 
	
	/**
	 * 在指定库中清除键的过期时间
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Boolean persist(String dbName, K key); 
		
	/**
	 * 将当前库的键移动到目标库
	 * @author Daniele 
	 * @param key
	 * @param dbIndex 目标库索引
	 * @return
	 */
	public <K> Boolean move(K key, int dbIndex);
	
	/**
	 * 将指定库中的键移动到目标库
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param dbIndex 目标库索引
	 * @return
	 */
	public <K> Boolean move(String dbName, K key, int dbIndex);
	
	/**
	 * 获取当前库的指定键的剩余秒数
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Long ttl(K key);
	
	/**
	 * 获取指定库中的键的剩余秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long ttl(String dbName, K key);
	
	/**
	 * 获取当前库的指定键的剩余毫秒数
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> Long pTtl(K key);
	
	/**
	 * 获取指定库中的键的剩余毫秒数
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> Long pTtl(String dbName, K key);
	
	/**
	 * 获取当前库中所有键对应的值
	 * @author Daniele 
	 * @return
	 */
	public <V> List<V> values();
	
	/**
	 * 获取当前库中所有键对应类型的值
	 * @author Daniele 
	 * @param valueType
	 * @return
	 */
	public <V> List<V> values(Class<V> valueType);
	
	/**
	 * 获取指定库中所有键对应的值
	 * @author Daniele 
	 * @param dbName
	 * @return
	 */
	public <V> List<V> values(String dbName);
	
	/**
	 * 获取指定库中所有键对应类型的值
	 * @author Daniele 
	 * @param dbName
	 * @param valueType
	 * @return
	 */
	public <V> List<V> values(String dbName, Class<V> valueType);
	
	/**
	 * 获取当前库中匹配模式键对应的值
	 * @author Daniele 
	 * @param pattern
	 * @return
	 */
	public <V> List<V> valuesByPattern(String pattern);
	
	/**
	 * 获取当前库中匹配模式键对应类型的值
	 * @author Daniele 
	 * @param pattern
	 * @param valueType
	 * @return
	 */
	public <V> List<V> valuesByPattern(String pattern, Class<V> valueType);
	
	/**
	 * 获取指定库中匹配模式键对应的值
	 * @author Daniele 
	 * @param dbName
	 * @param pattern
	 * @return
	 */
	public <V> List<V> valuesByPattern(String dbName, String pattern);
	
	/**
	 * 获取指定库中匹配模式键对应类型的值
	 * @author Daniele 
	 * @param dbName
	 * @param pattern
	 * @param valueType
	 * @return
	 */
	public <V> List<V> valuesByPattern(String dbName, String pattern, Class<V> valueType);
	
	/**
	 * 在当前库中获取键的数据类型
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K> DataType type(K key);
	
	/**
	 * 在指定库中获取键的数据类型
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K> DataType type(String dbName, K key);
	
	/**
	 * 在当前库中执行sort命令，将键按默认规则进行排序后返回结果
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public <K, V> List<V> sort(K key);
	
	/**
	 * 在当前库中执行sort命令，将键按默认规则进行排序后返回指定类型的结果
	 * @author Daniele 
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> sort(K key, Class<V> valueType);
	
	/**
	 * 在指定库中执行sort命令，将键按默认规则进行排序后返回结果
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @return
	 */
	public <K, V> List<V> sort(String dbName, K key);
	
	/**
	 * 在指定库中执行sort命令，将键按默认规则进行排序后返回指定类型的结果
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> sort(String dbName, K key, Class<V> valueType);
	
	/**
	 * 在当前库中执行sort命令，将键按可选规则进行排序后返回结果
	 * @author Daniele 
	 * @param key
	 * @param optional
	 * @return
	 */
	public <K, V> List<V> sortByOptional(K key, SortOptional optional);
	
	/**
	 * 在当前库中执行sort命令，将键按可选规则进行排序后返回指定类型的结果
	 * @author Daniele 
	 * @param key
	 * @param optional
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> sortByOptional(K key, SortOptional optional, Class<V> valueType);
	
	/**
	 * 在指定库中执行sort命令，将键按可选规则进行排序后返回结果
	 * @author Daniele 
	 * @param dbNames
	 * @param key
	 * @param optional
	 * @return
	 */
	public <K, V> List<V> sortByOptional(String dbName, K key, SortOptional optional);
	
	/**
	 * 在指定库中执行sort命令，将键按可选规则进行排序后返回指定类型的结果
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param optional
	 * @param valueType
	 * @return
	 */
	public <K, V> List<V> sortByOptional(String dbName, K key, SortOptional optional, Class<V> valueType);
	
	/**
	 * 在当前库中执行sort命令，将键按默认规则进行排序，将排序结果存入目标键后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param key
	 * @param destKey
	 * @return
	 */
	public <K> Long sortStore(K key, K destKey);
	
	/**
	 * 在当前库中执行sort命令，将键按默认规则进行排序，将排序结果存入目标键并设置过期时间后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param key
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sortStore(K key, K destKey, long expireSeconds);
	
	/**
	 * 在指定库中执行sort命令，将键按默认规则进行排序，将排序结果存入目标键后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param destKey
	 * @return
	 */
	public <K> Long sortStoreIn(String dbName, K key, K destKey);
	
	/**
	 * 在指定库中执行sort命令，将键按默认规则进行排序，将排序结果存入目标键并设置过期时间后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sortStore(String dbName, K key, K destKey, long expireSeconds);
	
	/**
	 * 在当前库中执行sort命令，将键按可选规则进行排序，将排序结果存入目标键后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param key
	 * @param optional
	 * @param destKey
	 * @return
	 */
	public <K> Long sortStoreByOptional(K key, SortOptional optional, K destKey);
	
	/**
	 * 在当前库中执行sort命令，将键按可选规则进行排序，将排序结果存入目标键并设置过期时间后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param key
	 * @param optional
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sortStoreByOptional(K key, SortOptional optional, K destKey, long expireSeconds);
	
	/**
	 * 在指定库中执行sort命令，将键按可选规则进行排序，将排序结果存入目标键后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param optional
	 * @param destKey
	 * @return
	 */
	public <K> Long sortStoreByOptional(String dbName, K key, SortOptional optional, K destKey);
	
	/**
	 * 在当前库中执行sort命令，将键按可选规则进行排序，将排序结果存入目标键并设置过期时间后返回结果个数</p>
	 * 注意：此命令执行成功后，目标键中原有的值将全部会被覆盖掉
	 * @author Daniele 
	 * @param dbName
	 * @param key
	 * @param optional
	 * @param destKey
	 * @param expireSeconds
	 * @return
	 */
	public <K> Long sortStoreByOptional(String dbName, K key, SortOptional optional, K destKey, long expireSeconds);
	
	/**
	 * 在当前库中执行scan命令，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @return
	 */
	public <K> IndexedScanResult<K> scan();
	
	/**
	 * 在当前库中执行scan命令，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(Class<K> keyType);
	
	/**
	 * 在当前库中执行scan命令，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param option
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(ScanOption option);
	
	/**
	 * 在当前库中执行scan命令，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param option
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(ScanOption option, Class<K> keyType);
	
	/**
	 * 在当前库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param cursorId
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(long cursorId);
	
	/**
	 * 在当前库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param cursorId
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(long cursorId, Class<K> keyType);
	
	/**
	 * 在当前库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(long cursorId, ScanOption option);
	
	/**
	 * 在当前库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param cursorId
	 * @param option
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(long cursorId, ScanOption option, Class<K> keyType);
	
	/**
	 * 在指定库中执行scan命令，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param dbName
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName);
	
	/**
	 * 在指定库中执行scan命令，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, Class<K> keyType);
	
	/**
	 * 在指定库中执行scan命令，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param option
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, ScanOption option);
	
	/**
	 * 在指定库中执行scan命令，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param option
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, ScanOption option, Class<K> keyType);
	
	/**
	 * 在指定库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param cursorId
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, long cursorId);
	
	/**
	 * 在指定库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param cursorId
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, long cursorId, Class<K> keyType);
	
	/**
	 * 在指定库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取到所有的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param cursorId
	 * @param option
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, long cursorId, ScanOption option);
	
	/**
	 * 在指定库中执行scan命令，从指定的游标处开始，以增量迭代的方式获取所有指定类型的键结果
	 * @author Daniele 
	 * @param dbName
	 * @param cursorId
	 * @param option
	 * @param keyType
	 * @return
	 */
	public <K> IndexedScanResult<K> scan(String dbName, long cursorId, ScanOption option, Class<K> keyType);
	
}
