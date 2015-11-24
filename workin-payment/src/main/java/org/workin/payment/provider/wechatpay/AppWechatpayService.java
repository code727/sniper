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

package org.workin.payment.provider.wechatpay;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.enums.category.SystemStatus;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.payment.domain.Order;
import org.workin.payment.domain.Payment;
import org.workin.payment.enums.payment.PaymentStatus;
import org.workin.payment.enums.payment.ThirdPaymentStatus;
import org.workin.payment.model.PaymentRequest;
import org.workin.payment.service.third.AbstractThirdPaymentService;

/**
 * @description APP版微信支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class AppWechatpayService extends AbstractThirdPaymentService {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		if (this.signature == null)
			this.signature = new WechatpaySignature();
	}
	
	@Override
	protected PaymentRequest createPaymentParameters(Order order, Map<String,String> parameters) {
		Map<String, Object> payParameters = MapUtils.newHashMap();
		// 公众账号ID
		payParameters.put("appid", contextParameter.getValue("wechatpay.corpid"));
		// 商户号
		payParameters.put("mch_id", contextParameter.getValue("wechatpay.mchid"));
		// 随机字符串，采用32位无符号全大写UUID
		payParameters.put("nonce_str", StringUtils.unsignedUUID(true));
		// 商品名称
		payParameters.put("body", order.getProductName());
		// 商品描述
		String description = order.getDescription();
		if (StringUtils.isNotBlank(description))
			payParameters.put("detail", order.getDescription());
		
		// 商户交易订单号
		payParameters.put("out_trade_no", order.getDescription());
		
		BigDecimal amount = order.getAmount();
		if (amount == null || NumberUtils.lessThanEquals(amount, 0)) {
			amount = new BigDecimal(order.getPrice().doubleValue() * order.getQuantity() * order.getDiscount());
			payParameters.put("total_fee", amount);
		}
		// 总金额
		payParameters.put("total_fee", amount);
		// 终端IP
		payParameters.put("spbill_create_ip", parameters.get("ip"));
		// 通知回调地址
		payParameters.put("notify_url", contextParameter.getValue("wechatpay.notify.url"));
		
		String tradeType = contextParameter.getValue("wechatpay.trade.type", String.class);
		// 交易类型
		payParameters.put("trade_type", tradeType);
		if ("JSAPI".equalsIgnoreCase(tradeType))
			payParameters.put("openid", parameters.get("openid"));
		
		// 签名
		payParameters.put("sign", signature.excute(payParameters));
		
		PaymentRequest request = new PaymentRequest();
		request.setUrl(contextParameter.getValue("wechatpay.request.url") + "?" + MapUtils.joinQueryString(payParameters));
		request.setOrderId(order.getOrderId());
		return request;
	}

	@Override
	public CodeMessageModel handlePaymentResponse(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		String thirdCode = paymentResponse.get("return_code");
		int status = ThirdPaymentStatus.getPaymentStatusCode(thirdCode);
		// 交易成功时更新支付记录
		if (status == PaymentStatus.TRADE_FINISHED.getKey())
			updatePayment(paymentResponse);
		else {
			/** 交易未成功时，则直接返回处理结果，不对支付记录做任何更新操作 */
			result.setCode(SystemStatus.FAILED.getKey());
			result.setMessage(paymentResponse.get("return_msg"));
		}
		
		return result;
	}
	
	protected CodeMessageModel updatePayment(Map<String, String> paymentResponse) throws Exception {
		CodeMessageModel result = new CodeMessageModel();
		String orderId = paymentResponse.get("out_trade_no");
		Payment payment = paymentService.findByOrderId(orderId);
		if (payment == null) {
			payment = new Payment();
			payment.setAmount(orderService.findByOrderId(orderId).getAmount());
		}
		payment.setOrderId(orderId);
		payment.setThirdOrderId(paymentResponse.get("transaction_id"));
		return result;
		
	}

}
