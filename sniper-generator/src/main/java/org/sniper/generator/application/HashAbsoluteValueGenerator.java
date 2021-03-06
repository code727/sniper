/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-10-22
 */

package org.sniper.generator.application;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.generator.AbstractParameterizeGenerator;

/**
 * 哈希绝对值生成器实现类
 * @author  Daniele
 * @version 1.0
 */
public class HashAbsoluteValueGenerator extends AbstractParameterizeGenerator<Object, Integer> {

	@Override
	public Integer generateByParameter(Object parameter) {
		return Math.abs(ObjectUtils.hashCode(parameter));
	}

	@Override
	public List<Integer> batchGenerateByParameter(Object parameter, int count) {
		checkBatchCount(count);
		
		List<Integer> results = CollectionUtils.newArrayList(count);
		Integer element = this.generateByParameter(parameter);
		for (int i = 0; i < count; i++) {
			results.add(element);
		}
		
		return results;
	}
	
}
