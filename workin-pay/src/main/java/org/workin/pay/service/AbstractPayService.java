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

package org.workin.pay.service;

import org.workin.commons.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.http.httpclient.v4.HttpClientTemplet;
import org.workin.pay.domain.Order;
import org.workin.pay.domain.PayRequest;
import org.workin.pay.domain.Payment;
import org.workin.pay.enums.pay.PayStatus;
import org.workin.pay.signature.Signature;
import org.workin.spring.beans.CheckableInitializingBean;
import org.workin.spring.context.ApplicationContextParameter;

/**
 * @description 支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractPayService extends CheckableInitializingBean implements PayService {
	
	/** 订单服务接口 */
	protected OrderService orderService;
	
	/** 支付服务接口 */
	protected PaymentService paymentService;
	
	/** 签名器 */
	protected Signature signature;
	
	protected HttpClientTemplet httpClientTemplet; 
		
	/** 应用上下文配置参数项 */
	protected ApplicationContextParameter<Object, Object> contextParameter;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
	public ApplicationContextParameter<Object, Object> getContextParameter() {
		return contextParameter;
	}

	public void setContextParameter(ApplicationContextParameter<Object, Object> contextParameter) {
		this.contextParameter = contextParameter;
	}

	protected void checkProperties() throws IllegalArgumentException {
		if (this.orderService == null)
			throw new IllegalArgumentException("Property 'orderService' must not be null.");
		
		if (this.paymentService == null)
			throw new IllegalArgumentException("Property 'paymentService' must not be null.");
		
		if (this.contextParameter == null)
			throw new IllegalArgumentException("Property 'contextParameter' must not be null.");
		
		if (this.httpClientTemplet == null)
			throw new IllegalArgumentException("Property 'httpClientTemplet' must not be null.");
	}

	@Override
	public ResultModel<PayRequest> createPayRequest(Order order) throws Exception {
		ResultModel<PayRequest> resultModel = new ResultModel<PayRequest>();
		
		// 第一步：先保存订单记录
		CodeMessageModel result = orderService.save(order);
		if (SystemStatus.SUCCESS.getKey().equals(result.getCode())) {
			// 第二步：订单记录保存成功后，再根据订单保存支付记录 
			result = savePaymentByOrder(order);
			if (SystemStatus.SUCCESS.getKey().equals(result.getCode())) {
				/* 上述两步完成后，再根据订单记录创建支付请求后包装在返回数据对象模型中  */
				resultModel.setCode(SystemStatus.SUCCESS.getKey());
				resultModel.setDate(createPayParameters(order));
			} else {
				resultModel.setCode(SystemStatus.FAILED.getKey());
				resultModel.setMessage(result.getMessage());
			}
		} else {
			resultModel.setCode(SystemStatus.FAILED.getKey());
			resultModel.setMessage(result.getMessage());
		}
		
		return resultModel;
	}
	
	protected CodeMessageModel savePaymentByOrder(Order order) throws Exception {
		Payment payment = new Payment();
		payment.setOrderId(order.getOrderId());
		payment.setAmount(order.getAmount());
		// 所有支付的初始状态统一为"交易创建 "
		payment.setStatus(PayStatus.WAIT_BUYER_PAY.getKey());
		payment.setMessage(PayStatus.WAIT_BUYER_PAY.getMessage());
		return paymentService.save(payment);
	}
	
	/**
	 * @description 根据订单创建支付请求参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @return
	 */
	protected abstract PayRequest createPayParameters(Order order);

}
