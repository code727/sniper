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
 * Create Date : 2017-4-19
 */

package org.sniper.sharding;

import org.sniper.commons.util.AssertUtils;

/**
 * 分片器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSharding<T> implements Shardable<T> {
	
	/** 是否允许分片参数为空 */
	private boolean allowNull;

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	@Override
	public <P> T sharding(P parameter) {
		T shardingEntity = buildShardingEntity();
		sharding(parameter, shardingEntity);
		return shardingEntity;
	}

	@Override
	public <P> void sharding(P parameter, T shardingEntity) {
		checkParameter(parameter);
		checkShardingEntity(shardingEntity);
		doSharding(parameter, shardingEntity);
	}
	
	/**
	 * 检查路由参数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @throws IllegalArgumentException
	 */
	protected <P> void checkParameter(P parameter) throws IllegalArgumentException {
		if (!allowNull && parameter == null)
			// 当不允许为空而参数为空时，则抛出IllegalArgumentException
			throw new IllegalArgumentException("Sharded parameter must not be null");
	}
	
	/**
	 * 检查分片实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param shardingEntity
	 * @throws IllegalArgumentException
	 */
	protected void checkShardingEntity(T shardingEntity) throws IllegalArgumentException {
		// 强制要求指定的切分目标不能为空
		AssertUtils.assertNotNull(shardingEntity, "Sharding entity must not be null");
	}
	
	/**
	 * 构建分片实体
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected abstract T buildShardingEntity();
	
	/** 
	 * 分片处理
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @param shardingEntity 
	 */
	protected abstract <P> void doSharding(P parameter, T shardingEntity);
		
}
