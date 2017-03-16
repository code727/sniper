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
 * Create Date : 2015-12-11
 */

package org.workin.payment.provider.alipay.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.payment.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.service.AbstractPaymentService;

/**
 * 阿里支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public abstract class AlipayService<T, P> extends AbstractPaymentService<T, P> {
	
	/** APP支付所支持的签名类型组 */
	protected static final String[] APP_SIGN_TYPES = new String[] {"RSA"};
	
	/** 网银支付所支持的签名类型组 */
	protected static final String[] WEB_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};
	
	/** 手机网站支付所支持的签名类型组 */
	protected static final String[] WAP_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};
	
	/**
	 * 根据响应参数更新充值记录
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param payResponse
	 * @return
	 * @throws Exception
	 */
	protected CodeMessageModel updatePayment(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		String orderId = paymentResponse.get("out_trade_no");
		
		Payment payment = paymentService.findByOrderId(orderId);
		if (payment == null) {
			payment = paymentFactory.create();
			payment.setAmount(orderService.findByOrderId(orderId).getAmount());
		}
		
		payment.setOrderId(orderId);
		payment.setThirdOrderId(paymentResponse.get("trade_no"));
		payment.setPayAmount(new BigDecimal(paymentResponse.get("total_fee")));
		
		int status = payment.getStatus();
		/* 当前支付记录处于"未成功"以及"未完成"时，才继续往下处理 */
		if (status != PaymentStatus.TRADE_SUCCESS.getKey() && status != PaymentStatus.TRADE_FINISHED.getKey()) {
			String thirdCode = paymentResponse.get("trade_status");
			int returnStatus = ThirdPaymentStatus.getPaymentStatusCode(thirdCode);
			
			payment.setStatus(returnStatus);
			payment.setMessage(ThirdPaymentStatus.getPaymentMessage(thirdCode));
			status = payment.getStatus();
			// 只有等交易成功或完成后才记录支付时间
			if (status == PaymentStatus.TRADE_SUCCESS.getKey() || status == PaymentStatus.TRADE_FINISHED.getKey())
				payment.setPayTime(new Date());
			else
				payment.setPayTime(null);
			
			result = paymentService.update(payment);
		} else 
			// 不重复处理"已成功"或"已完成"的支付记录
			result.setCode(SystemStatus.FAILED.getKey());
				
		return result;
	}

}
