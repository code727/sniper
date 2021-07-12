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

package org.sniper.commons.enums.financial;

import java.util.Currency;
import java.util.Map;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.MessageUtils;
import org.sniper.commons.util.NumberUtils;

/**
 * 货币枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum CurrencyEnum {
	
	/** 人民币  */	
	CNY("CNY", "currency.cny", String.format("¥%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 美元 */  
	USD("USD", "currency.usd", String.format("$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 日元 */  
	JPY("JPY", "currency.jpy", String.format("¥%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 韩元 */  	
	KRW("KRW", "currency.krw", String.format("₩%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 港币 */  
	HKD("HKD", "currency.hkd", String.format("HK$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 欧元 */  
	EUR("EUR", "currency.eur", String.format("€%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 英镑	*/  
	GBP("GBP", "currency.gbp", String.format("￡%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 德国马克	*/  
	DEM("DEM", "currency.dem", String.format("DM%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 瑞士法郎	*/  
	CHF("CHF", "currency.chf", String.format("S₣%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 法国法郎	*/  
	FRF("FRF", "currency.frf", String.format("F₣%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 俄罗斯卢布 */  	
	SUR("SUR", "currency.sur", String.format("РУБ%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 加拿大元	*/  
	CAD("CAD", "currency.cad", String.format("CAN$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 澳大利亚元 */  
	AUD("AUD", "currency.aud", String.format("A$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 新西兰元 */  	
	NZD("NZD", "currency.nzd", String.format("NZ$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 奥地利先令 */  
	ATS("ATS", "currency.ats", String.format("ASCH%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 芬兰马克 */  
	FIM("FIM", "currency.fim", String.format("FMK%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 瑞典克朗 */
	SEK("SEK", "currency.sek", String.format("SKR%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 丹麦克朗 */
	DKR("DKK", "currency.dkk", String.format("DKR%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 挪威克朗 */
	NOK("NOK", "currency.nok", String.format("DKK%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 比利时法郎 */  
	BEF("BEF", "currency.bef", String.format("B₣%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 意大利里拉 */  
	ITL("ITL", "currency.itl", String.format("₤%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 荷兰盾 */ 	
	NLG("NLG", "currency.nlg", String.format("F%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 印度卢比 */ 	
	INR("INR", "currency.inr", String.format("RS%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 缅甸元 */ 	
	BUK("BUK", "currency.buk", String.format("K%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 印尼盾 */  
	IDR	("IDR", "currency.idr", String.format("RP%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 新加坡元 */  		
	SGD("SGD", "currency.sgd", String.format("S$%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 泰铢	*/  	
	THB("THB", "currency.thb", String.format("฿%s", NumberUtils.CURRENCY_FORMAT)),
	
	/** 菲律宾比索 */  	
	PHP("PHP", "currency.php", String.format("₱%s", NumberUtils.CURRENCY_FORMAT));
	
	private static final Map<String, CurrencyEnum> mappings = MapUtils.newHashMap(233);
	
	static {
		for (CurrencyEnum currency : values()) {
			mappings.put(currency.abbreviation, currency);
		}
	}
		
	/** 键 */
	private final int key;
	
	/** 缩写/简称 */
	private final String abbreviation;
	
	/** 值 */
	private final String value;
	
	/** 模式 */
	private final String pattern;
	
	/** 消息 */
	private final String message;
	
	private CurrencyEnum(String abbreviation, String value, String pattern) {
		this.key = ordinal();
		this.abbreviation = abbreviation;
		this.value = value;
		this.pattern = pattern;
		this.message = MessageUtils.getClassMessage(getClass(), value);
	}
	
	public int getKey() {
		return key;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getValue() {
		return value;
	}

	public String getPattern() {
		return pattern;
	}
	
	public String getMessage() {
		return message;
	}
	
	/**
	 * 判断指定的键是否匹配一个Currency对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public boolean matches(int key) {
		return this.key == key;
	}
	
	/**
	 * 判断指定的缩写/简称是否匹配一个Currency对象
	 * @author Daniele 
	 * @param abbreviation
	 * @return
	 */
	public boolean matches(String abbreviation) {
		return this.abbreviation.equalsIgnoreCase(abbreviation);
	}
	
	/**
	 * 将指定的键解析成Currency对象
	 * @author Daniele 
	 * @param key
	 * @return
	 */
	public static CurrencyEnum resolve(int key) {
		return ArrayUtils.get(values(), key);
	}
	
	/**
	 * 将指定的缩写/简称解析成Currency对象
	 * @author Daniele 
	 * @param abbreviation
	 * @return
	 */
	public static CurrencyEnum resolve(String abbreviation) {
		return abbreviation != null ? mappings.get(abbreviation.toUpperCase()) : null;
	}
	
	public static void main(String[] args) {
		System.out.println(Currency.getAvailableCurrencies().size());;
		
		Currency currency = Currency.getInstance("CNY");
		System.out.println(currency.getDisplayName());
		System.out.println(currency.getSymbol());
	}
	
}
