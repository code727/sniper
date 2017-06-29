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

package org.sniper.payment.enums.payment;

import java.util.Map;

import org.sniper.commons.enums.AbstractNestableLocaleEnums;
import org.sniper.commons.enums.Enums;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 第三方支付状态枚举类
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
	public static final ThirdPaymentStatus ALIPAY_WAIT_BUYER_PAY = new ThirdPaymentStatus(
			"WAIT_BUYER_PAY", PaymentStatus.WAIT_BUYER_PAY);
	public static final ThirdPaymentStatus ALIPAY_TRADE_SUCCESS = new ThirdPaymentStatus(
			"TRADE_SUCCESS", PaymentStatus.TRADE_SUCCESS);
	public static final ThirdPaymentStatus ALIPAY_TRADE_FINISHED = new ThirdPaymentStatus(
			"TRADE_FINISHED", PaymentStatus.TRADE_FINISHED);
	public static final ThirdPaymentStatus ALIPAY_TRADE_CLOSED = new ThirdPaymentStatus(
			"TRADE_CLOSED", PaymentStatus.TRADE_CLOSED);
	public static final ThirdPaymentStatus ALIPAY_TRADE_PENDING = new ThirdPaymentStatus(
			"TRADE_PENDING", PaymentStatus.TRADE_PENDING);
	
	/** 微信交易状态枚举*/
	public static final ThirdPaymentStatus WECHATPAY_TRADE_SUCCESS = new ThirdPaymentStatus(
			"SUCCESS", PaymentStatus.TRADE_SUCCESS);
	public static final ThirdPaymentStatus WECHATPAY_TRADE_FAIL = new ThirdPaymentStatus(
			"FAIL", PaymentStatus.TRADE_CLOSED);
	
	public static final ThirdPaymentStatus WECHATPAY_NOAUTH = new ThirdPaymentStatus(
			"NOAUTH", PaymentStatus.NOAUTH);
	public static final ThirdPaymentStatus WECHATPAY_NOTENOUGH = new ThirdPaymentStatus(
			"NOTENOUGH", PaymentStatus.NOT_SUFFICIENT_FUNDS);
	public static final ThirdPaymentStatus WECHATPAY_ORDERPAID = new ThirdPaymentStatus(
			"ORDERPAID", PaymentStatus.ORDER_PAID);
	public static final ThirdPaymentStatus WECHATPAY_ORDERCLOSED = new ThirdPaymentStatus(
			"ORDERCLOSED", PaymentStatus.ORDER_CLOSED);
	public static final ThirdPaymentStatus WECHATPAY_SYSTEMERROR = new ThirdPaymentStatus(
			"SYSTEMERROR", PaymentStatus.SYSTEME_RROR);
	public static final ThirdPaymentStatus WECHATPAY_APPID_NOT_EXIST = new ThirdPaymentStatus(
			"APPID_NOT_EXIST", PaymentStatus.LACK_PARAMS_OF);
	public static final ThirdPaymentStatus WECHATPAY_MCHID_NOT_EXIST = new ThirdPaymentStatus(
			"MCHID_NOT_EXIST", PaymentStatus.LACK_PARAMS_OF);
	public static final ThirdPaymentStatus WECHATPAY_APPID_MCHID_NOT_MATCH = new ThirdPaymentStatus(
			"APPID_MCHID_NOT_MATCH", PaymentStatus.PARAMS_NOT_MATCH);
	public static final ThirdPaymentStatus WECHATPAY_LACK_PARAMS = new ThirdPaymentStatus(
			"LACK_PARAMS", PaymentStatus.LACK_PARAMS);
	public static final ThirdPaymentStatus WECHATPAY_OUT_TRADE_NO_USED = new ThirdPaymentStatus(
			"OUT_TRADE_NO_USED", PaymentStatus.DUPLICATION_OUT_TRADE_NO);
	public static final ThirdPaymentStatus WECHATPAY_SIGNERROR = new ThirdPaymentStatus(
			"SIGNERROR", PaymentStatus.SIGN_ERROR);
	public static final ThirdPaymentStatus WECHATPAY_XML_FORMAT_ERROR = new ThirdPaymentStatus(
			"XML_FORMAT_ERROR", PaymentStatus.FORMAT_ERROR);
	public static final ThirdPaymentStatus WECHATPAY_REQUIRE_POST_METHOD = new ThirdPaymentStatus(
			"REQUIRE_POST_METHOD", PaymentStatus.REQUIRE_HTTP_METHOD);
	public static final ThirdPaymentStatus WECHATPAY_POST_DATA_EMPTY = new ThirdPaymentStatus(
			"POST_DATA_EMPTY", PaymentStatus.DATA_EMPTY);
	public static final ThirdPaymentStatus WECHATPAY_NOT_UTF8 = new ThirdPaymentStatus(
			"NOT_UTF8", PaymentStatus.ENCODING_ERROR);
	
	static {
		PAY_STATUS_MAP = MapUtils.newHashMap();
		
		/** 支付宝交易状态映射集 */
		PAY_STATUS_MAP.put(ALIPAY_WAIT_BUYER_PAY.getKey(), ALIPAY_WAIT_BUYER_PAY.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_SUCCESS.getKey(), ALIPAY_TRADE_SUCCESS.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_FINISHED.getKey(), ALIPAY_TRADE_FINISHED.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_CLOSED.getKey(), ALIPAY_TRADE_CLOSED.getValue());
		PAY_STATUS_MAP.put(ALIPAY_TRADE_PENDING.getKey(), ALIPAY_TRADE_PENDING.getValue());
		
		/** 微信支付交易状态映射集 */
		PAY_STATUS_MAP.put(WECHATPAY_TRADE_SUCCESS.getKey(), WECHATPAY_TRADE_SUCCESS.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_TRADE_FAIL.getKey(), WECHATPAY_TRADE_FAIL.getValue());
		
		PAY_STATUS_MAP.put(WECHATPAY_NOAUTH.getKey(), WECHATPAY_NOAUTH.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_NOTENOUGH.getKey(), WECHATPAY_NOTENOUGH.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_ORDERPAID.getKey(), WECHATPAY_ORDERPAID.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_ORDERCLOSED.getKey(), WECHATPAY_ORDERCLOSED.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_SYSTEMERROR.getKey(), WECHATPAY_SYSTEMERROR.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_APPID_NOT_EXIST.getKey(), WECHATPAY_APPID_NOT_EXIST.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_MCHID_NOT_EXIST.getKey(), WECHATPAY_MCHID_NOT_EXIST.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_APPID_MCHID_NOT_MATCH.getKey(), WECHATPAY_APPID_MCHID_NOT_MATCH.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_LACK_PARAMS.getKey(), WECHATPAY_LACK_PARAMS.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_OUT_TRADE_NO_USED.getKey(), WECHATPAY_OUT_TRADE_NO_USED.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_SIGNERROR.getKey(), WECHATPAY_SIGNERROR.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_XML_FORMAT_ERROR.getKey(), WECHATPAY_XML_FORMAT_ERROR.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_REQUIRE_POST_METHOD.getKey(), WECHATPAY_REQUIRE_POST_METHOD.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_POST_DATA_EMPTY.getKey(), WECHATPAY_POST_DATA_EMPTY.getValue());
		PAY_STATUS_MAP.put(WECHATPAY_NOT_UTF8.getKey(), WECHATPAY_NOT_UTF8.getValue());
	}
	
	/**
	 * 根据第三方交易状态码获取对应的支付状态对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static PaymentStatus getPaymentStatus(String thirdCode) {
		return (PaymentStatus) PAY_STATUS_MAP.get(thirdCode);
	}
	
	/**
	 * 根据第三方交易状态码获取对应的支付状态码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static int getPaymentStatusCode(String thirdCode) {
		PaymentStatus payStatus = getPaymentStatus(thirdCode);
		return payStatus != null ? payStatus.getKey() : -1;
	}
	
	/**
	 * 根据第三方交易状态码获取对应的支付消息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdPayStatuCode
	 * @return
	 */
	public static String getPaymentMessage(String thirdCode) {
		return getPaymentMessage(thirdCode, null);
	}
		
	/**
	 * 根据第三方交易状态码获取对应的参数化支付消息
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
