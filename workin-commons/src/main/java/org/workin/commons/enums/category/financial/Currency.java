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
 * Create Date : 2015-11-27
 */

package org.workin.commons.enums.category.financial;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MessageUtils;

/**
 * @description 货币枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum Currency {
	
	/** 国际通用格式 	 */
	GENERIC("GENERIC", "#,##0.00", "currency.generic.type"),
	
	/** 人民币  */	
	CNY("CNY", "¥" + GENERIC.getPattern(), "currency.cny.type"),
	
	/** 美元 */  
	USD("USD", "$" + GENERIC.getPattern(), "currency.usd.type"),
	
	/** 日元 */  
	JPY("JPY", "¥" + GENERIC.getPattern(), "currency.jpy.type"),
	
	/** 韩元 */  	
	KRW("KRW", "₩" + GENERIC.getPattern(), "currency.krw.type"),
	
	/** 港币	HKD */  
	HKD("HKD", "HK$" + GENERIC.getPattern(), "currency.hkd.type"),
	
	/** 欧元 */  
	EUR("EUR", "€" + GENERIC.getPattern(), "currency.eur.type"),
	
	/** 英镑	*/  
	GBP("GBP", "￡" + GENERIC.getPattern(), "currency.gbp.type"),
	
	/** 德国马克	*/  
	DEM("DEM", "DM" + GENERIC.getPattern(), "currency.dem.type"),
	
	/** 瑞士法郎	*/  
	CHF("CHF", "S₣" + GENERIC.getPattern(), "currency.chf.type"),
	
	/** 法国法郎	*/  
	FRF("FRF", "F₣" + GENERIC.getPattern(), "currency.frf.type"),
	
	/** 俄罗斯卢布 */  	
	SUR("SUR", "РУБ" + GENERIC.getPattern(), "currency.sur.type"),
	
	/** 加拿大元	*/  
	CAD("CAD", "CAN$" + GENERIC.getPattern(), "currency.cad.type"),
	
	/** 澳大利亚元 */  
	AUD("AUD", "A$" + GENERIC.getPattern(), "currency.aud.type"),
	
	/** 新西兰元	*/  	
	NZD("NZD", "NZ$" + GENERIC.getPattern(), "currency.nzd.type"),
	
	/** 奥地利先令 */  
	ATS("ATS", "ASCH" + GENERIC.getPattern(), "currency.ats.type"),
	
	/** 芬兰马克 */  
	FIM("FIM", "FMK" + GENERIC.getPattern(), "currency.fim.type"),
	
	/** 瑞典克朗 */
	SEK("SEK", "SKR" + GENERIC.getPattern(), "currency.sek.type"),
	
	/** 丹麦克朗 */
	DKR("DKK", "DKR" + GENERIC.getPattern(), "currency.dkk.type"),
	
	/** 挪威克朗 */
	NOK("NOK", "DKK" + GENERIC.getPattern(), "currency.nok.type"),
	
	/** 比利时法郎 */  
	BEF("BEF", "B₣" + GENERIC.getPattern(), "currency.bef.type"),
	
	/** 意大利里拉 */  
	ITL("ITL", "₤" + GENERIC.getPattern(), "currency.itl.type"),
	
	/** 荷兰盾 */ 	
	NLG("NLG", "F" + GENERIC.getPattern(), "currency.nlg.type"),
	
	/** 印度卢比 */ 	
	INR("INR", "RS" + GENERIC.getPattern(), "currency.inr.type"),
	
	/** 缅甸元 */ 	
	BUK("BUK", "K" + GENERIC.getPattern(), "currency.buk.type"),
	
	/** 印尼盾 */  
	IDR	("IDR", "RP" + GENERIC.getPattern(), "currency.idr.type"),
	
	/** 新加坡元 */  		
	SGD("SGD", "S$" + GENERIC.getPattern(), "currency.sgd.type"),
	
	/** 泰铢	*/  	
	THB("THB", "฿" + GENERIC.getPattern(), "currency.thb.type"),
	
	/** 菲律宾比索 */  	
	PHP("PHP", "₱" + GENERIC.getPattern(), "currency.php.type");
	
	/** 简称 */
	private String shortName;
	
	/** 模式 */
	private String pattern;
	
	/** 全称*/
	private String name;
	
	private Currency(String shortName, String pattern, String name) {
		this.shortName = shortName;
		this.pattern = pattern;
		this.name = name;
	}

	/**
	 * @description 获取当前货币的简称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @description 获取当前货币的模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getPattern() {
		return pattern;
	}
	
	/**
	 * @description 获取当前货币的类型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int getType() {
		return this.ordinal();
	}

	/**
	 * @description 获取当前货币的全称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getName() {
		return MessageUtils.getClassMessage(this.getClass(), this.name);
	}
	
	/**
	 * @description 获取指定类型对应的货币
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static Currency get(int type) {
		return ArrayUtils.get(Currency.values(), type);
	}
	
	/**
	 * @description 获取指定类型对应的货币模式
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static String getPattern(int type) {
		Currency currency = get(type);
		return currency != null ? currency.getPattern() : getGenericPattern();
	}
	
	/**
	 * @description 获取国际通用的货币符号
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public static String getGenericPattern() {
		return "¤" + GENERIC.getPattern();
	}
	
	/**
	 * @description 获取指定类型对应的货币全称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static String getName(int type) {
		Currency currency = get(type);
		return currency != null ? currency.getName() : GENERIC.getName();
	}
			
}
