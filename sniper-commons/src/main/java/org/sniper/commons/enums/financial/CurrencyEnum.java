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

import org.sniper.commons.enums.Enumerable;
import org.sniper.commons.util.CurrencyUtils;
import org.sniper.commons.util.MapUtils;

/**
 * 国际货币枚举类
 * @author  Daniele
 * @version 1.0
 */
public enum CurrencyEnum implements Enumerable<Integer> {
	
	/** 人民币  */	
	CNY("¥"),
	
	/** 美元 */  
	USD("$"),
	
	/** 日元 */  
	JPY("¥"),
	
	/** 韩元 */  	
	KRW("₩"),
	
	/** 港币 */  
	HKD("HK$"),
	
	/** 澳门元 */
	MOP("MOP$"),
	
	/** 新台币 */
	TWD("NT$"),
	
	/** 欧元 */  
	EUR("€"),
	
	/** 英镑 */  
	GBP("￡"),
	
	/** 德国马克 */  
	DEM("DM"),
	
	/** 意大利里拉 */  
	ITL("₤"),
	
	/** 荷兰盾 */ 	
	NLG("F"),
	
	/** 俄罗斯卢布 */  	
	RUB("₽"),
	
	/** 瑞士法郎 */  
	CHF("S₣"),
	
	/** 法国法郎 */  
	FRF("F₣"),
	
	/** 比利时法郎 */  
	BEF(null),
	
	/** 奥地利先令 */  
	ATS(null),
	
	/** 芬兰马克 */  
	FIM(null),
	
	/** 瑞典克朗 */
	SEK(null),
	
	/** 丹麦克朗 */
	DKK(null),
	
	/** 挪威克朗 */
	NOK(null),
	
	/** 加拿大元 */  
	CAD("C$"),
	
	/** 澳大利亚元 */  
	AUD("A$"),
	
	/** 新西兰元 */  	
	NZD("NZ$"),
	
	/** 印度卢比 */ 	
	INR("₹"),
	
	/** 缅甸元 */ 	
	MMK(null),
	
	/** 印尼盾 */  
	IDR("Rp"),
	
	/** 新加坡元 */  		
	SGD("S$"),
	
	/** 泰铢 */  	
	THB(null),
	
	/** 菲律宾比索 */  	
	PHP(null)
	;
	
	private static final Map<Integer, CurrencyEnum> KEY_MAPPINGS = MapUtils.newHashMap(30);
	
	private static final Map<String, CurrencyEnum> NAME_MAPPINGS = MapUtils.newHashMap(30);
	
	static {
		for (CurrencyEnum currency : values()) {
			KEY_MAPPINGS.put(currency.key, currency);
			NAME_MAPPINGS.put(currency.name(), currency);
		}
	}
		
	/** 键 */
	private final int key;
	
	/** Currency实例 */
	private final Currency instance;
	
	/** 符号 */
	private final String symbol;
	
	/** 格式化模式 */
	private final String pattern;
	
	/** 消息 */
	private final String message;
	
	private CurrencyEnum(String symbol) {
		this.key = ordinal();
		this.instance = Currency.getInstance(name());
		this.symbol = (symbol != null ? symbol : this.instance.getSymbol());
		this.pattern = this.symbol + CurrencyUtils.CURRENCY_FORMAT;
		this.message = this.instance.getDisplayName();
	}
	
	@Override
	public Integer getKey() {
		return key;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public Currency getInstance() {
		return instance;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	public boolean matches(Integer key) {
		return key != null && this.key == key.intValue();
	}

	@Override
	public boolean matches(String name) {
		return this.name().equalsIgnoreCase(name);
	}
	
	/**
	 * 将指定的键解析成枚举对象
	 * @author Daniele 
	 * @param type
	 * @return
	 */
	public static CurrencyEnum resolve(int key) {
		return KEY_MAPPINGS.get(key);
	}
	
	/**
	 * 将指定的名称解析成枚举对象
	 * @author Daniele 
	 * @param name
	 * @return
	 */
	public static CurrencyEnum resolve(String name) {
		return name != null ? NAME_MAPPINGS.get(name.toUpperCase()) : null;
	}
		
}
