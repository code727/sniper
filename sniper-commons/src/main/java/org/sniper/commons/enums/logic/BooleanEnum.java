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
 * Create Date : 2017-6-14
 */

package org.sniper.commons.enums.logic;

/**
 * 布尔枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum BooleanEnum {
	
	FALSE, TRUE, N, Y, NO, YES, OFF, ON;
	
	/**
	 * 根据字符串值解析出布尔值
	 * @author Daniele 
	 * @param value
	 * @return
	 */
	public static Boolean parse(String value) {
		return BooleanEnum.TRUE.name().equalsIgnoreCase(value) || BooleanEnum.Y.name().equalsIgnoreCase(value)
				|| BooleanEnum.YES.name().equalsIgnoreCase(value) || BooleanEnum.ON.name().equalsIgnoreCase(value)
				|| "1".equals(value);
	}
			
}
