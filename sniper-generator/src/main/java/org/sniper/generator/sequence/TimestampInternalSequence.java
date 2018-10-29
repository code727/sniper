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
 * Create Date : 2018-10-24
 */

package org.sniper.generator.sequence;

/**
 * 时间戳内序列实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TimestampInternalSequence implements DimensionSequence<TimestampInternalSequence> {
	
	/** 序列掩码 */
	private final long sequenceMask;
	
	/** 时间戳序列 */
	private final TimestampSequence timestampSequence;
	
	/** 毫秒内序列，[0,sequenceMask]区间内 */
	protected long sequence;
	
	/** 最近生成的时间截 */
	private long lastTimestamp = -1L;
	
	public TimestampInternalSequence(long sequenceMask) {
		this.sequenceMask = sequenceMask;
		this.timestampSequence = new TimestampSequence();
	}
	
	/**
	 * 更新当前时间序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public TimestampInternalSequence update() {
		long currentTimestamp = this.timestampSequence.update();
		
        //如果当前时间小于最近生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (currentTimestamp < this.lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", 
					this.lastTimestamp - currentTimestamp));
        }

        /* 如果是同一时间生成的，则进行毫秒内序列 */
        if (this.lastTimestamp == currentTimestamp) {
        	this.nextSequence();
            if (this.getSequence() == 0) {
				// 阻塞到下一个毫秒，获得新的时间戳
            	currentTimestamp = nextMillis(this.lastTimestamp);
            }
        } else {
			// 时间戳改变，毫秒内序列重置
        	this.resetSequence();
        }

		// 更新最近生成的时间戳为当前时间戳
        this.setLastTimestamp(currentTimestamp);
        return this;
	}
	
	public long getSequence() {
		return sequence;
	}
	
	public long getLastTimestamp() {
		return lastTimestamp;
	}
	
	/**
	 * 生成时间戳内的下一序列
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected long nextSequence() {
		return this.sequence = (this.sequence + 1) & sequenceMask;
	}
	
	/**
	 * 重置时间戳内的序列计数
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	protected long resetSequence() {
		return this.sequence = 0;
	}
	
	protected void setLastTimestamp(long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	/**
	 * 生成下一个毫秒时间刻度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param lastTimestamp
	 * @return
	 */
	private long nextMillis(long lastTimestamp) {
        long timestamp = this.timestampSequence.update();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timestampSequence.update();
        }
        return timestamp;
    }
		
}
