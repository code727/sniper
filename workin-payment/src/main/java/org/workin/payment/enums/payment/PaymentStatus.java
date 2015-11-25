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

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * @description 支付状态枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class PaymentStatus extends AbstractLocaleEnums<Integer> {
	
	protected PaymentStatus(Integer key, String value) {
		super(key, value);
	}
	
	/** 交易创建 */
	public static final PaymentStatus WAIT_BUYER_PAY = new PaymentStatus(0, "msg.wait_buyer_pay");
	
	/** 买家付款成功 */
	public static final PaymentStatus WAIT_SELLER_SEND_GOODS = new PaymentStatus(1, "msg.wait_seller_send_goods");
	
	/** 卖家发货成功 */
	public static final PaymentStatus WAIT_BUYER_CONFIRM_GOODS = new PaymentStatus(2, "msg.wait_buyer_confirm_goods");
	
	/** 交易成功结束 */
	public static final PaymentStatus TRADE_FINISHED = new PaymentStatus(3, "msg.trade_finished");
	
	/** 交易关闭 */
	public static final PaymentStatus TRADE_CLOSED = new PaymentStatus(4,"msg.trade_closed");
	
	/** 修改交易价格 */
	public static final PaymentStatus MODIFY_TRADEBASE_TOTALFEE = new PaymentStatus(5, "msg.modify_tradebase_totalfee");
	
	
	/** 商户无此接口权限 */
	public static final PaymentStatus NOAUTH = new PaymentStatus(-1, "msg.seller_noauth");
	
	/** 签名错误 */
	public static final PaymentStatus SIGN_ERROR = new PaymentStatus(-2, "msg.sign_error");
	
	/** 缺少XXX参数 */
	public static final PaymentStatus LACK_PARAMS_OF = new PaymentStatus(-3, "msg.lack_params_of");
	
	/** 缺少参数 */
	public static final PaymentStatus LACK_PARAMS = new PaymentStatus(-4, "msg.lack_params");
	
	/** XXX和XXX参数不匹配 */
	public static final PaymentStatus PARAMS_NOT_MATCH = new PaymentStatus(-5, "msg.params_not_match");
	
	/** 未使用HTTP XXX方法传递参数 */
	public static final PaymentStatus REQUIRE_HTTP_METHOD = new PaymentStatus(-6, "msg.require_http_method");
	
	/** 编码格式错误，请使用XXX编码格式 */
	public static final PaymentStatus ENCODING_ERROR = new PaymentStatus(-7, "msg.encoding_error");
	
	/** XXX格式错误 */
	public static final PaymentStatus FORMAT_ERROR = new PaymentStatus(-8, "msg.format_error");
	
	/** 数据为空 */
	public static final PaymentStatus DATA_EMPTY = new PaymentStatus(-9, "msg.data_empty");	
	
	/** 系统错误 */
	public static final PaymentStatus SYSTEME_RROR = new PaymentStatus(-10, "msg.system_error");
	
	/** 用户帐号余额不足 */
	public static final PaymentStatus NOT_SUFFICIENT_FUNDS = new PaymentStatus(-11, "msg.user_not_sufficient_funds");
	
	/** 商户订单已支付 */
	public static final PaymentStatus ORDER_PAID = new PaymentStatus(-12, "msg.order_paid");
	
	/** 商户订单号重复 */
	public static final PaymentStatus DUPLICATION_OUT_TRADE_NO = new PaymentStatus(-13, "msg.duplication_out_trade_no");
	
	/** 当前订单已关闭 */
	public static final PaymentStatus ORDER_CLOSED = new PaymentStatus(-14, "msg.order_closed");
	
	/** 其它错误 */
	public static final PaymentStatus OTHER_ERROR = new PaymentStatus(-999, "msg.other_error");
}
