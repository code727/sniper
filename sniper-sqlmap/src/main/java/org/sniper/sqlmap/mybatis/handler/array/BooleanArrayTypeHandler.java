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

package org.sniper.sqlmap.mybatis.handler.array;

/**
 * 布尔数组类型处理器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BooleanArrayTypeHandler extends ArrayTypeHandler<Boolean> {
	
	@Override
	protected Boolean[] handResult(String[] resultArray) {
		int length = resultArray.length;
		Boolean[] result = new Boolean[length];
		
		String element;
		for (int i = 0; i < length; i++) {
			element = resultArray[i];
			result[i] = ("true".equalsIgnoreCase(element) || "Y".equalsIgnoreCase(element) || "1".equals(element));
		}
			
		return result;
	}

}
