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
 * Create Date : 2017-11-14
 */

package org.sniper.generator;

import java.math.BigInteger;

import org.sniper.commons.util.NumberUtils;

/**
 * 参数化数字生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractParameterizeNumberGenerator<T> extends AbstractDimensionParameterizeGenerator<T> {
	
	@Override
	protected String makeupGenerate(String generated, int offset) {
		// 要求generated参数必须为字符串类型的数字，否则会抛出IllegalArgumentException
		return NumberUtils.format(new BigInteger(generated), generated.length() + offset);
	}

}
