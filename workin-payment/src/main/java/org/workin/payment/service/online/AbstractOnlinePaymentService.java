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
 * Create Date : 2015-11-16
 */

package org.workin.payment.service.online;

import org.workin.commons.model.impl.ResultModel;
import org.workin.payment.domain.Order;
import org.workin.payment.model.PaymentRequest;
import org.workin.payment.service.PaymentServiceSupport;

/**
 * @description 支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractOnlinePaymentService extends PaymentServiceSupport implements ThirdOnlinePaymentService {
	
	/** 线上支付服务接口 */
	protected OnlinePaymentService paymentService;
	
	public OnlinePaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	protected void checkProperties() throws IllegalArgumentException {
		super.checkProperties();
		
		if (this.paymentService == null)
			throw new IllegalArgumentException("Property 'paymentService' must not be null.");
	}

	@Override
	public ResultModel<PaymentRequest> createPaymentRequest(Order order) throws Exception {
		return super.createPaymentRequest(paymentService, order);
	}

}
