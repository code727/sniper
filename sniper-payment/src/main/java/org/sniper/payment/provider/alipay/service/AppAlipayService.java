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
 * Create Date : 2015-11-30
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
import org.sniper.commons.util.StringUtils;
import org.sniper.payment.Order;
import org.sniper.payment.PaymentUtils;
import org.sniper.payment.enums.validation.ThirdValidationResult;
import org.sniper.payment.enums.validation.ValidationResult;
import org.sniper.payment.provider.alipay.signature.AlipayRSASignature;
import org.sniper.support.signature.AsymmetricSignature;
import org.sniper.support.signature.Signature;

/**
 * 阿里移动支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Service
public class AppAlipayService extends AlipayService<Map<String, Object>, Map<String, Object>> {
	
	@Override
	protected Signature<Map<String, Object>> initSignature() throws Exception {
		
		Signature<Map<String, Object>> signature = getSignature();
		if (signature == null) 
			signature = new AlipayRSASignature();
		
		/* 移动支付目前仅支持RSA加密签名,因此需要做如下两项检查  */
		if (!ArrayUtils.contains(APP_SIGN_TYPES, signature.getType())) 
			signature.setType(APP_SIGN_TYPES[0]);
		if (!(signature instanceof AsymmetricSignature))
			throw new IllegalArgumentException("Signature must be instance of" + AsymmetricSignature.class);
		
		String privateKey = ((AsymmetricSignature<Map<String, Object>>) signature).getPrivateKey();
		String publicKey = ((AsymmetricSignature<Map<String, Object>>) signature).getPublicKey();
		
		/* 检查/设置私钥 */
		if (StringUtils.isBlank(privateKey)) {
			privateKey = paymentContextParameters.getString("alipay.app.privatekey");
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay app privatekey is required.");
			
			((AsymmetricSignature<Map<String, Object>>) signature).setPrivateKey(privateKey);
					
		}
			
		/* 检查/设置公钥 */
		if (StringUtils.isBlank(publicKey)) {
			publicKey = paymentContextParameters.getString("alipay.app.publickey");
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay app publickey is required.");
			
			((AsymmetricSignature<Map<String, Object>>) signature).setPublicKey(publicKey);
		}
		return signature;
	}
	
	@Override
	protected ResultModel<Map<String, Object>> createParameters(Order order, Map<String, String> parameters) throws Exception {
		Map<String, Object> paymentParameters = MapUtils.newLinkedHashMap();
		paymentParameters.putAll(createRequiredParameters(order, parameters));
		paymentParameters.putAll(createUnrequiredParameters(order, parameters));
		
		Signature<Map<String, Object>> signature = getSignature();
		
		// 签名和签名类型必须为LinkedHashMap的最后两个参数
		paymentParameters.put("sign", signature.excute(paymentParameters));
		paymentParameters.put("sign_type", signature.getType());
		
		ResultModel<Map<String, Object>> resultModel = new ResultModel<Map<String, Object>>();
		resultModel.setData(paymentParameters);
		return resultModel;
	}
	
	/**
	 * 创建必填参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @param parameters
	 * @return
	 */
	protected Map<String, Object> createRequiredParameters(Order order, Map<String, String> parameters) {
		Map<String, Object> requiredParameters = MapUtils.newLinkedHashMap();
		// 接口名称
		requiredParameters.put("service", paymentContextParameters.getValue("alipay.app.pay.service"));
		// 合作者身份ID 
		requiredParameters.put("partner", paymentContextParameters.getValue("alipay.app.partner"));
		
		String inputCharset = paymentContextParameters.getString("alipay.app.input.charset");
		if (StringUtils.isBlank(inputCharset))
			inputCharset = CodecUtils.UTF8_ENCODING;
		
		// 参数编码字符集
		requiredParameters.put("_input_charset", inputCharset);
		// 服务器异步通知页面路径 
		requiredParameters.put("notify_url", paymentContextParameters.getValue("alipay.app.notify.url"));
		// 商户网站唯一订单号
		requiredParameters.put("out_trade_no", order.getOrderId());
		// 商品名称
		requiredParameters.put("subject", order.getProductName());
		// 支付类型
		requiredParameters.put("payment_type", 1);
		// 卖家支付宝账号
		requiredParameters.put("seller_id", paymentContextParameters.getValue("alipay.app.seller.id"));
		
		PaymentUtils.prepare(order);
		// 总金额
		requiredParameters.put("total_fee", order.getAmount().setScale(2, BigDecimal.ROUND_DOWN));
		// 商品详情
		if (StringUtils.isNotBlank(order.getDescription()))
			requiredParameters.put("body", order.getDescription());
		else 
			requiredParameters.put("body", order.getProductName());
		
		return requiredParameters;
	}
	
	/**
	 * 创建非必填参数项
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param order
	 * @param parameters
	 * @return
	 */
	protected Map<String, Object> createUnrequiredParameters(Order order, Map<String, String> parameters) {
		Map<String, Object> unrequiredParameters =  MapUtils.newLinkedHashMap();
		
		// 客户端号
		String appId = parameters.get("app_id");
		if (StringUtils.isNotBlank(appId))
			unrequiredParameters.put("app_id", appId);
		
		// 客户端来源 
		String appenv = parameters.get("appenv");
		if (StringUtils.isNotBlank(appenv))
			unrequiredParameters.put("appenv", appenv);
		
		// 商品类型
		String goodsType = parameters.get("goods_type");
		if (StringUtils.isNotBlank(goodsType))
			unrequiredParameters.put("goods_type", goodsType);
		
		// 是否发起实名校验
		String rnCheck = parameters.get("rn_check");
		if (StringUtils.isNotBlank(rnCheck)) 
			unrequiredParameters.put("rn_check", rnCheck);
		
		// 未付款交易的超时时间
		String itBPay = parameters.get("it_b_pay");
		if (StringUtils.isNotBlank(itBPay)) 
			unrequiredParameters.put("it_b_pay", itBPay);
		
		// 授权令牌
		String externToken = parameters.get("extern_token");
		if (StringUtils.isNotBlank(externToken)) 
			unrequiredParameters.put("extern_token", externToken);
		
		// 商户业务扩展参数
		String outContext = parameters.get("out_context");
		if (StringUtils.isNotBlank(outContext)) 
			unrequiredParameters.put("out_context", outContext);
		
		return unrequiredParameters;
	}

	@Override
	public CodeMessageModel handleResponse(Map<String, String> response) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("service", paymentContextParameters.getString("alipay.app.validation.service"));
		parameters.put("partner", paymentContextParameters.getString("alipay.app.partner"));
		parameters.put("notify_id", response.get("notify_id"));
		
		// 发送支付宝通知验证请求，并返回验证结果状态
		String status = paymentHttpTemplate.request("appAlipayNotifyValidation", parameters);
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
