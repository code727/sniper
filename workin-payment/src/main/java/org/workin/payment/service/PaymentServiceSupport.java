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
 * Create Date : 2015-11-20
 */

package org.workin.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.payment.signature.Signature;
import org.workin.spring.beans.CheckableInitializingBean;

/**
 * @description 支付服务支持抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class PaymentServiceSupport extends CheckableInitializingBean {
	
	/** 订单服务接口 */
	@Autowired
	protected OrderBaseService orderService;
	
	/** 支付服务接口 */
	@Autowired
	protected PaymentBaseService paymentService;
	
	/** 签名器 */
	@Autowired(required = false)
	protected Signature signature;
	
	public OrderBaseService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderBaseService orderService) {
		this.orderService = orderService;
	}
	
	public PaymentBaseService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentBaseService paymentService) {
		this.paymentService = paymentService;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
	protected void checkProperties() throws IllegalArgumentException {
		if (this.orderService == null)
			throw new IllegalArgumentException("Property 'orderService' must not be null.");
		if (this.paymentService == null)
			throw new IllegalArgumentException("Property 'paymentService' must not be null.");
	}
	
}
