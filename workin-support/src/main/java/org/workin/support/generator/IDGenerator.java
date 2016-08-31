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

package org.workin.support.generator;

/**
 * ID生成器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface IDGenerator {
	
	/**
	 * 生成ID号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String generate();
	
	/**
	 * 生成ID号，并组合上前缀和后缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public String generate(String prefix, String suffix);
	
	/**
	 * 生成ID号，并组合上前缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param prefix
	 * @return
	 */
	public String generateByPrefix(String prefix);
	
	/**
	 *  生成ID号，并组合上后缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param suffix
	 * @return
	 */
	public String generateBySuffix(String suffix);

}
