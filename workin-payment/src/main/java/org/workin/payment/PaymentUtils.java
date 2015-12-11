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
 * Create Date : 2015-11-30
 */

package org.workin.payment;

import java.math.BigDecimal;

import org.workin.commons.util.NumberUtils;
import org.workin.payment.domain.Order;

/**
 * @description 支付工具类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PaymentUtils {
	
	/**
	 * @description 准备订单
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 */
	public static void prepare(Order order) {
		int quantity = NumberUtils.minLimit(order.getQuantity(), 1);
		// 折扣限制在[0.01, 1]闭合区间
		double discount = NumberUtils.maxLimit(NumberUtils.minLimit(order.getDiscount(), 0.01), 1);
		BigDecimal price = NumberUtils.safeBigDecimal(order.getPrice());
		
		// 单价不能小于0.01元
		if (NumberUtils.lessThan(price, 0.01)) 
			price = new BigDecimal(0.01);
		
		order.setQuantity(quantity);
		order.setDiscount(discount);
		order.setPrice(price);
		
		BigDecimal amount = NumberUtils.safeBigDecimal(order.getAmount());
		// 应付的实际金额
		BigDecimal payAmount = price.multiply(new BigDecimal(quantity)).multiply(new BigDecimal(discount));
		
		// 金额小于等于0时，则金额=单价*数量*折扣
		if (NumberUtils.lessThanEquals(amount, 0) || NumberUtils.lessThan(amount, payAmount)) 
			amount = payAmount;
		
		order.setAmount(amount);
	}
	
}
