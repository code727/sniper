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

/**
 * 推特Snowflake序列生成器实现类，其结果为一个long型的数字。</p>
 * 其算法核心思想为：</p>
 * 1.使用41bit作为毫秒数，10bit作为服务节点的ID，其中5个bit的workerId为服务器ID，
 *   另外5个bit的dataCenterId为数据中心ID，因此最大可支持1024(32*32)个节点的部署</p>
 * 2.12bit作为毫秒内的流水号，这意味着每个节点在每个毫秒刻度内可以产生4096个不同的结果，最后还有一个符号位，永远是0</p>
 * 此实现类生成的结果具备如下特点：</P>
 * 1)生成的数字是全局唯一的；</P>
 * 2)在单节点单线程环境中生成的数字是趋势递增的；</P>
 * 3)在单节点多线程环境中，由于共用的是同一时钟，因此当线程交替执行时，
 *   也能保证生成的数字是趋势递增的，即后执行的线程生成的数字一定比先执行的线程生成的数字大</P>
 * 4)在其余环境(多节点单线程/多节点多线程)中，由于很难保证有一个全局同步的时钟，因此不能保证先后生成的数字是趋势递增的</P>
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SnowflakeGenerator extends AbstractSnowflakeGenerator<Number> {
	
	private final TimeSequence timeSequence;
	
	private final SequenceGenerator<Number> sequenceGenerator;
	
    public SnowflakeGenerator() {
    	this(new SequenceNode());
    }

    public SnowflakeGenerator(SequenceNode sequenceNode) {
		super(sequenceNode);
		this.timeSequence = new TimeSequence();
		this.sequenceGenerator = this.useTwepoch ? new TwepochSequenceGenerator() : new UntwepochSequenceGenerator();
    }
    
	@Override
	public synchronized Number generate() {
		return doGenerate();
	}

	@Override
	protected synchronized List<Number> doBatchGenerate(int count) {
		List<Number> results = CollectionUtils.newArrayList(count);
		for (int i = 0; i < count; i++) {
			results.add(doGenerate());
		}
		
		return results;
	}
	
	/**
	 * 执行生成操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private Number doGenerate() {
		long currentTimestamp = currentTimestamp();
		long lastTimestamp = timeSequence.getLastTimestamp();
		
        //如果当前时间小于最近生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (currentTimestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
        }

        /* 如果是同一时间生成的，则进行毫秒内序列 */
        if (lastTimestamp == currentTimestamp) {
        	timeSequence.nextSequence();
            if (timeSequence.getSequence() == 0) {
				// 阻塞到下一个毫秒，获得新的时间戳
            	currentTimestamp = nextMillis(lastTimestamp);
            }
        } else {
			// 时间戳改变，毫秒内序列重置
        	timeSequence.resetSequence();
        }

		// 更新最近生成的时间戳为当前时间戳
        timeSequence.setLastTimestamp(currentTimestamp);

        // 移位并通过"或"运算拼到一起组成64位的序列结果
		return sequenceGenerator.generate(currentTimestamp, timeSequence);
	}
	
}
