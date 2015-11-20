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
	

}
