/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-3-13
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.Map;

/**
 * 哈希增量迭代结果实现类
 * @author  Daniele
 * @version 1.0
 */
public class HScanResult<HK, HV> extends AbstractMappedScanResult<HK, HV> {

	public HScanResult(long cursorId) {
		this(cursorId, null);
	}

	public HScanResult(long cursorId, Map<HK, HV> hashKeyValues) {
		super(cursorId, hashKeyValues);
	}

	@Override
	public String toString() {
		return String.format("{cursorId:%d,hashKeyValues:%s}", this.cursorId, this.mapped);
	}
}
