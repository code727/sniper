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
 * Create Date : 2017-9-8
 */

package org.sniper.http.headers;

import org.sniper.commons.util.NumberUtils;

/**
 * Keep-Alive消息头对象
 * @author  Daniele
 * @version 1.0
 */
public class KeepAlive {
	
	/** 保持打开状态的最小时长(单位:秒) */
	private long timeout;
	
	/** 在此连接可以发送的请求的最大值 */
	private int max;
	
	public KeepAlive() {
		this(5);
	}
	
	public KeepAlive(long timeout) {
		this(timeout, 1000);
	}
	
	public KeepAlive(long timeout, int max) {
		setTimeout(timeout);
		setMax(max);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = NumberUtils.minLimit(timeout, 1);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = NumberUtils.minLimit(max, 1);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("timeout=").append(timeout)
				.append(HttpHeaders.VALUE_SEPARATOR).append("max=").append(max).toString();
	}
		
}
