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

package org.workin.payment.provider.alipay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.payment.PaymentUtils;
import org.workin.payment.WebPaymentRequest;
import org.workin.payment.domain.Order;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.enums.validation.ThirdValidationResult;
import org.workin.payment.enums.validation.ValidationResult;
import org.workin.payment.service.AbstractWorkinPaymentService;

/**
 * @description Web版阿里支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class WebAlipayService extends AbstractWorkinPaymentService<WebPaymentRequest> {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.signature == null)
			this.signature = new AlipaySignature();
	}
	
	@Override
	public ResultModel<WebPaymentRequest> createPaymentParameters(Order order, Map<String,String> parameters) {
		Map<String, Object> paymentParameters = MapUtils.newHashMap();
		// 接口名称
		paymentParameters.put("service", paymentContextParameters.getValue("alipay.pay.service"));
		// 商户ID
		paymentParameters.put("partner", paymentContextParameters.getValue("alipay.partner"));
		
		String sellerEmail = paymentContextParameters.getValue("alipay.seller.email", String.class);
		if (StringUtils.isNotBlank(sellerEmail)) 
			// 卖家邮件
			paymentParameters.put("seller_email", sellerEmail);
		else 
			// 卖家ID
			paymentParameters.put("seller_id", paymentContextParameters.getValue("alipay.seller.id"));
		
		// 通知回调地址
		paymentParameters.put("notify_url", paymentContextParameters.getValue("alipay.notify.url"));
		
		String returnUrl = paymentContextParameters.getValue("alipay.return.url", String.class);
		if (StringUtils.isNotBlank(returnUrl))
			// 返回URL
			paymentParameters.put("return_url", paymentContextParameters.getValue("alipay.return.url"));
		
		// 商品名称
		paymentParameters.put("subject", order.getProductName());
		// 商品描述
		String description = order.getDescription();
		if (StringUtils.isNotBlank(description))
			paymentParameters.put("body", order.getDescription());
		
		// 商户交易订单号
		paymentParameters.put("out_trade_no", order.getOrderId());
		
		PaymentUtils.prepare(order);
		BigDecimal amount = order.getAmount();
		/* 交易金额大于0时设置交易金额参数，否则设置单价参数 */
		if (NumberUtils.greaterThan(amount, 0))
			paymentParameters.put("total_fee", amount);
		else
			paymentParameters.put("price", order.getPrice());
		
		// 购买数量
		paymentParameters.put("quantity", NumberUtils.minLimit(order.getQuantity(), 1));
		// 支付类型
		paymentParameters.put("payment_type", 1);
		// 物流类型
		paymentParameters.put("logistics_type", paymentContextParameters.getValue("alipay.logistics.type"));
		// 物流费用
		paymentParameters.put("logistics_fee", paymentContextParameters.getValue("alipay.logistics.fee"));
		// 物流支付类型
		paymentParameters.put("logistics_payment", paymentContextParameters.getValue("alipay.logistics.payment"));
		
		// 签名
		paymentParameters.put("sign", signature.excute(paymentParameters, ""));
		// 签名类型
		paymentParameters.put("sign_type", signature.getType());
		
		String inputCharset = paymentContextParameters.getValue("alipay.input.charset", String.class);
		if (StringUtils.isBlank(inputCharset))
			inputCharset = MessageUtils.UTF8_ENCODING;
		
		// 商户系统与支付宝系统交互信息时使用的编码字符集
		paymentParameters.put("_input_charset", inputCharset);
		
		WebPaymentRequest request = new WebPaymentRequest();
		request.setUrl(paymentContextParameters.getValue("alipay.request.url") + "?" + MapUtils.joinQueryString(paymentParameters));
		request.setOrderId(order.getOrderId());
		
		ResultModel<WebPaymentRequest> resultModel = new ResultModel<WebPaymentRequest>();
		resultModel.setDate(request);
		return resultModel;
	}

	@Override
	public CodeMessageModel handlePaymentResponse(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("notify_id", paymentResponse.get("notify_id"));
		
		// 发送支付宝验证请求，并返回验证结果状态
		String status = paymentHttpTemplet.request("alipayNotify", parameters);
		// 再根据支付宝验证结果状态获取本系统的状态码
		String code = ThirdValidationResult.getValidationResultCode(status);
		
		if (ValidationResult.SUCCESS.getKey().equals(code)) {
			result = updatePayment(paymentResponse);
		} else {
			/* 验证未成功时，则直接返回状态码和验证信息，不做充值记录的处理 */
			result.setCode(code);
			result.setMessage(ThirdValidationResult.getValidationMessage(status));
		}
		return result;
	}
	
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
