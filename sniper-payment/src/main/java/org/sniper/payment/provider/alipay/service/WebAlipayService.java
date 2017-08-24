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

package org.sniper.payment.provider.alipay.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.sniper.commons.model.impl.CodeMessageModel;
import org.sniper.commons.model.impl.ResultModel;
import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.CodecUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.payment.Order;
import org.sniper.payment.PaymentUtils;
import org.sniper.payment.WebPaymentRequest;
import org.sniper.payment.enums.validation.ThirdValidationResult;
import org.sniper.payment.enums.validation.ValidationResult;
import org.sniper.payment.provider.alipay.signature.AlipayMD5Signature;
import org.sniper.support.signature.AsymmetricSignature;
import org.sniper.support.signature.SymmetricSignature;
import org.sniper.support.signature.Signature;

/**
 * 阿里网银支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Service
public class WebAlipayService extends AlipayService<WebPaymentRequest, Map<String, Object>> {
	
	@Override
	protected Signature<Map<String, Object>> initSignature() throws Exception {
		SymmetricSignature<Map<String, Object>> signature = (SymmetricSignature<Map<String, Object>>) getSignature();
		if (signature == null)
			signature = new AlipayMD5Signature();
		
		if (!ArrayUtils.contains(APP_SIGN_TYPES, signature.getType())) 
			signature.setType(WEB_SIGN_TYPES[0]);
		
		/* 检查/设置私钥 */
		String privateKey = signature.getPrivateKey();
		if (StringUtils.isBlank(privateKey)) {
			privateKey = paymentContextParameters.getString("alipay.web.privatekey");
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay web privatekey is required.");
			signature.setPrivateKey(privateKey);
		}
		
		/* 检查/设置公钥 */
		if (signature instanceof AsymmetricSignature) {
			String publicKey = ((AsymmetricSignature<Map<String, Object>>) signature).getPublicKey();
			if (StringUtils.isBlank(publicKey)) {
				publicKey = paymentContextParameters.getString("alipay.web.publickey");
				if (StringUtils.isBlank(publicKey))
					throw new IllegalArgumentException("Alipay web publickey is required.");
				
				((AsymmetricSignature<Map<String, Object>>) signature).setPublicKey(publicKey);
			}
		}	
		return signature;
	}
	
	@Override
	public ResultModel<WebPaymentRequest> createParameters(Order order, Map<String, String> parameters) {
		Map<String, Object> paymentParameters = MapUtils.newHashMap();
		
		// 接口名称
		paymentParameters.put("service",paymentContextParameters.getValue("alipay.web.pay.service"));
		
		// 商户ID
		paymentParameters.put("partner", paymentContextParameters.getValue("alipay.web.partner"));
		
		String sellerEmail = paymentContextParameters.getString("alipay.web.seller.email");
		if (StringUtils.isNotBlank(sellerEmail)) 
			// 卖家邮件
			paymentParameters.put("seller_email", sellerEmail);
		else 
			// 卖家ID
			paymentParameters.put("seller_id", paymentContextParameters.getString("alipay.web.seller.id"));
		
		// 通知回调地址
		paymentParameters.put("notify_url", paymentContextParameters.getString("alipay.web.notify.url"));
		
		String returnUrl = paymentContextParameters.getString("alipay.web.return.url");
		if (StringUtils.isNotBlank(returnUrl))
			// 返回URL
			paymentParameters.put("return_url", paymentContextParameters.getString("alipay.web.return.url"));
		
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
		
		String inputCharset = paymentContextParameters.getString("alipay.web.input.charset");
		if (StringUtils.isBlank(inputCharset))
			inputCharset = CodecUtils.UTF8_ENCODING;
		
		// 商户系统与支付宝系统交互信息时使用的编码字符集
		paymentParameters.put("_input_charset", inputCharset);
		
		WebPaymentRequest request = new WebPaymentRequest();
		request.setUrl(paymentContextParameters.getValue("alipay.web.pay.url") + "?" + MapUtils.joinQueryString(paymentParameters));
		request.setOrderId(order.getOrderId());
		
		ResultModel<WebPaymentRequest> resultModel = new ResultModel<WebPaymentRequest>();
		resultModel.setData(request);
		return resultModel;
	}

	@Override
	public CodeMessageModel handleResponse(Map<String, String> response) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Map<String, String> parameters = MapUtils.newHashMap();
		parameters.put("service", paymentContextParameters.getString("alipay.web.validation.service"));
		parameters.put("partner", paymentContextParameters.getString("alipay.web.partner"));
		parameters.put("notify_id", response.get("notify_id"));
		
		// 发送支付宝验证请求，并返回验证结果状态
		String status = paymentHttpTemplate.request("webAlipayNotifyValidation", parameters);
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
	
}
