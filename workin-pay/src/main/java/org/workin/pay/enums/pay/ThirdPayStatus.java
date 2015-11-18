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

package org.workin.pay.enums.pay;

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
public final class ThirdPayStatus extends AbstractNestableLocaleEnums<String, Integer> {
	
	/** 支付状态映射集 */
	private static final Map<String, Enums<Integer, String>> PAY_STATUS_MAP;

	protected ThirdPayStatus(String key, Enums<Integer, String> value) {
		super(key, value);
	}
	
	/** 支付宝交易状态枚举*/
	public static final ThirdPayStatus ALIPAY_WAIT_BUYER_PAY = new ThirdPayStatus("WAIT_BUYER_PAY", PayStatus.WAIT_BUYER_PAY);
	public static final ThirdPayStatus ALIPAY_WAIT_SELLER_SEND_GOODS = new ThirdPayStatus("WAIT_SELLER_SEND_GOODS", PayStatus.WAIT_SELLER_SEND_GOODS);
	public static final ThirdPayStatus ALIPAY_WAIT_BUYER_CONFIRM_GOODS = new ThirdPayStatus("WAIT_BUYER_CONFIRM_GOODS", PayStatus.WAIT_BUYER_CONFIRM_GOODS);
	public static final ThirdPayStatus ALIPAY_TRADE_FINISHED = new ThirdPayStatus("TRADE_FINISHED", PayStatus.TRADE_FINISHED);
	public static final ThirdPayStatus ALIPAY_TRADE_CLOSED = new ThirdPayStatus("TRADE_CLOSED", PayStatus.TRADE_CLOSED);
	public static final ThirdPayStatus ALIPAY_MODIFY_TRADEBASE_TOTALFEE = new ThirdPayStatus("modify.tradeBase.totalFee", PayStatus.TRADE_CLOSED);
	
	
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
	public static PayStatus getPayStatus(String thirdCode) {
		return (PayStatus) PAY_STATUS_MAP.get(thirdCode);
	}
	
	/**
	 * @description 根据第三方交易状态码获取对应的支付状态码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static int getPayStatusCode(String thirdCode) {
		PayStatus payStatus = getPayStatus(thirdCode);
		return payStatus != null ? payStatus.getKey() : -1;
	}
	
	/**
	 * @description 根据第三方交易状态码获取对应的支付消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdPayStatuCode
	 * @return
	 */
	public static String getPayMessage(String thirdCode) {
		return getPayMessage(thirdCode, null);
	}
		
	/**
	 * @description 根据第三方交易状态码获取对应的参数化支付消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @param params
	 * @return
	 */
	public static String getPayMessage(String thirdCode, Object[] params) {
		PayStatus payStatus = getPayStatus(thirdCode);
		return payStatus != null ? payStatus.getMessage(params) : StringUtils.EMPTY_STRING;
	}
	
}
