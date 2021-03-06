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
 * Create Date : 2019-3-14
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.Map;

/**
 * 有序集合增量迭代结果实现类
 * @author  Daniele
 * @version 1.0
 */
public class ZScanResult<M> extends AbstractMappedScanResult<M, Double> {
	
	public ZScanResult(long cursorId) {
		this(cursorId, null);
	}

	public ZScanResult(long cursorId, Map<M, Double> scoreMembers) {
		super(cursorId, scoreMembers);
	}
	
	@Override
	public String toString() {
		return String.format("{cursorId:%d,scoreMembers:%s}", this.cursorId, this.mapped);
	}

}
