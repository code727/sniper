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
 * Create Date : 2015-11-17
 */

package org.workin.payment.enums.payment;

import java.util.Map;

import org.workin.commons.enums.AbstractNestableLocaleEnums;
import org.workin.commons.enums.Enums;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 第三方支付状态枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class ThirdPaymentStatus extends AbstractNestableLocaleEnums<String, Integer> {
	
	/** 支付状态映射集 */
	private static final Map<String, Enums<Integer, String>> PAY_STATUS_MAP;

	protected ThirdPaymentStatus(String key, Enums<Integer, String> value) {
		super(key, value);
	}
	
	/** 支付宝交易状态枚举*/
	public static final ThirdPaymentStatus ALIPAY_WAIT_BUYER_PAY = new ThirdPaymentStatus("WAIT_BUYER_PAY", PaymentStatus.WAIT_BUYER_PAY);
	public static final ThirdPaymentStatus ALIPAY_WAIT_SELLER_SEND_GOODS = new ThirdPaymentStatus("WAIT_SELLER_SEND_GOODS", PaymentStatus.WAIT_SELLER_SEND_GOODS);
	public static final ThirdPaymentStatus ALIPAY_WAIT_BUYER_CONFIRM_GOODS = new ThirdPaymentStatus("WAIT_BUYER_CONFIRM_GOODS", PaymentStatus.WAIT_BUYER_CONFIRM_GOODS);
	public static final ThirdPaymentStatus ALIPAY_TRADE_FINISHED = new ThirdPaymentStatus("TRADE_FINISHED", PaymentStatus.TRADE_FINISHED);
	public static final ThirdPaymentStatus ALIPAY_TRADE_CLOSED = new ThirdPaymentStatus("TRADE_CLOSED", PaymentStatus.TRADE_CLOSED);
	public static final ThirdPaymentStatus ALIPAY_MODIFY_TRADEBASE_TOTALFEE = new ThirdPaymentStatus("modify.tradeBase.totalFee", PaymentStatus.TRADE_CLOSED);
	
	
	static {
		PAY_STATUS_MAP = MapUtils.newHashMap();
		
		/** 支付宝交易状态映射集 */
		PAY_STATUS_MAP.put(ALIPAY_WAIT_BUYER_PAY.getKey(), ALIPAY_WAIT_BUYER_PAY.getValue());
		PAY_STATUS_MAP.put(ALIPAY_WAIT_SELLER_SEND_GOODS.getKey(), ALIPAY_WAIT_SELLER_SEND_GOODS.getValue());
		PAY_STATUS_MAP.put(ALIPAY_WAIT_BUYER_CONFIRM_GOODS.getKey(), ALIPAY_WAIT_BUYER_CONFIRM_GOODS.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_FINISHED.getKey(), ALIPAY_TRADE_FINISHED.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_CLOSED.getKey(), ALIPAY_TRADE_CLOSED.getValue());
		PAY_STATUS_MAP.put(ALIPAY_MODIFY_TRADEBASE_TOTALFEE.getKey(), ALIPAY_MODIFY_TRADEBASE_TOTALFEE.getValue());
		
		
	}
	
	/**
	 * @description 根据第三方交易状态码获取对应的支付状态对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static PaymentStatus getPaymentStatus(String thirdCode) {
		return (PaymentStatus) PAY_STATUS_MAP.get(thirdCode);
	}
	
	/**
	 * @description 根据第三方交易状态码获取对应的支付状态码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static int getPaymentStatusCode(String thirdCode) {
		PaymentStatus payStatus = getPaymentStatus(thirdCode);
		return payStatus != null ? payStatus.getKey() : -1;
	}
	
	/**
	 * @description 根据第三方交易状态码获取对应的支付消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdPayStatuCode
	 * @return
	 */
	public static String getPaymentMessage(String thirdCode) {
		return getPaymentMessage(thirdCode, null);
	}
		
	/**
	 * @description 根据第三方交易状态码获取对应的参数化支付消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @param params
	 * @return
	 */
	public static String getPaymentMessage(String thirdCode, Object[] params) {
		PaymentStatus payStatus = getPaymentStatus(thirdCode);
		return payStatus != null ? payStatus.getMessage(params) : StringUtils.EMPTY_STRING;
	}
	
}
