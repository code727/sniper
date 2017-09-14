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
 * Create Date : 2017-9-11
 */

package org.sniper.http.headers.request;

import java.util.Iterator;
import java.util.List;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.HttpHeaders;

/**
 * HTTP文件范围
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class Range {
	
	/** 单位 */
	private String unit;
	
	/** 范围值列表 */
	private List<RangeValue> rangeValues;
	
	public Range(List<RangeValue> rangeValues) {
		this(null, rangeValues);
	}
	
	public Range(String unit, List<RangeValue> rangeValues) {
		AssertUtils.assertTrue(CollectionUtils.isNotEmpty(rangeValues), "Range values must not be null or empty");
		
		this.unit = (StringUtils.isNotBlank(unit) ? unit : "bytes");
		this.rangeValues = rangeValues;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(unit).append(StringUtils.ASSIGNMENT);
		
		Iterator<RangeValue> iterator = rangeValues.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next());
			if (iterator.hasNext())
				builder.append(HttpHeaders.VALUE_SEPARATOR);
		}
		
		return builder.toString();
	}
	
}


