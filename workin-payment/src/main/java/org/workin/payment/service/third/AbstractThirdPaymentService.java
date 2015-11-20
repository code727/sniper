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

package org.workin.payment.service.third;

import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.enums.category.O2OTypes;
import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.StringUtils;
import org.workin.http.httpclient.v4.HttpClientTemplet;
import org.workin.payment.domain.Order;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.model.PaymentRequest;
import org.workin.payment.service.PaymentServiceSupport;
import org.workin.spring.context.ApplicationContextParameter;

/**
 * @description 第三方在线支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractThirdPaymentService extends PaymentServiceSupport
		implements ThirdPaymentService {
	
	/** 调用第三方接口所用到的HTTP调用模板 */
	@Autowired
	protected HttpClientTemplet httpClientTemplet; 
	
	/** 第三方应用上下文配置参数项 */
	@Autowired
	protected ApplicationContextParameter<Object, Object> contextParameter;
	
	public HttpClientTemplet getHttpClientTemplet() {
		return httpClientTemplet;
	}

	public void setHttpClientTemplet(HttpClientTemplet httpClientTemplet) {
		this.httpClientTemplet = httpClientTemplet;
	}

	public ApplicationContextParameter<Object, Object> getContextParameter() {
		return contextParameter;
	}

	public void setContextParameter(ApplicationContextParameter<Object, Object> contextParameter) {
		this.contextParameter = contextParameter;
	}
	
	protected void checkProperties() throws IllegalArgumentException {
		super.checkProperties();
		
		if (this.contextParameter == null)
			throw new IllegalArgumentException("Property 'contextParameter' must not be null.");
		
		if (this.httpClientTemplet == null)
			throw new IllegalArgumentException("Property 'httpClientTemplet' must not be null.");
	}

	public ResultModel<PaymentRequest> createPaymentRequest(Order order) throws Exception {
		ResultModel<PaymentRequest> resultModel = new ResultModel<PaymentRequest>();
		
		// 第一步：先验证支付订单的类型，如果是线下订单，则首先验证用户输入的账号密码是否有效
		if (order.getO2oType() == O2OTypes.OFFLINE.getKey()) {
			String loginName = orderService.findUserLoginName(order.getAccount(), order.getPassword());
			if (StringUtils.isNotEmpty(loginName))
				// 设置验证返回的登录名到订单中
				order.setLoginName(loginName);
			else {
				resultModel.setCode(SystemStatus.FAILED.getKey());
				resultModel.setMessage("ms.account.invalid");
				return resultModel;
			}
		}
		
		// 第二步：先保存订单记录
		CodeMessageModel result = orderService.save(order);
		if (SystemStatus.SUCCESS.getKey().equals(result.getCode())) {
			// 第三步：订单记录保存成功后，再根据订单保存支付记录 
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
	
	/**
	 * @description 根据订单执行支付服务的保存业务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paymentService
	 * @param order
	 * @return
	 * @throws Exception
	 */
	protected CodeMessageModel savePaymentByOrder(Order order) throws Exception {
		Payment payment = new Payment();
		payment.setOrderId(order.getOrderId());
		payment.setAmount(order.getAmount());
		// 所有支付的初始状态统一为"交易创建 "
		payment.setStatus(PaymentStatus.WAIT_BUYER_PAY.getKey());
		payment.setMessage(PaymentStatus.WAIT_BUYER_PAY.getMessage());
		return paymentService.save(payment);
	}
	
	/** 
	 * @description 根据订单创建第三方支付请求对象模型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @return 
	 */
	protected abstract PaymentRequest createPayParameters(Order order);
	
}
