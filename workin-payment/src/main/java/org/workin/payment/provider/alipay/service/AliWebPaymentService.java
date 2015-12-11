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

package org.workin.payment.provider.alipay.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.ArrayUtils;
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
import org.workin.payment.provider.alipay.signature.AlipayMD5Signature;
import org.workin.support.signature.AESignature;
import org.workin.support.signature.SESignature;
import org.workin.support.signature.Signature;

/**
 * @description 阿里网银支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class AliWebPaymentService extends AlipayService<WebPaymentRequest, Map<String, Object>> {
	
	@Override
	protected Signature<Map<String, Object>> initSignature() throws Exception {
		SESignature<Map<String, Object>> signature = (SESignature<Map<String, Object>>) getSignature();
		if (signature == null)
			signature = new AlipayMD5Signature();
		
		if (!ArrayUtils.contains(APP_SIGN_TYPES, signature.getType())) 
			signature.setType(WEB_SIGN_TYPES[0]);
		
		/* 检查/设置私钥 */
		String privateKey = signature.getPrivateKey();
		if (StringUtils.isBlank(privateKey)) {
			privateKey = paymentContextParameters.getValue("alipay.web.privatekey", String.class);
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay web privatekey is required.");
			signature.setPrivateKey(privateKey);
		}
		
		/* 检查/设置公钥 */
		if (signature instanceof AESignature) {
			String publicKey = ((AESignature<Map<String,Object>>) signature).getPublicKey();
			if (StringUtils.isBlank(publicKey)) {
				publicKey = paymentContextParameters.getValue("alipay.web.publickey", String.class);
				if (StringUtils.isBlank(publicKey))
					throw new IllegalArgumentException("Alipay web publickey is required.");
				
				((AESignature<Map<String,Object>>) signature).setPublicKey(publicKey);
			}
		}	
		return signature;
	}
	
	@Override
	public ResultModel<WebPaymentRequest> createParameters(Order order, Map<String,String> parameters) {
		Map<String, Object> paymentParameters = MapUtils.newHashMap();
		// 接口名称
		paymentParameters.put("service", paymentContextParameters.getValue("alipay.web.pay.service"));
		
		// 商户ID
		paymentParameters.put("partner", paymentContextParameters.getValue("alipay.web.partner"));
		
		String sellerEmail = paymentContextParameters.getValue("alipay.web.seller.email", String.class);
		if (StringUtils.isNotBlank(sellerEmail)) 
			// 卖家邮件
			paymentParameters.put("seller_email", sellerEmail);
		else 
			// 卖家ID
			paymentParameters.put("seller_id", paymentContextParameters.getValue("alipay.web.seller.id"));
		
		// 通知回调地址
		paymentParameters.put("notify_url", paymentContextParameters.getValue("alipay.web.notify.url"));
		
		String returnUrl = paymentContextParameters.getValue("alipay.web.return.url", String.class);
		if (StringUtils.isNotBlank(returnUrl))
			// 返回URL
			paymentParameters.put("return_url", paymentContextParameters.getValue("alipay.web.return.url"));
		
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
		paymentParameters.put("logistics_type", paymentContextParameters.getValue("alipay.web.logistics.type"));
		// 物流费用
		paymentParameters.put("logistics_fee", paymentContextParameters.getValue("alipay.web.logistics.fee"));
		// 物流支付类型
		paymentParameters.put("logistics_payment", paymentContextParameters.getValue("alipay.web.logistics.payment"));
		
		Signature<Map<String, Object>> signature = getSignature();
		
		// 签名
		paymentParameters.put("sign", signature.excute(paymentParameters));
		// 签名类型
		paymentParameters.put("sign_type", signature.getType());
		
		String inputCharset = paymentContextParameters.getValue("alipay.web.input.charset", String.class);
		if (StringUtils.isBlank(inputCharset))
			inputCharset = MessageUtils.UTF8_ENCODING;
		
		// 商户系统与支付宝系统交互信息时使用的编码字符集
		paymentParameters.put("_input_charset", inputCharset);
		
		WebPaymentRequest request = new WebPaymentRequest();
		request.setUrl(paymentContextParameters.getValue("alipay.web.pay.url") + "?" + MapUtils.joinQueryString(paymentParameters));
		request.setOrderId(order.getOrderId());
		
		ResultModel<WebPaymentRequest> resultModel = new ResultModel<WebPaymentRequest>();
		resultModel.setDate(request);
		return resultModel;
	}

	@Override
	public CodeMessageModel handleResponse(Map<String, String> response) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("notify_id", response.get("notify_id"));
		
		// 发送支付宝验证请求，并返回验证结果状态
		String status = paymentHttpTemplet.request("alipayWebNotifyValidation", parameters);
		// 再根据支付宝验证结果状态获取本系统的状态码
		String code = ThirdValidationResult.getValidationResultCode(status);
		
		if (ValidationResult.SUCCESS.getKey().equals(code)) {
			result = updatePayment(response);
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
