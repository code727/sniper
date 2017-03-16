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
 * Create Date : 2015-12-17
 */

package org.workin.commons.enums.astrology;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * 十二生肖枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Zodiac extends AbstractLocaleEnums<Integer> {

	private Zodiac(Integer key, String messageKey) {
		super(key, messageKey);
	}
	
	/** 鼠 */
	public static final Zodiac mouse = new Zodiac(0, "zodiac.type.mouse");
	
	/** 牛 */
	public static final Zodiac cattle = new Zodiac(1, "zodiac.type.cattle");
	
	/** 虎 */
	public static final Zodiac tiger = new Zodiac(2, "zodiac.type.tiger");
	
	/** 兔 */
	public static final Zodiac rabbit = new Zodiac(3, "zodiac.type.rabbit");
	
	/** 龙  */
	public static final Zodiac dragon = new Zodiac(4, "zodiac.type.dragon");
	
	/** 蛇 */
	public static final Zodiac snake = new Zodiac(5, "zodiac.type.snake");
	
	/** 马 */
	public static final Zodiac horse = new Zodiac(6, "zodiac.type.horse");
	
	/** 羊 */
	public static final Zodiac sheep = new Zodiac(7, "zodiac.type.sheep");
	
	/** 猴 */
	public static final Zodiac monkey = new Zodiac(8, "zodiac.type.monkey");
	
	/** 鸡 */
	public static final Zodiac chook = new Zodiac(9, "zodiac.type.chook");
	
	/** 狗*/
	public static final Zodiac dog = new Zodiac(10, "zodiac.type.dog");
	
	/** 猪 */
	public static final Zodiac pig = new Zodiac(11, "zodiac.type.pig");

}
