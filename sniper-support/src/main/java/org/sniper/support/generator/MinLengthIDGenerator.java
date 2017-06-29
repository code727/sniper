/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-11-19
 */

package org.sniper.support.generator;

/**
 * 具有最小长度限制的ID生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface MinLengthIDGenerator extends IDGenerator {
	
	/** 默认的最小长度 */
	public int DEFAULT_MIN_LENGTH = 16;
	
	/**
	 * 设置ID的最小长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param minLength
	 */
	public void setMinLength(int minLength);
	
	/**
	 * 设置ID的最小长度
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getMinLength();
	
	/**
	 * 设置当总长度未达到最小要求时需要补充的字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param supply
	 */
	public void setSupply(char supply);
	
	/**
	 * 获取当总长度未达到最小要求时需要补充的字符
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public char getSupply();
	
	/**
	 * 设置补充字符的位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param supplyPosition
	 */
	public void setSupplyPosition(int supplyPosition);
	
	/**
	 * 获取补充字符的位置
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getSupplyPosition();

}
