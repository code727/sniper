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

import org.sniper.commons.util.NumberUtils;

/**
 * 时间戳内随机序列实现类
 * @author  Daniele
 * @version 1.0
 */
public class TimestampInternalRadomSequence extends TimestampInternalSequence {
	
	/** 随机种子*/
	private final long radomSeed;

	public TimestampInternalRadomSequence(long sequenceMask) {
		super(sequenceMask);
		this.radomSeed = ++sequenceMask;
	}
	
	@Override
	protected long resetSequence() {
		return this.sequence = NumberUtils.randomIn(this.radomSeed);
	}

}
