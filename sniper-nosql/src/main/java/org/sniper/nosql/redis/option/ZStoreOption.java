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
 * Create Date : 2019-1-17
 */

package org.sniper.nosql.redis.option;

import java.util.Arrays;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.AssertUtils;
import org.sniper.nosql.redis.enums.ZStoreAggregate;

/**
 * 有序集合存储选项
 * @author  Daniele
 * @version 1.0
 */
public final class ZStoreOption implements ZStoreOptional {

	/** 乘法因子数组 */
	private final int[] weights;

	/** 结果集的聚合方式 */
	private final ZStoreAggregate aggregate;

	private ZStoreOption(int[] weights, ZStoreAggregate aggregate) {
		AssertUtils.assertTrue(ArrayUtils.isNotEmpty(weights), "Weights must not be empty");
		this.weights = weights;
		this.aggregate = (aggregate != null ? aggregate : DEFAULT_AGGREGATE);
	}

	@Override
	public int[] getWeights() {
		return weights;
	}

	@Override
	public ZStoreAggregate getAggregate() {
		return aggregate;
	}

	@Override
	public String toString() {
		return String.format("WEIGHTS %s AGGREGATE %s", Arrays.toString(this.weights), this.aggregate);
	}

	/**
	 * 根据键来构建有序集合的存储选项
	 * @author Daniele 
	 * @param keys
	 * @return
	 */
	public static <K> ZStoreOption build(K[] keys) {
		return build(keys, null, null);
	}

	/**
	 * 根据键和聚合方式枚举来构建有序集合的存储选项
	 * @author Daniele 
	 * @param keys
	 * @param aggregate
	 * @return
	 */
	public static <K> ZStoreOption build(K[] keys, ZStoreAggregate aggregate) {
		return build(keys, null, aggregate);
	}

	/**
	 * 根据键和乘法因子数组来构建有序集合的存储选项
	 * @author Daniele 
	 * @param keys
	 * @param weights
	 * @return
	 */
	public static <K> ZStoreOption build(K[] keys, int[] weights) {
		return build(keys, weights, null);
	}

	/**
	 * 根据键、乘法因子数组和聚合方式枚举来构建有序集合的存储选项
	 * @author Daniele 
	 * @param keys
	 * @param weights
	 * @param aggregate
	 * @return
	 */
	public static <K> ZStoreOption build(K[] keys, int[] weights, ZStoreAggregate aggregate) {
		AssertUtils.assertNotEmpty(keys, "keys must not be empty");
		
		int keysLength = keys.length;
		/* 如果乘法因子为空，则构建与键相等个数的乘法因子数组 */
		if (ArrayUtils.isEmpty(weights)) {
			weights = new int[keysLength];
			for (int i = 0; i < keysLength; i++) {
				weights[i] = DEFAULT_WEIGHT;
			}
		} else {
			int weightsLength = weights.length;
			/* 如果乘法因子个数与键个数不相等，则需要抛弃多余的或补充"keysLength-weightsLength"个默认值 */
			if (weightsLength != keysLength) {
				weights = Arrays.copyOf(weights, keysLength);
				if (weightsLength < keysLength) {
					for (int i = weightsLength; i < keysLength; i++) {
						weights[i] = DEFAULT_WEIGHT;
					}
				}
			}
		}

		return new ZStoreOption(weights, aggregate);
	}
	
}
