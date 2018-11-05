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
 * Create Date : 2018年10月29日
 */

package org.sniper.generator.redis;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.keyspace.AbstractKeyspaceGeneratorGenerator;

/**
 * 基于Redis实现的序列生成器，生成的结果具备的特点：</P>
 * 1.同一个键维度内生成的数字是唯一的；</P>
 * 2.同一个键维度内生成的数字在单节点以及整个分布式环境中都是连续递增的。</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class RedisSequenceGenerator extends AbstractKeyspaceGeneratorGenerator<Object, Long> {
	
	private final RedisTrendSequence redisTrendSequence;
	
	public RedisSequenceGenerator(RedisTrendSequence redisTrendSequence) {
		super(redisTrendSequence.getDefaultKeyspace());
		this.redisTrendSequence = redisTrendSequence;
	}
	
	@Override
	protected Long doGenerateByKey(Object key) {
		return this.redisTrendSequence.updateByKey(key, 1);
	}

	@Override
	protected List<Long> doBatchGenerateByKey(Object key, int count) {
		// 当前批次起始值
		long batchStartValue = this.redisTrendSequence.updateByKey(key, count) - count;
		
		List<Long> list = CollectionUtils.newArrayList(count);
		for (int i = 0; i < count; i++) {
			list.add(++batchStartValue);
		}
		
		return list;
	}
	
}
