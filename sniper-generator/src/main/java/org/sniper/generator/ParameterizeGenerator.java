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
 * Create Date : 2017-11-9
 */

package org.sniper.generator;

import java.util.List;

/**
 * 参数化生成器接口
 * @author  Daniele
 * @version 1.0
 */
public interface ParameterizeGenerator<P, T> extends Generator<T> {
	
	/**
	 * 根据指定的参数生成结果
	 * @author Daniele 
	 * @param parameter
	 * @return
	 */
	public T generateByParameter(P parameter);
	
	/**
	 * 根据参数批量生成指定数量的结果
	 * @author Daniele 
	 * @param parameter
	 * @param count
	 * @return
	 */
	public List<T> batchGenerateByParameter(P parameter, int count);

}
