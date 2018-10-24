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
import org.sniper.generator.Generator;

/**
 * 推特Snowflake算法生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractSnowflakeGenerator<T> extends AbstractGenerator<T> implements Generator<T> {
	
	/** 相对的开始时间截，越接近当前时间，最终生成的数字结果越小 */
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
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	interface SequenceGenerator<T> {

		/** 
		 * 根据时间序列生成结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param timeSequence
		 * @return 
		 */
		public T generate(TimeSequence timeSequence);
	}
	
	/**
	 * 默认的Snowflake序列生成器实现类，其生成规则为：</P>
	 * 1.得到"最近生成的时间截"与"相对的开始时间截"的时间差再左移(22位)；</P>
	 * 2.根据左移结果再依次与数据中心ID左移(17位)结果、服务器ID左移(12位)结果、当前毫秒内的序列号进行逻辑"或"运算后得到一个64位的序列结果。</P>
	 * 具体生成规则请参考<a>https://segmentfault.com/a/1190000011282426</a></P>
	 * 此实现类生成的结果具备如下特点：</P>
	 * 1)生成的数字是全局唯一的；</P>
	 * 2)在单节点单线程环境中生成的数字是趋势递增的；</P>
	 * 3)在单节点多线程环境中，由于共用的是同一时钟，因此当线程交替执行时，也能保证生成的数字是趋势递增的，即后执行的线程生成的数字一定比先执行的线程生成的数字大</P>
	 * 4)在其余环境(多节点单线程/多节点多线程)中，由于很难保证有一个全局同步的时钟，因此不能保证先后生成的数字是趋势递增的</P>
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class DefaultSequenceGenerator implements SequenceGenerator<Long> {

		/**
		 * 通过移位并进行"或"运算后拼到一起组成的64位序列结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param timeSequence
		 * @return 
		 */
		@Override
		public Long generate(TimeSequence timeSequence) {
			return ((timeSequence.getLastTimestamp() - twepoch) << timestampLeftShift)
					| (dataCenterId << datacenterIdShift) | (workerId << workerIdShift) | timeSequence.getSequence();
		}
	}
	
	/**
	 * 自定义的Snowflake序列生成器实现类，其生成规则为：</P>
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class CustomizeSequenceGenerator implements SequenceGenerator<String> {
		
		@Override
		public String generate(TimeSequence timeSequence) {
			System.out.println(timeSequence.getSequence());
			return String.valueOf(timeSequence.getLastTimestamp() - twepoch)
					+ NumberUtils.format(timeSequence.getSequence(), 4);
		}
	}
		
}
