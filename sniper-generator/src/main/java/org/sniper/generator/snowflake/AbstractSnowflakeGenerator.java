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
 * Create Date : 2018-6-29
 */

package org.sniper.generator.snowflake;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.generator.AbstractGenerator;
import org.sniper.generator.sequence.SequenceNode;
import org.sniper.generator.sequence.TimestampInternalSequence;

/**
 * 推特Snowflake算法生成器抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractSnowflakeGenerator<T> extends AbstractGenerator<T> {
	
	/** 相对的开始时间截，一旦投入使用，不能再修改此值 */
	protected final long twepoch = DateUtils.stringToMillis("2018-10-01 00:00:00");
	
    /** 机器id所占的位数 */
	protected final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
	protected final long dataCenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
	protected final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
	protected final long maxDatacenterId = -1L ^ (-1L << dataCenterIdBits);

    /** 序列在id中占的位数 */
	protected final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
	protected final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
	protected final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(12+5+5) */
	protected final long timestampLeftShift = datacenterIdShift + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	protected final long sequenceMask = -1L ^ (-1L << sequenceBits);
    
    /** 服务器ID */
	protected final long workerId;

    /** 数据中心ID*/
	protected final long dataCenterId;
                
    protected AbstractSnowflakeGenerator(SequenceNode sequenceNode) {    	
    	AssertUtils.assertNotNull(sequenceNode, "Sequence node must not be null");

    	long workerId = sequenceNode.getWorkerId();
    	long dataCenterId = sequenceNode.getDataCenterId();

    	AssertUtils.assertTrue(workerId >= 0 && workerId <= maxWorkerId,
    			String.format("Parameter 'workerId' must within interval [0-%d]", maxWorkerId));

    	AssertUtils.assertTrue(dataCenterId >= 0 && dataCenterId <= maxDatacenterId,
    			String.format("Parameter 'dataCenterId' must within interval [0-%d]", maxDatacenterId));

    	this.workerId = workerId;
    	this.dataCenterId = dataCenterId;
    }
        
	/**
	 * 序列生成器接口
	 * @author  Daniele
	 * @version 1.0
	 */
	protected interface SequenceGenerator<T> {

		/** 
		 * 根据时间序列生成结果
		 * @author Daniele 
		 * @param timeSequence
		 * @return 
		 */
		public T generate(TimestampInternalSequence timeSequence);
	}
	
	/**
	 * 默认的Snowflake序列生成器实现类，其生成规则为：</P>
	 * 1.得到"最近生成的时间截"与"相对的开始时间截"的时间差再左移(22位)；</P>
	 * 2.根据左移结果再依次与数据中心ID左移(17位)结果、服务器ID左移(12位)结果、当前毫秒内的序列号进行逻辑"或"运算后得到一个64位的序列结果。</P>
	 * 此实现类生成的结果具备如下特点：</P>
	 * 1)由于有数据中心和服务器ID的参与，因此能保证生成的数字是全局唯一的；</P>
	 * 2)在单节点环境中，由于共用的是同一时钟，因此当多个线程交替执行时，也能保证生成的数字是趋势递增的，即后执行的线程生成的数字一定比先执行的线程生成的数字大</P>
	 * 3)在分布式环境中，由于多个节点之间很难保证有一个全局同步的时钟，因此不能保证先后生成的数字是趋势递增的</P>
	 * @author  Daniele
	 * @version 1.0
	 */
	class DefaultSequenceGenerator implements SequenceGenerator<Long> {

		@Override
		public Long generate(TimestampInternalSequence timeSequence) {
			return ((timeSequence.getLastTimestamp() - twepoch) << timestampLeftShift)
					| (dataCenterId << datacenterIdShift) | (workerId << workerIdShift) | timeSequence.getSequence();
		}
	}
	
	/**
	 * 自定义的Snowflake序列生成器实现类，其生成规则为：</P>
	 * 1.得到"最近生成的时间截"与"相对的开始时间截"的时间差；</P>
	 * 2.将时间差与"当前毫秒内的序列号"进行拼接后得到序列结果；</P>
	 * 3.每一毫秒内的序列号范围在[0-4095]区间内，因此序列结果最后固定的4位为毫秒内的序列号。</P>
	 * 此实现类生成的结果具备如下特点：</P>
	 * 1)在单节点环境中能保证生成的数字是唯一的，并且保持趋势递增性；</P>
	 * 2)在分布式环境中，由于多个节点之间很难保证有一个全局同步的时钟，同时无数据中心和服务器ID的参与，因此生成的数字可能会有重复。</P>
	 * 例如：A节点比B节点快1ms，A节点先生成序列结果，过了1ms后，B节点也生成了序列结果，
	 * 此时A和B的结果中时间差值部分是相同的，而毫秒内的序列号取决于两个节点的执行时刻，因此有可能重复。
	 * 最极端情况下，A和B节点在同一毫秒内都生成了超过2048个结果，则必然会有重复。
	 * 3)同理，由于分布式环境时钟同步问题，并不能保证多个节点先后生成的数字是趋势递增的。</P>
	 * @author  Daniele
	 * @version 1.0
	 */
	class CustomizeSequenceGenerator implements SequenceGenerator<String> {
		
		@Override
		public String generate(TimestampInternalSequence timeSequence) {
			return String.valueOf(timeSequence.getLastTimestamp() - twepoch)
					+ NumberUtils.format(timeSequence.getSequence(), 4);
		}
	}
			
}
