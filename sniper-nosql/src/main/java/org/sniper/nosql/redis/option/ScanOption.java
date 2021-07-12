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
 * Create Date : 2019年3月11日
 */

package org.sniper.nosql.redis.option;

import java.io.Serializable;

import org.sniper.commons.util.AssertUtils;

/**
 * 增量迭代选项
 * @author  Daniele
 * @version 1.0
 */
public class ScanOption implements Serializable {
	
	private static final long serialVersionUID = 2425248451111070973L;

	/** 匹配模式 */
	private String pattern;
	
	/** 迭代个数 */
	private Long count;
	
	public String getPattern() {
		return pattern;
	}

	public ScanOption setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public Long getCount() {
		return count;
	}

	public ScanOption setCount(Long count) {
		if (count != null)
			AssertUtils.assertTrue(count != 0, "Scan count must not be 0");
		
		this.count = count;
		return this;
	}

}
