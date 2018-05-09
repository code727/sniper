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
 * Create Date : 2017-11-27
 */

package org.sniper.generator.application;

import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.AbstractParameterizeNumberGenerator;
import org.sniper.generator.dimension.TimeStampDimensionGenerator;

/**
 * 自定义生成器实现类，生成结果满足几点要求：</P>
 * 1.每一个独立的参数在指定的维度刻度内（默认为毫秒级维度）是唯一的；</P>
 * 2.每一个独立的参数随着维度值的递增而趋势递增的。</P>
 * 此实现类可应用在用户下单的情况下，作为每个用户，
 * 传入各自唯一的标识参数后可保证在某一个时间刻度内（默认为毫秒级维度）生成的订单编号是唯一的
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CustomizeGenerator<P> extends AbstractParameterizeNumberGenerator<P, String> {
	
	private ShortLinkGenerator shortLinkGenerator;
	
	public CustomizeGenerator() {
		this.shortLinkGenerator = new ShortLinkGenerator(true);
		
		this.parameterAsDimensionKeyPrefix = false;
		this.dimensionGenerator = new TimeStampDimensionGenerator();
	}

	@Override
	protected String generateByKey(String key) {
		String result = shortLinkGenerator.generate(key);
		
//		int beginIndex = NumberUtils.randomIn(3) << 3;
//		return StringUtils.substring(result, beginIndex, beginIndex + 8);
		
		/* 由于短链接算法生成的是24位长的结果，因此这里将结果分为四组，
		 * 每组6位长度，然后在这四组中随机抽取出一组并且再加上四位随机数来作为最终结果 */
		int beginIndex = NumberUtils.randomIn(3) << 2;
		return StringUtils.substring(result, beginIndex, beginIndex + 6) + NumberUtils.rangeRandom(1000, 9999);
	}
		
}
