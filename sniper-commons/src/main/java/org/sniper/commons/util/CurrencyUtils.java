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

package org.sniper.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.sniper.commons.enums.financial.CurrencyEnum;

/**
 * 货币金融工具类
 * @author  Daniele
 * @version 1.0
 */
public abstract class CurrencyUtils {
	
	/** 国际货币格式 */
	public static final String CURRENCY_FORMAT = "#,##0.00";
	
	/** 元角分货币格式 */
	public static final String YUANJIAOFEN_FORMAT = "0.00";
	
	/**
	 * 将字符的ASCII码转化为指定货币格式的字符串
	 * @author Daniele 
	 * @param number
	 * @param currency
	 * @return
	 */
	public static String currency(char number, CurrencyEnum currency) {
		return NumberUtils.format(number, currency.getPattern());
	}
	
	/**
	 * 将数字转化为指定货币格式的字符串
	 * @author Daniele 
	 * @param number
	 * @param currency
	 * @return
	 */
	public static String currency(Number number, CurrencyEnum currency) {
		return NumberUtils.format(number, currency.getPattern());
	}
	
	/**
	 * 将字符的ASCII码转化为人民币字符串
	 * @author Daniele 
	 * @param number
	 * @return
	 */
	public static String RMB(char number) {
		return RMB((long) number);
	}
	
	/**
	 * 将数字转化为人民币字符串
	 * @author Daniele 
	 * @param number
	 * @return
	 */
	public static String RMB(Number number) {
		return NumberUtils.format(number, CurrencyEnum.CNY.getPattern());
	}

	/**
	 * 将比特型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(byte cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * 将字符型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(char cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * 将短整型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(short cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * 将整型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(int cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * 将长整型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(long cash) {
		return yuanToFen(new BigDecimal(cash));
	}
	
	/**
	 * 将单精度浮点数的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(float cash) {
		return yuanToFen(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将单精度浮点数的元转换为分
	 * @author Daniele 
	 * @param yuan
	 * @param round
	 * @return
	 */
	public static BigInteger yuanToFen(float cash, boolean round) {
		return yuanToFen(new BigDecimal(cash), round);
	}
	
	/**
	 * 将双精度浮点数的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(double cash) {
		return yuanToFen(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将双精度浮点数的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigInteger yuanToFen(double cash, boolean round) {
		return yuanToFen(new BigDecimal(cash), round);
	}
	
	/**
	 * 将BigDecimal类型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(BigDecimal cash) {
		return yuanToFen(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将BigDecimal类型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigInteger yuanToFen(BigDecimal cash, boolean round) {
		if (cash == null)
			return new BigInteger("0");
		
		// 选择是否四舍五入第三位小数的厘到分
		cash = round ? cash.setScale(2, BigDecimal.ROUND_HALF_UP) : cash.setScale(2, BigDecimal.ROUND_DOWN);
		BigDecimal fen = cash.multiply(new BigDecimal(100)).setScale(0);
		return new BigInteger(fen.toString());
	}
	
	/**
	 * 将BigInteger类型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(BigInteger cash) {
		if (cash == null)
			return new BigInteger("0");
		
		return cash.multiply(new BigInteger("100"));
	}
	
	/**
	 * 将字符串类型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigInteger yuanToFen(String cash) {
		return yuanToFen(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将字符串类型的元转换为分
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigInteger yuanToFen(String cash, boolean round) {
		return  yuanToFen(cash, new BigInteger("0"));
	}
	
	/**
	 * 将字符串类型的元转换为分， 当字符串为空或转换出现异常时返回指定的默认分
	 * @author Daniele 
	 * @param cash
	 * @param defaultFen
	 * @return
	 */
	public static BigInteger yuanToFen(String cash, BigInteger defaultFen) {
		return yuanToFen(cash, false, defaultFen);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将字符串类型的元转换为分， 当字符串为空或转换出现异常时返回指定的默认分
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @param defaultFen
	 * @return
	 */
	public static BigInteger yuanToFen(String cash, boolean round, BigInteger defaultFen) {
		if (StringUtils.isBlank(cash))
			return defaultFen;
		
		try {
			return yuanToFen(new BigDecimal(cash), round);
		} catch (NumberFormatException e) {
			return defaultFen; 
		}
	}
	
	/**
	 * 将比特型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(byte cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将字符型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(char cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将短整型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(short cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将整型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(int cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将长整型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(long cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将单精度浮点数的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(float cash) {
		return fenToYuan(cash , false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将单精度浮点数的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigDecimal fenToYuan(float cash, boolean round) {
		return round ? fenToYuan(Math.round(cash)) : fenToYuan((int) cash);
	}
	
	/**
	 * 将双精度浮点数的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(double cash) {
		return fenToYuan(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将双精度浮点数的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigDecimal fenToYuan(double cash, boolean round) {
		return round ? fenToYuan(Math.round(cash)) : fenToYuan((long) cash);
	}
	
	/**
	 * 将BigInteger类型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(BigInteger cash) {
		return fenToYuan(new BigDecimal(cash));
	}
	
	/**
	 * 将BigDecimal类型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(BigDecimal cash) {
		return fenToYuan(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将BigDecimal类型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigDecimal fenToYuan(BigDecimal cash, boolean round) {
		if (cash == null)
			return new BigDecimal(0).setScale(2);
		
		/*  选择是否采用四舍五入的方式舍弃小数点位的数字 */
		if (round)
			cash = cash.setScale(0, BigDecimal.ROUND_HALF_UP);
		else
			cash = cash.setScale(0, BigDecimal.ROUND_DOWN);
		
		// 现金除以100后保留两位小数
		return cash.divide(new BigDecimal(100)).setScale(2);
	}
	
	/**
	 * 将字符串类型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @return
	 */
	public static BigDecimal fenToYuan(String cash) {
		return fenToYuan(cash, false);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将字符串类型的分转换为元
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @return
	 */
	public static BigDecimal fenToYuan(String cash, boolean round) {
		return fenToYuan(cash, round, new BigDecimal("0.00"));
	}
	
	/**
	 * 将字符串类型的分转换为元，当字符串为空或转换出现异常时返回指定的默认分
	 * @author Daniele 
	 * @param cash
	 * @param defaultYuan
	 * @return
	 */
	public static BigDecimal fenToYuan(String cash, BigDecimal defaultYuan) {
		return fenToYuan(cash, false, defaultYuan);
	}
	
	/**
	 * 选择是否采用四舍五入的方式将字符串类型的分转换为元， 当字符串为空或转换出现异常时返回指定的默认元
	 * @author Daniele 
	 * @param cash
	 * @param round
	 * @param defaultYuan
	 * @return
	 */
	public static BigDecimal fenToYuan(String cash, boolean round, BigDecimal defaultYuan) {
		if (StringUtils.isBlank(cash))
			return round ? defaultYuan.setScale(2, BigDecimal.ROUND_HALF_UP)
					: defaultYuan.setScale(2, BigDecimal.ROUND_DOWN);
		
		try {
			return fenToYuan(new BigDecimal(cash), round);
		} catch (NumberFormatException e) {
			if (defaultYuan == null)
				return defaultYuan;
			
			return round ? defaultYuan.setScale(2, BigDecimal.ROUND_HALF_UP)
					: defaultYuan.setScale(2, BigDecimal.ROUND_DOWN);
		}
	}
	
}
