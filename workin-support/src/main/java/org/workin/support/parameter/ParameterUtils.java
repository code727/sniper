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
 * Create Date : 2015-11-21
 */

package org.workin.support.parameter;

import org.workin.commons.util.MapUtils;

/**
 * 泛型参数工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParameterUtils {
	
	/**
	 * 判断参数对象是否为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	public static <K, V> boolean isEmpty(Parameter<K, V> parameter) {
		return parameter == null || MapUtils.isEmpty(parameter.getParameters());
	}
	
	/**
	 * 判断参数对象是否不为空
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param parameter
	 * @return
	 */
	public static <K, V> boolean isNotEmpty(Parameter<K, V> parameter) {
		return !isEmpty(parameter);
	}

}
