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
 * Create Date : 2017-11-13
 */

package org.sniper.generator.dimension;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.DateUtils;
import org.sniper.commons.util.MapUtils;

/**
 * 日期维度生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DateDimensionGenerator implements DimensionGenerator<String> {
	
	/** 时间维度与格式的映射关系 */
	private static final Map<Integer, String> patterns;
	
	private final String pattern;
	
	static {
		patterns = MapUtils.newHashMap();
		
		/* 年 */
		patterns.put(0, "yyyy");
		/* 月 */
		patterns.put(1, "yyyyMM");
		/* 日 */
		patterns.put(2, "yyyyMMdd");
		
		/* 时 */
		patterns.put(3, "yyyyMMddHH");
		/* 分 */
		patterns.put(4, "yyyyMMddHHmm");
		/* 秒 */
		patterns.put(5, "yyyyMMddHHmmss");
		/* 毫秒 */
		patterns.put(6, "yyyyMMddHHmmssSSS");
	}
	
	public DateDimensionGenerator() {
		// 默认以"日"的维度
		this(2); 
	}
	
	public DateDimensionGenerator(int timeDimension) {
		String pattern = patterns.get(timeDimension);
		AssertUtils.assertNotNull(pattern, MessageFormat.format(
				"Invalid time dimension \"{0}\",must in interval[{1},{2}]", timeDimension, 0, patterns.size() - 1));
		this.pattern = pattern;
	}

	@Override
	public String create() {
		return DateUtils.dateToString(new Date(), this.pattern);
	}
	
}
