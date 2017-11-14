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

package org.sniper.generator;


import org.sniper.commons.util.AssertUtils;

/**
 * 推特Snowflake算法实现类结果是一个long型的ID。</p>
 * 其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID，支持32*32=1024个节点的部署），
 * 12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096个 ID），最后还有一个符号位，永远是0
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class SnowflakeGenerator implements Generator<Long> {
	
	/** 开始时间截  2017-01-01 00:00:00 */
    private final long twepoch = 1483200000000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long dataCenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << dataCenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    
    /** 毫秒内序列(0~4095) */
    private long sequence;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;
    
    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long dataCenterId;

    public SnowflakeGenerator(long workerId, long dataCenterId) {
		AssertUtils.assertTrue(workerId <= maxWorkerId || workerId >= 0,
				String.format("Parameter 'workerId' can not be greater than %d or less than 0", maxWorkerId));
		
		AssertUtils.assertTrue(dataCenterId <= maxDatacenterId || dataCenterId >= 0,
				String.format("Parameter 'dataCenterId' can not be greater than %d or less than 0", maxDatacenterId));
    	
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

	@Override
	public synchronized Long generate() {
		long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else
        	//时间戳改变，毫秒内序列重置
            sequence = 0;

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) | (dataCenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}
	
	protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
}
