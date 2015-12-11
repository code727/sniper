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

import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.service.AbstractPaymentService;

/**
 * @description 阿里支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AlipayService<T, P> extends AbstractPaymentService<T, P> {
	
	/** APP支付所支持的签名类型组 */
	protected static final String[] APP_SIGN_TYPES = new String[] {"RSA"};
	
	/** 网银支付所支持的签名类型组 */
	protected static final String[] WEB_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};
	
	/** 手机网站支付所支持的签名类型组 */
	protected static final String[] WAP_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};
	
	/**
	 * @description 根据响应参数更新充值记录
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
			payment = new Payment();
			payment.setAmount(orderService.findByOrderId(orderId).getAmount());
		}
		payment.setOrderId(orderId);
		payment.setThirdOrderId(paymentResponse.get("trade_no"));
		payment.setPayAmount(new BigDecimal(paymentResponse.get("total_fee")));
		String thirdCode = paymentResponse.get("trade_status");
		payment.setStatus(ThirdPaymentStatus.getPaymentStatusCode(thirdCode));
		payment.setMessage(ThirdPaymentStatus.getPaymentMessage(thirdCode));
		// 只有等交易完成后才记录支付时间
		if (PaymentStatus.TRADE_FINISHED.getKey() == payment.getStatus())
			payment.setPayTime(new Date());
		else
			payment.setPayTime(null);
		
		if (payment.getId() != null)
			result = paymentService.update(payment);
		else
			result = paymentService.save(payment);
		
		return result;
	}

}
