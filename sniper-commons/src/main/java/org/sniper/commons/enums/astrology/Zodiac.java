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

package org.sniper.commons.enums.astrology;

import org.sniper.commons.enums.AbstractLocaleEnum;

/**
 * 十二生肖枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Zodiac extends AbstractLocaleEnum<Integer> {

	private static final long serialVersionUID = -562460069540966306L;

	private Zodiac(Integer key, String value) {
		super(key, value);
	}
	
	/** 鼠 */
	public static final Zodiac MOUSE = new Zodiac(0, "zodiac.type.mouse");
	
	/** 牛 */
	public static final Zodiac CATTLE = new Zodiac(1, "zodiac.type.cattle");
	
	/** 虎 */
	public static final Zodiac TIGER = new Zodiac(2, "zodiac.type.tiger");
	
	/** 兔 */
	public static final Zodiac RABBIT = new Zodiac(3, "zodiac.type.rabbit");
	
	/** 龙  */
	public static final Zodiac DRAGON = new Zodiac(4, "zodiac.type.dragon");
	
	/** 蛇 */
	public static final Zodiac SNAKE = new Zodiac(5, "zodiac.type.snake");
	
	/** 马 */
	public static final Zodiac HORSE = new Zodiac(6, "zodiac.type.horse");
	
	/** 羊 */
	public static final Zodiac SHEEP = new Zodiac(7, "zodiac.type.sheep");
	
	/** 猴 */
	public static final Zodiac MONKEY = new Zodiac(8, "zodiac.type.monkey");
	
	/** 鸡 */
	public static final Zodiac CHOOK = new Zodiac(9, "zodiac.type.chook");
	
	/** 狗*/
	public static final Zodiac DOG = new Zodiac(10, "zodiac.type.dog");
	
	/** 猪 */
	public static final Zodiac PIG = new Zodiac(11, "zodiac.type.pig");

}
