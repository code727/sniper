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
 * Create Date : 2017年11月14日
 */

package org.sniper.generator;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.dimension.DimensionGenerator;

/**
 * 数字生成器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractNumberGenerator extends AbstractGenerator {
	
	private static final Map<Integer, DecimalFormat> decimalFormats = MapUtils.newConcurrentHashMap();
	
	protected AbstractNumberGenerator() {
		super();
	}
	
	protected AbstractNumberGenerator(int minLength) {
		super(minLength);
	}
	
	protected AbstractNumberGenerator(DimensionGenerator<?> dimensionGenerator) {
		super(dimensionGenerator);
	}
	
	protected AbstractNumberGenerator(int minLength, DimensionGenerator<?> dimensionGenerator) {
		super(minLength, dimensionGenerator);
	}
	
	/**
	 * 获取指定长度位数的补偿数字格式化对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param size
	 * @return
	 */
	protected static DecimalFormat getMakeupDecimalFormat(Integer length) {
		if (length == null || length <=0)
			return null;
		
		DecimalFormat decimalFormat = decimalFormats.get(length);
		if (decimalFormat == null) {
			/* 创建指定长度位数的数字格式化对象 */
			decimalFormat = new DecimalFormat(StringUtils.leftSupplement(StringUtils.EMPTY, '0', length));
			decimalFormats.put(length, decimalFormat);
		}
		
		return decimalFormat;
	}

	@Override
	protected String makeupGenerate(String generatedString, int offset) {
		DecimalFormat decimalFormat = getMakeupDecimalFormat(generatedString.length() + offset);
		
		// 要求generatedString参数必须为字符串类型的数字，否则会抛出IllegalArgumentException
		return decimalFormat != null ? decimalFormat.format(new BigInteger(generatedString)) : generatedString;
	}

}
