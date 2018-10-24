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
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HashAbsoluteValueGenerator extends AbstractParameterizeGenerator<Object, Integer> {

	@Override
	public Integer generate(Object parameter) {
		return Math.abs(ObjectUtils.hashCode(parameter));
	}

	@Override
	protected List<Integer> doBatchGenerate(Object parameter, int count) {
		List<Integer> results = CollectionUtils.newArrayList(count);
		Integer element = this.generate(parameter);
		for (int i = 0; i < count; i++) {
			results.add(element);
		}
		
		return results;
	}
	
}