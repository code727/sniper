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

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * @description 支付状态枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class PayStatus extends AbstractLocaleEnums<Integer> {

	protected PayStatus(Integer key, String value) {
		super(key, value);
	}

	/** 交易创建 */
	public static final PayStatus WAIT_BUYER_PAY = new PayStatus(0, "msg.wait_buyer_pay");
	
	/** 买家付款成功 */
	public static final PayStatus WAIT_SELLER_SEND_GOODS = new PayStatus(1, "msg.wait_seller_send_goods");
	
	/** 卖家发货成功 */
	public static final PayStatus WAIT_BUYER_CONFIRM_GOODS = new PayStatus(2, "msg.wait_buyer_confirm_goods");
	
	/** 交易成功结束 */
	public static final PayStatus TRADE_FINISHED = new PayStatus(3, "msg.trade_finished");
	
	/** 交易关闭 */
	public static final PayStatus TRADE_CLOSED = new PayStatus(4,"msg.trade_closed");
	
	/** 修改交易价格 */
	public static final PayStatus MODIFY_TRADEBASE_TOTALFEE = new PayStatus(5, "msg.modify.tradebase.totalfee");
	

}
