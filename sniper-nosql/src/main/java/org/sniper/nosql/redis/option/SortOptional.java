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

import org.sniper.nosql.redis.enums.Order;

/**
 * 排序可选项接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface SortOptional {
	
	/**
	 * 获取by模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public byte[] getBy();
	
	/**
	 * 设置by模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param by
	 */
	public void setBy(byte[] by);
	
	/**
	 * 设置by模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param pattern
	 */
	public void setBy(String pattern);

	/**
	 * 获取分段限制
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Limit getLimit();

	/**
	 * 设置分段限制
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param limit
	 */
	public void setLimit(Limit limit);

	/**
	 * 获取get模式组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public byte[][] getGets();
	
	/**
	 * 设置get模式组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param gets
	 */
	public void setGets(byte[]... gets);

	/**
	 * 设置get模式组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param patterns
	 */
	public void setGets(String... patterns);
	
	/**
	 * 获取排序方式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public Order getOrder();

	/**
	 * 设置排序方式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 */
	public void setOrder(Order order);

	/**
	 * 判断是否使用alpha修饰符对字符串进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isAlpha();

	/**
	 * 设置是否使用alpha修饰符对字符串进行排序
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param alpha
	 */
	public void setAlpha(boolean alpha);
	
}
