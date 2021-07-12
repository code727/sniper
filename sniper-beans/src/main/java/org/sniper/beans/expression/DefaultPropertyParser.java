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
 * Create Date : 2018年12月14日
 */

package org.sniper.beans.expression;

import org.sniper.commons.util.StringUtils;

/**
 * 属性解析器默认实现类
 * @author  Daniele
 * @version 1.0
 */
public class DefaultPropertyParser implements PropertyParser {
	
	@Override
	public boolean hasNested(String expression) {
		return StringUtils.contains(expression, NESTED);
	}

	@Override
	public String resolve(String expression) {
		return StringUtils.beforeFrist(expression, NESTED);
	}

	@Override
	public String next(String expression) {
		return StringUtils.afterFrist(expression, NESTED);
	}
	
}
