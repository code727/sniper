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
 * Create Date : 2019-3-12
 */

package org.sniper.nosql.redis.model.xscan;

/**
 * 可扫描的结果抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractScannableResult<T> implements ScannableResult<T> {
	
	/** 当前游标ID */
	protected final long cursorId;
		
	protected AbstractScannableResult(long cursorId) {
		this.cursorId = cursorId;
	}

	@Override
	public long getCursorId() {
		return cursorId;
	}

	@Override
	public boolean completed() {
		return this.cursorId == 0;
	}
	
	@Override
	public boolean isNotEmpty() {
		return !isEmpty();
	}
	
}
