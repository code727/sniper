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
 * 星座枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class Horoscope extends AbstractLocaleEnums<Integer> {

	private Horoscope(Integer key, String messageKey) {
		super(key, messageKey);
	}
	
	/** 白羊座 */
	public static final Horoscope ARIES = new Horoscope(0, "horoscope.type.aries");
	
	/** 金牛座 */
	public static final Horoscope TAURUS = new Horoscope(1, "horoscope.type.taurus");
	
	/** 双子座 */
	public static final Horoscope GEMINI = new Horoscope(2, "horoscope.type.gemini");
	
	/** 巨蟹座 */
	public static final Horoscope CANCER = new Horoscope(3, "horoscope.type.cancer");
	
	/** 狮子座 */
	public static final Horoscope LEO = new Horoscope(4, "horoscope.type.leo");
	
	/** 处女座 */
	public static final Horoscope VIRGO = new Horoscope(5, "horoscope.type.virgo");
	
	/** 天秤座 */
	public static final Horoscope LIBRA = new Horoscope(6, "horoscope.type.libra");
	
	/** 天蝎座 */
	public static final Horoscope ACRAB = new Horoscope(7, "horoscope.type.acrab");
	
	/** 射手座 */
	public static final Horoscope SAGITTARIUS = new Horoscope(8, "horoscope.type.sagittarius");
	
	/** 摩羯座 */
	public static final Horoscope CAPRICORN = new Horoscope(9, "horoscope.type.capricorn");
	
	/** 水平座 */
	public static final Horoscope AQUARIUS = new Horoscope(10, "horoscope.type.aquarius");
	
	/** 双鱼座 */
	public static final Horoscope PISCES = new Horoscope(11, "horoscope.type.pisces");

}
