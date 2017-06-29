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
 * Create Date : 2017-3-20
 */

package org.sniper.persistence.sqlmap.mybatis.handler.array;

/**
 * 双精度浮点型数组类型处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DoubleArrayTypeHandler extends ArrayTypeHandler<Double> {
	
	@Override
	protected Double[] handResult(String[] resultArray) {
		int length = resultArray.length;
		Double[] result = new Double[length];
		
		for (int i = 0; i < length; i++)
			result[i] = Double.valueOf(resultArray[i]);
		
		return result;
	}

}
