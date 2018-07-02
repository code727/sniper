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

import java.math.BigInteger;
import java.util.Map;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.generator.ParameterizeGenerator;
import org.sniper.generator.application.ShortLinkGenerator;
import org.sniper.lock.ParameterizeLock;
import org.sniper.lock.jdk.JdkParameterizeLock;

/**
 * 自定义生成器实现类，生成结果满足几点要求：</P>
 * 1.每一个独立的参数在指定的维度刻度内（默认为毫秒级维度）是唯一的；</P>
 * 2.每一个独立的参数随着维度值的递增而趋势递增的。</P>
 * 此实现类可应用在用户下单的情况下，作为每个用户，
 * 传入各自唯一的标识参数后可保证在某一个时间刻度内（默认为毫秒级维度）生成的订单编号是唯一的
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterizeSnowflakeGenerator extends AbstractSnowflakeGenerator<Number>
		implements ParameterizeGenerator<Object, Number> {

	private final ParameterizeLock<Object> lock;
	
	private final Map<Object, TimeSequence> timeSequences;

	private final ParameterizeSequenceGenerator<Number> sequenceGenerator;

	public ParameterizeSnowflakeGenerator() {
		this((ParameterizeLock<Object>) null);
	}

	public ParameterizeSnowflakeGenerator(ParameterizeSequenceNode sequenceNode) {
		this(sequenceNode, null);
	}

	public ParameterizeSnowflakeGenerator(ParameterizeLock<Object> lock) {
		this(new ParameterizeSequenceNode(), lock);
	}
	
	public ParameterizeSnowflakeGenerator(ParameterizeSequenceNode sequenceNode, ParameterizeLock<Object> lock) {
		super(sequenceNode);
		
		this.lock = (lock != null ? lock : new JdkParameterizeLock<Object>());
		this.timeSequences = MapUtils.newConcurrentHashMap();
		this.sequenceGenerator = createParameterizeSequenceGenerator(sequenceNode);
	}
	
	/**
	 * 根据参数化序列节点创建对应的参数化序列生成器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param sequenceNode
	 * @return
	 */
	private ParameterizeSequenceGenerator<Number> createParameterizeSequenceGenerator(ParameterizeSequenceNode sequenceNode) {
		if (sequenceNode.isParameterAsResult()) {
			return sequenceNode.isUseTwepoch()
					? new DefaultParameterizeSequenceGenerator(new TwepochSequenceGenerator())
					: new DefaultParameterizeSequenceGenerator(new UntwepochSequenceGenerator());
		}

		return sequenceNode.isUseTwepoch() ? new TwepochUnparameterizeSequenceGenerator()
				: new UntwepochUnparameterizeSequenceGenerator();
	}
	
	@Override
	public Number generate() {
		return generate(null);
	}

	@Override
	public Number generate(Object parameter) {
		return generateByParameter(parameter != null ? parameter : StringUtils.EMPTY);
	}

	/**
	 * 根据参数来生成结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	protected Number generateByParameter(Object parameter) {
		TimeSequence timeSequence = timeSequences.get(parameter);
		if (timeSequence == null) {
			lock.lock(parameter);
			try {
				// 双重检查，防止多线程环境针对同一参数同时创建多个时间序列
				if ((timeSequence = timeSequences.get(parameter)) == null) {
					timeSequence = new TimeSequence();
					timeSequences.put(parameter, timeSequence);
				}
			} finally {
				lock.unlock(parameter);
			}
		}

		lock.lock(parameter);
		try {
			long currentTimestamp = currentTimestamp();
			long lastTimestamp = timeSequence.getLastTimestamp();

			// 如果当前时间小于序列最近生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
			if (currentTimestamp < lastTimestamp) {
				throw new RuntimeException(String.format(
						"Clock moved backwards.Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
			}

			/* 如果是同一时间生成的，则进行毫秒内序列 */
			if (lastTimestamp == currentTimestamp) {
				timeSequence.nextSequence();
				if (timeSequence.getSequence() == 0) {
					// 阻塞到下一个毫秒,获得新的时间戳
					currentTimestamp = nextMillis(lastTimestamp);
				}
			} else {
				// 时间戳改变，毫秒内序列重置
				timeSequence.resetSequence();
			}

			// 更新最近生成的时间戳为当前时间戳
			timeSequence.setLastTimestamp(currentTimestamp);

			// 移位并通过"或"运算拼到一起组成64位的序列结果
			return sequenceGenerator.generate(currentTimestamp, timeSequence, parameter);
		} finally {
			lock.unlock(parameter);
		}
	}

	/**
	 * 参数化序列生成器接口
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	interface ParameterizeSequenceGenerator<T> {
		
		/**
		 * 根据指定的时间戳、时间序列对象和参数生成序列结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param timestamp
		 * @param timeSequence
		 * @param parameter
		 * @return
		 */
		public T generate(long timestamp, TimeSequence timeSequence, Object parameter);
	}
	
	/**
	 * 有相对开始时间截参与的无参序列生成器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class TwepochUnparameterizeSequenceGenerator extends TwepochSequenceGenerator
			implements ParameterizeSequenceGenerator<Number> {

		@Override
		public Number generate(long timestamp, TimeSequence timeSequence, Object parameter) {
			return super.generate(timestamp, timeSequence);
		}
	}
	
	/**
	 * 无相对开始时间截参与的无参序列生成器实现类
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class UntwepochUnparameterizeSequenceGenerator extends UntwepochSequenceGenerator implements ParameterizeSequenceGenerator<Number> {

		@Override
		public Number generate(long timestamp, TimeSequence timeSequence, Object parameter) {
			return super.generate(timestamp, timeSequence);
		}
	}
	
	/**
	 * 参数化序列生成器默认实现类，此实现类生成的结果是一个由无参序列生成器生成的结果和参数序列生成器生成的结果组合而成的数字
	 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
	 * @version 1.0
	 */
	class DefaultParameterizeSequenceGenerator implements ParameterizeSequenceGenerator<Number> {
		
		/** 无参序列生成器 */
		private final SequenceGenerator<Number> sequenceGenerator;
		
		/** 参数序列生成器 */
		private final ParameterizeGenerator<Object, String> parameterSequenceGenerator = new ShortLinkGenerator(true);
		
		protected DefaultParameterizeSequenceGenerator(SequenceGenerator<Number> sequenceGenerator) {
			AssertUtils.assertNotNull(sequenceGenerator, "Sequence generator must not be null");
			this.sequenceGenerator = sequenceGenerator;
		}
		
		/**
		 * 根据指定参数生成对应的字符串结果
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param parameter
		 * @return
		 */
		protected String generateByParameter(Object parameter) {
			int beginIndex = NumberUtils.randomIn(4) * 6;
			
			/* 利用短链接生成器生成参数序列结果。由于短链接算法生成的是24位长的结果，
			 * 因此这里将结果分为四组，每组6位长度，这里随机抽取任意一组字符串来作为实际结果 */
			return parameterSequenceGenerator.generate(parameter).substring(beginIndex, beginIndex + 6);
		}

		/**
		 * 生成的结果是一个由无参序列生成器生成的结果和参数序列生成器生成的结果组合而成的数字
		 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
		 * @param timestamp
		 * @param timeSequence
		 * @param parameter
		 * @return
		 */
		@Override
		public Number generate(long timestamp, TimeSequence timeSequence, Object parameter) {
			return new BigInteger(sequenceGenerator.generate(timestamp, timeSequence).toString() + generateByParameter(parameter));
		}
	}
	
	public static void main(String[] args) {
		ParameterizeSequenceNode sequenceNode = new ParameterizeSequenceNode();
		sequenceNode.setParameterAsResult(false);
		ParameterizeSnowflakeGenerator generator = new ParameterizeSnowflakeGenerator(sequenceNode);
		for (int i = 0; i < 20; i++) {
			System.out.println(generator.generate());
		}
	}

}
