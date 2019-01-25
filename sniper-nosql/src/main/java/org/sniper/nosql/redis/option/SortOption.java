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
 * Create Date : 2019-1-17
 */

package org.sniper.nosql.redis.option;

import java.util.List;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.nosql.redis.enums.Order;

/**
 * 排序可选项
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class SortOption implements SortOptional {
	
	/** by模式 */
	private byte[] by;
	
	/** 分段限制 */
	private Limit limit;
	
	/** get模式组 */
	private byte[][] gets;
	
	/** 排序方式 */
	private Order order;
	
	/** 是否使用alpha修饰符对字符串进行排序 */
	private boolean alpha;
	
	public SortOption() {
		this(true);
	}
	
	public SortOption(boolean alpha) {
		this(null, null, alpha);
	}
	
	public SortOption(Limit limit, Order order) {
		this(limit, order, true);
	}
		
	public SortOption(Limit limit, Order order, boolean alpha) {
		this((byte[]) null, limit, (byte[][]) null, order, alpha);
	}
	
	public SortOption(String byPattern, Limit limit, String[] getPatterns, Order order, boolean alpha) {
		this(toBy(byPattern), limit, toGets(getPatterns), order, alpha);
	}
	
	public SortOption(byte[] by, Limit limit, byte[][] gets, Order order, boolean alpha) {
		this.by = by;
		this.limit = limit;
		this.gets = gets;
		this.order = order;
		this.alpha = alpha;
	}
	
	@Override
	public byte[] getBy() {
		return by;
	}
	
	@Override
	public void setBy(byte[] by) {
		this.by = by;
	}
	
	@Override
	public void setBy(String pattern) {
		setBy(toBy(pattern));
	}

	@Override
	public Limit getLimit() {
		return limit;
	}

	@Override
	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	@Override
	public byte[][] getGets() {
		return gets;
	}
	
	@Override
	public void setGets(byte[]... gets) {
		this.gets = gets;
	}

	@Override
	public void setGets(String... patterns) {
		setGets(toGets(patterns));
	}
	
	@Override
	public Order getOrder() {
		return order;
	}

	@Override
	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public boolean isAlpha() {
		return alpha;
	}

	@Override
	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(toByString());
		append(builder, toLimitString());
		append(builder, toGetsString());
		append(builder, toOrderString());
		append(builder, toAlphaString());
			
		return builder.toString();
	}
	
	/**
	 * 将字符串选项拼接到builder对象中
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param builder
	 * @param option
	 */
	private void append(StringBuilder builder, String option) {
		if (option.length() > 0) {
			if (builder.length() > 0)
				builder.append(StringUtils.SPACE);
			
			builder.append(option);
		}
	}
	
	/**
	 * 将BY模式字节数组转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private String toByString() {
		if (ArrayUtils.isEmpty(by))
			return StringUtils.EMPTY;
		
		return "BY " + new String(by);
	}
	
	/**
	 * 将分段限制转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private String toLimitString() {
		return limit != null ? "LIMIT " + limit : StringUtils.EMPTY;
	}
	
	/**
	 * 将GET模式字节组转换成字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private String toGetsString() {
		if (ArrayUtils.isEmpty(gets))
			return StringUtils.EMPTY;
		
		StringBuilder builder = new StringBuilder();
		for (byte[] get : gets) {
			
			if (builder.length() > 0)
				builder.append(StringUtils.SPACE);
			
			if (ArrayUtils.isNotEmpty(get))
				builder.append("GET ").append(new String(get));
		}
		
		return builder.toString();
	}
	
	/**
	 * 将排序方式转换为字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private String toOrderString() {
		return order != null ? order.name() : StringUtils.EMPTY;
	}
	
	/**
	 * 将alpha转换为字符串
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	private String toAlphaString() {
		return alpha ? "ALPHA" : StringUtils.EMPTY;
	}
	
	/**
	 * 将指定的模式转换成BY模式字节数组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 * @return
	 */
	private static byte[] toBy(String pattern) {
		return pattern != null ? pattern.getBytes() : null;
	}
	
	/**
	 * 将指定的多个模式转换成GET模式字节组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param patterns
	 * @return
	 */
	private static byte[][] toGets(String[] patterns) {
		if (ArrayUtils.isEmpty(patterns))
			return null;
		
		List<byte[]> gets = CollectionUtils.newArrayList(patterns.length);
		for (String pattern : patterns) {
			if (pattern != null)
				gets.add(pattern.getBytes());
		}
				
		return gets.toArray(new byte[gets.size()][]);
	}
	
}
