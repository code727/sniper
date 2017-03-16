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

package org.workin.payment.service;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.enums.ebusiness.O2OType;
import org.workin.commons.enums.status.SystemStatus;
import org.workin.commons.model.CodeModel;
import org.workin.commons.model.impl.CodeDataModel;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.StringUtils;
import org.workin.http.httpclient.v4.HttpClientTemplet;
import org.workin.payment.Order;
import org.workin.payment.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.PaymentType;
import org.workin.payment.factory.PaymentFactory;
import org.workin.spring.context.ApplicationContextParameter;
import org.workin.support.generator.DateTimeIDGenerator;
import org.workin.support.generator.IDGenerator;
import org.workin.support.signature.Signature;

/**
 * 支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public abstract class AbstractPaymentService<T, P> implements PaymentService, InitializingBean {
	
	/** 支付接口HTTP调用模板 */
	@Autowired
	protected HttpClientTemplet paymentHttpTemplet; 
	
	/** 支付应用上下文配置参数项 */
	@Autowired
	protected ApplicationContextParameter<Object, Object> paymentContextParameters;
	
	/** 订单服务接口 */
	@Autowired
	protected OrderBaseService orderService;
	
	/** 支付服务接口 */
	@Autowired
	protected PaymentBaseService paymentService;
	
	@Autowired
	protected PaymentFactory paymentFactory;
		
	/** 订单号生成器 */
	protected IDGenerator orderIdGenerator;
		
	/** 签名器 */
	private Signature<P> signature;
	
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
	
	public Signature<P> getSignature() {
		return signature;
	}

	public void setSignature(Signature<P> signature) {
		this.signature = signature;
	}
	
	public PaymentFactory getPaymentFactory() {
		return paymentFactory;
	}

	public void setPaymentFactory(PaymentFactory paymentFactory) {
		this.paymentFactory = paymentFactory;
	}

	public IDGenerator getOrderIdGenerator() {
		return orderIdGenerator;
	}

	public void setOrderIdGenerator(IDGenerator orderIdGenerator) {
		this.orderIdGenerator = orderIdGenerator;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.orderService == null)
			throw new IllegalArgumentException("Property 'orderService' must not be null.");
		if (this.paymentService == null)
			throw new IllegalArgumentException("Property 'paymentService' must not be null.");
		if (this.paymentContextParameters == null)
			throw new IllegalArgumentException("Property 'paymentContextParameters' must not be null.");
		if (this.paymentHttpTemplet == null)
			throw new IllegalArgumentException("Property 'paymentHttpTemplet' must not be null.");
		if (this.paymentFactory == null)
			throw new IllegalArgumentException("Property 'paymentFactory' must not be null.");
		
		if (this.orderIdGenerator == null)
			this.orderIdGenerator = new DateTimeIDGenerator();
				
		this.signature = initSignature();
	}
	
	public HttpClientTemplet getPaymentHttpTemplet() {
		return paymentHttpTemplet;
	}

	public void setPaymentHttpTemplet(HttpClientTemplet paymentHttpTemplet) {
		this.paymentHttpTemplet = paymentHttpTemplet;
	}

	public ApplicationContextParameter<Object, Object> getPaymentContextParameters() {
		return paymentContextParameters;
	}

	public void setPaymentContextParameters(
			ApplicationContextParameter<Object, Object> paymentContextParameters) {
		this.paymentContextParameters = paymentContextParameters;
	}

	public CodeModel createRequest(Order order, Map<String,String> parameters) throws Exception {		
		// 第一步：先验证支付订单的类型，如果是线下订单，则首先验证用户输入的账号密码是否有效
		if (order.getO2oType() == O2OType.OFFLINE.getKey()) {
			String loginName = orderService.findLoginName(order.getAccount(), order.getPassword());
			if (StringUtils.isNotEmpty(loginName))
				// 设置验证返回的登录名到订单中
				order.setLoginName(loginName);
			else {
				CodeMessageModel result = new CodeMessageModel();
				result.setCode(SystemStatus.FAILED.getKey());
				result.setMessage("msg.error.invalid.payment.account");
				return result;
			}
		}
		
		PaymentType paymentType = PaymentType.get(order.getType());
		if (paymentType != null) {
			// 第二步：验证完订单支付类型有效后，则利用生成器生成编号
			String orderId = orderIdGenerator.generate(paymentType.getOrderPrefix(), paymentType.getOrderSuffix());
			order.setOrderId(orderId);
			
			// 第三步：先保存订单
			CodeMessageModel model = orderService.save(order);
			if (SystemStatus.SUCCESS.getKey().equals(model.getCode())) {
				// 第四步：订单保存成功后，再保存支付记录
				model = savePaymentByOrder(order);
				if (SystemStatus.SUCCESS.getKey().equals(model.getCode())) {
					// 第五步：根据订单和其它非订单参数项创建支付参数对象模型
					ResultModel<T> resultModel = createParameters(order, parameters);
					if (SystemStatus.SUCCESS.getKey().equals(resultModel.getCode())) {
						// 支付请求对象模型创建成功后，返回状态码和数据
						CodeDataModel<T> result = new CodeDataModel<T>();
						result.setCode(resultModel.getCode());
						result.setData(resultModel.getData());
						return result;
					} else {
						// 支付请求对象模型创建未成功时，返回创建结果状态码和消息
						model.setCode(resultModel.getCode());
						model.setMessage(resultModel.getMessage());
					}
				}
			} 
			// 第三、四和五步任意一步未成功时，则直接返回处理结果
			return model;
		} else {
			/* 无效的支付类型 */
			CodeMessageModel result = new CodeMessageModel();
			result.setCode(SystemStatus.FAILED.getKey());
			result.setMessage("msg.error.invalid.payment.type");
			return result;
		}
	}
	
	/**
	 * 根据订单执行支付服务的保存业务
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param paymentService
	 * @param order
	 * @return
	 * @throws Exception
	 */
	protected CodeMessageModel savePaymentByOrder(Order order) throws Exception {
		Payment payment = paymentFactory.create();
		payment.setOrderId(order.getOrderId());
		payment.setAmount(order.getAmount());
		// 所有支付的初始状态统一为"交易创建 "
		payment.setStatus(PaymentStatus.WAIT_BUYER_PAY.getKey());
		payment.setMessage(PaymentStatus.WAIT_BUYER_PAY.getMessage());
		return paymentService.save(payment);
	}
	
	/** 
	 * 初始化签名器
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return 
	 */
	protected abstract Signature<P> initSignature() throws Exception;
		
	/**
	 * 根据订单和其它非订单参数项创建支付参数对象模型
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @param parameters
	 * @return
	 */
	protected abstract ResultModel<T> createParameters(Order order,
			Map<String, String> parameters) throws Exception;
	
}
