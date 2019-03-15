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
 * Create Date : 2019-3-11
 */

package org.sniper.nosql.redis.model.xscan;

import java.util.List;

/**
 * 键增量迭代结果实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ScanResult<K> extends AbstractIndexedScanResult<K>{
	
	public ScanResult(long cursorId) {
		this(cursorId, null);
	}
	
	public ScanResult(long cursorId, List<K> keys) {
		super(cursorId, keys);
	}
	
	@Override
	public String toString() {
		return String.format("{cursorId:%d,keys:%s}", this.cursorId, this.items);
	}
	
}
