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
 * Create Date : 2015-11-25
 */

package org.sniper.payment.provider.wechatpay.parser;

import org.sniper.payment.PaymentParser;

/**
 * 微信支付解析器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface WechatpayParser extends PaymentParser {
	
	/** 返回状态码变量名 */
	public static final String RETURN_CODE = "return_code";
	
	/** 返回信息变量名 */
	public static final String RETURN_MSG = "return_msg";
	
	/** 公众账号ID变量名 */
	public static final String APPID = "appid";
	
	/** 商户号变量名 */
	public static final String MCH_ID = "mch_id";
	
	/** 设备号变量名 */
	public static final String DEVICE_INFO = "device_info";
	
	/** 随机字符串变量名 */
	public static final String NONCE_STR = "nonce_str";
	
	/** 签名变量名 */
	public static final String SIGN = "sign";
	
	/** 业务结果变量名 */
	public static final String RESULT_CODE = "result_code";
		
	/** 预支付交易会话标识变量名 */
	public static final String PREPAY_ID = "prepay_id";
	
	/** 二维码链接变量名 */
	public static final String CODE_URL = "code_url";
	
	/** 错误代码变量名 */
	public static final String ERR_CODE = "err_code";
	
	/** 错误代码描述变量名 */
	public static final String ERR_CODE_DES = "err_code_des";
	
	/** 变量名 */
	public static final String OPENID = "openid";
	
	/** 是否关注公众账号变量名 */
	public static final String IS_SUBSCRIBE = "is_subscribe";
	
	/** 交易类型变量名 */
	public static final String TRADE_TYPE = "trade_type";
	
	/** 付款银行变量名 */
	public static final String BANK_TYPE = "bank_type";
	
	/** 总金额变量名 */
	public static final String TOTAL_FEE = "total_fee";
	
	/** 货币种类变量名 */
	public static final String FEE_TYPE = "fee_type";
	
	/** 现金支付金额变量名 */
	public static final String CASH_FEE = "cash_fee";
	
	/** 现金支付货币类型变量名 */
	public static final String CASH_FEE_TYPE = "cash_fee_type";
	
	/** 代金券或立减优惠金额变量名 */
	public static final String COUPON_FEE = "coupon_fee";
	
	/** 代金券或立减优惠使用数量变量名 */
	public static final String COUPON_COUNT = "coupon_count";
	
	/** 代金券或立减优惠ID变量名 */
	public static final String COUPON_ID_$N = "coupon_id_$n";
	
	/** 单个代金券或立减优惠支付金额变量名 */
	public static final String COUPON_FEE_$N = "coupon_fee_$n";
	
	/** 微信支付订单号变量名 */
	public static final String TRANSACTION_ID = "transaction_id";
	
	/** 商户订单号变量名 */
	public static final String OUT_TRADE_NO = "out_trade_no";
	
	/** 商家数据包变量名 */
	public static final String ATTACH = "attach";
	
	/** 支付完成时间变量名 */
	public static final String TIME_END = "time_end";
	
}
