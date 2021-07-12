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

package org.sniper.generator.snowflake;

import java.util.List;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.ParameterizeGenerator;
import org.sniper.generator.application.ShortLinkGenerator;
import org.sniper.generator.sequence.SequenceNode;
import org.sniper.generator.sequence.TimestampInternalRadomSequence;
import org.sniper.generator.sequence.TimestampInternalSequence;

/**
 * Snowflake参数化序列生成器实现类，算法核心思想为：</p>
 * 1.时间序列部分采用的是内部自定义的CustomizeSequenceGenerator实现类来生成的，与DefaultSequenceGenerato不同的是，
 * 数据中心和服务器ID并未参与计算，而是由"最近生成的时间截"与"相对的开始时间截"的时间差,以及"当前毫秒内的序列号"进行拼接而成的。</p>
 * 2.其中当前毫秒内的序列号采用的是TimestampInternalRadomSequence实现类生成的，与默认的TimestampInternalSequence不同的是，
 * TimestampInternalRadomSequence在重置时，不会像TimeSequence那样直接重置为0，而是重置成[0,4095]区间内任意一个随机数，这样做的目的有如下两点：</p>
 * 1)避免每个毫秒时刻内生成的第一笔序列，其末尾的序列号都是从0开始的。例如：假设这些序列用来做哈希散列存储，并且系统在大多数情况下每毫秒就只生成了一笔，
 *   则这些数据将全部映射到同一节点上，哈希散列将失去意义。而如果用随机数来重置，则可以使哈希散列的更均匀一些；</p>
 * 2)通过与后续的参数处理结果拼接，尽可能的降低在分布式环境下重复的概率。</p>
 * 3.内部通过ParameterizeGenerator接口类来对参数进行处理，默认使用的ShortLinkGenerator生成24位的纯数字后再截取最后两位(固定两位，不足向前补0)而得。</p>
 * 4.将第1步和第3步的结果进行拼接就是最终的结果，例如：2142664137405042"</p>
 * 1)"2142664137":"最近生成的时间截"与"相对的开始时间截"的时间差；</p>
 * 2)"4050":"当前毫秒内的序列号"；</p>
 * 3)"42":业务参数的值经过ParameterizeGenerator处理后截取的值。</p>
 * 5.具体生成规则和结果特点与内部使用的CustomizeSequenceGenerator一致。</p>
 * @author  Daniele
 * @version 1.0
 */
public class SnowflakeParameterizeGenerator extends AbstractSnowflakeGenerator<Long>
		implements ParameterizeGenerator<Object, Long> {
	
	private final SequenceGenerator<String> sequenceGenerator;
		
	private final TimestampInternalSequence timeSequence;
	
	private final ParameterizeGenerator<Object, ?> parameterGenerator;
	
	public SnowflakeParameterizeGenerator() {
		this((ParameterizeGenerator<Object, ?>) null);
	}
	
	public SnowflakeParameterizeGenerator(SequenceNode sequenceNode) {
		this(sequenceNode, null);
	}
	
	public SnowflakeParameterizeGenerator(ParameterizeGenerator<Object, ?> parameterGenerator) {
		this(new SequenceNode(), parameterGenerator);
	}
	
	public SnowflakeParameterizeGenerator(SequenceNode sequenceNode, ParameterizeGenerator<Object, ?> parameterGenerator) {
		super(sequenceNode);
		this.sequenceGenerator = new CustomizeSequenceGenerator();
		this.timeSequence = new TimestampInternalRadomSequence(sequenceMask);
		this.parameterGenerator = (parameterGenerator != null ? parameterGenerator : new ShortLinkGenerator(true));
	}
	
	@Override
	public Long generate() {
		return generateByParameter(null);
	}
			
	@Override
	public synchronized Long generateByParameter(Object parameter) {
		return Long.valueOf(sequenceGenerator.generate(this.timeSequence.update()) + generateParameterResult(parameter));
	}
	
	@Override
	public List<Long> batchGenerate(int count) {
		return batchGenerateByParameter(null, count);
	}

	@Override
	public synchronized List<Long> batchGenerateByParameter(Object parameter, int count) {
		checkBatchCount(count);
		
		List<Long> results = CollectionUtils.newArrayList(count);
		String parameterResult = generateParameterResult(parameter);
		for (int i = 0; i < count; i++) {
			results.add(Long.valueOf(sequenceGenerator.generate(this.timeSequence.update()) + parameterResult));
		}
		
		return results;
	}
		
	/**
	 * 生成参数结果
	 * @author Daniele 
	 * @param parameter
	 * @return
	 */
	protected String generateParameterResult(Object parameter) {
		/* 将生成值的最后2位作为序列号作为返回，不足2位时在结果的前面补0 */
		String generatedValue = StringUtils.substringEnd(parameterGenerator.generateByParameter(parameter).toString(), 2);
		return StringUtils.beforeSupplement(generatedValue, 2, '0');
	}

}
