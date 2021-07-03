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
 * Create Date : 2018-12-28
 */

package org.sniper.commons.util;

/**
 * 布尔工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BooleanUtils {
	
	private BooleanUtils() {}
	
	/**
	 * 判断包装类布尔值是否为true
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static boolean isTrue(Boolean value) {
		return value != null && value;
	}
	
	/**
	 * 判断包装类布尔值是否为false
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param value
	 * @return
	 */
	public static boolean isFalse(Boolean value) {
		return !isTrue(value);
	}
	
}
