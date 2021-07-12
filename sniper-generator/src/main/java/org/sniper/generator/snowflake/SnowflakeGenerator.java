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
 * Create Date : 2017-11-6
 */

package org.sniper.generator.snowflake;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.generator.sequence.SequenceNode;
import org.sniper.generator.sequence.TimestampInternalSequence;

/**
 * 推特Snowflake序列生成器实现类，其结果为一个long型的数字。算法核心思想为：</p>
 * 1.使用41bit作为毫秒数，10bit作为服务节点的ID，其中5个bit的workerId为服务器ID，
 * 另外5个bit的dataCenterId为数据中心ID，因此最大可支持1024(32*32)个节点的部署</p>
 * 2.12bit作为毫秒内的流水号，这意味着每个节点在每个毫秒刻度内可以产生4096个不同的结果，最后还有一个符号位，永远是0。</p>
 * 3.理论上每秒中可同时生成4194304(1024*4096)个不同的结果。</p>
 * 4.具体实现原理请参考<a>https://segmentfault.com/a/1190000011282426</a></P>
 * 5.具体生成规则和结果特点与内部使用的DefaultSequenceGenerator一致。</p>
 * 
 * @author  Daniele
 * @version 1.0
 */
public class SnowflakeGenerator extends AbstractSnowflakeGenerator<Long> {
	
	private final SequenceGenerator<Long> sequenceGenerator;
	
	private final TimestampInternalSequence timeSequence;
	
    public SnowflakeGenerator() {
    	this(new SequenceNode());
    }

    public SnowflakeGenerator(SequenceNode sequenceNode) {
		super(sequenceNode);
		this.sequenceGenerator = new DefaultSequenceGenerator();
		this.timeSequence = new TimestampInternalSequence(sequenceMask);
    }
    
	@Override
	public synchronized Long generate() {
		return sequenceGenerator.generate(this.timeSequence.update());
	}

	@Override
	public synchronized List<Long> batchGenerate(int count) {
		checkBatchCount(count);
		List<Long> results = CollectionUtils.newArrayList(count);
		for (int i = 0; i < count; i++) {
			results.add(sequenceGenerator.generate(this.timeSequence.update()));
		}
		
		return results;
	}
					
}
