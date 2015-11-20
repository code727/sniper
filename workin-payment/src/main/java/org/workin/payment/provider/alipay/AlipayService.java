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
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.payment.domain.Order;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.enums.validation.ThirdValidationResult;
import org.workin.payment.enums.validation.ValidationResult;
import org.workin.payment.model.PaymentRequest;
import org.workin.payment.service.third.AbstractThirdPaymentService;

/**
 * @description 阿里支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class AlipayService extends AbstractThirdPaymentService {
	
	@Override
	public PaymentRequest createPayParameters(Order order) {
		Map<String, Object> payParameters = MapUtils.newHashMap();
		// 接口名称
		payParameters.put("service", contextParameter.getValue("alipay.pay.service"));
		// 商户ID
		payParameters.put("partner", contextParameter.getValue("alipay.partner"));
		
		String sellerEmail = contextParameter.getValue("alipay.seller.email", String.class);
		if (StringUtils.isNotBlank(sellerEmail)) 
			// 卖家邮件
			payParameters.put("seller_email", sellerEmail);
		else 
			// 卖家ID
			payParameters.put("seller_id", contextParameter.getValue("alipay.seller.id"));
		
		// 通知URL
		payParameters.put("notify_url", contextParameter.getValue("alipay.notify.url"));
		
		String returnUrl = contextParameter.getValue("alipay.return.url", String.class);
		if (StringUtils.isNotBlank(returnUrl))
			// 返回URL
			payParameters.put("return_url", contextParameter.getValue("alipay.return.url"));
		
		// 商品名称
		payParameters.put("subject", order.getMercName());
		// 商品描述
		String description = order.getDescription();
		if (StringUtils.isNotBlank(description))
			payParameters.put("body", order.getDescription());
		
		// 商户交易订单号
		payParameters.put("out_trade_no", order.getOrderId());
		
		BigDecimal amount = order.getAmount();
		
		if (amount != null && amount.compareTo(new BigDecimal(0)) == 1)
			// 交易金额大于0时设置交易金额
			payParameters.put("total_fee", amount);
		else {
			BigDecimal price = order.getPrice();
			if (price == null || price.compareTo(new BigDecimal(0)) < 1)
				// 商品单价小于等于时设置单价为0.01元
				price = new BigDecimal(0.01);
			payParameters.put("price", price);
		}
		
		// 购买数量
		payParameters.put("quantity", NumberUtils.minLimit(order.getQuantity(), 1));
		// 支付类型
		payParameters.put("payment_type", 1);
		// 物流类型
		payParameters.put("logistics_type", contextParameter.getValue("alipay.logistics.type"));
		// 物流费用
		payParameters.put("logistics_fee", contextParameter.getValue("alipay.logistics.fee"));
		// 物流支付类型
		payParameters.put("logistics_payment", contextParameter.getValue("alipay.logistics.payment"));
		
		String signType = contextParameter.getValue("alipay.sign.type", String.class);
		if (StringUtils.isBlank(signType))
			signType = "MD5";
		
		// 签名
		payParameters.put("sign", signature.excute(payParameters, signType));
		// 签名类型
		payParameters.put("sign_type", signType);
		
		String inputCharset = contextParameter.getValue("alipay.input.charset", String.class);
		if (StringUtils.isBlank(inputCharset))
			inputCharset = MessageUtils.UTF8_ENCODING;
		
		// 商户系统与支付宝系统交互信息时使用的编码字符集
		payParameters.put("_input_charset", inputCharset);
		
		PaymentRequest request = new PaymentRequest();
		request.setUrl(MapUtils.joinQueryString(payParameters));
		request.setOrderId(order.getOrderId());
		return request;
	}

	@Override
	public CodeMessageModel handlePayResponse(Map<String, String> payResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("notify_id", payResponse.get("notify_id"));
		
		// 发送支付宝验证请求，并返回验证结果状态
		String status = httpClientTemplet.request("alipayNotify", parameters);
		// 再根据支付宝验证结果状态获取本系统的状态码
		String code = ThirdValidationResult.getValidationResultCode(status);
		
		if (ValidationResult.SUCCESS.getKey().equals(code)) {
			result = updatePayment(payResponse);
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
	protected CodeMessageModel updatePayment(Map<String, String> payResponse) throws Exception {
		String orderId = payResponse.get("out_trade_no");
		Payment payment = paymentService.findByOrderId(orderId);
		CodeMessageModel result = new CodeMessageModel();
		if (payment == null) {
			payment = new Payment();
			payment.setAmount(orderService.findByOrderId(orderId).getAmount());
		}
		payment.setOrderId(orderId);
		payment.setThirdOrderId(payResponse.get("trade_no"));
		payment.setPayAmount(new BigDecimal(payResponse.get("total_fee")));
		String thirdCode = payResponse.get("trade_status");
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
