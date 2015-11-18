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

package org.workin.pay.provider.alipay.service;

import java.math.BigDecimal;
import java.util.Map;

import org.workin.commons.util.MapUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.commons.util.NumberUtils;
import org.workin.commons.util.StringUtils;
import org.workin.pay.domain.Order;
import org.workin.pay.domain.PayRequest;
import org.workin.pay.service.AbstractPayService;
import org.workin.support.model.impl.CodeableMessageModel;

/**
 * @description 阿里支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AlipayService extends AbstractPayService {
	
	@Override
	public PayRequest createPayParameters(Order order) {
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
		
		PayRequest request = new PayRequest();
		request.setUrl(MapUtils.joinURLParameters(payParameters));
		request.setOrderId(order.getOrderId());
		return request;
	}

	@Override
	public CodeableMessageModel notify(Map<String, String> payNotice) throws Exception {
		// 
		Map<String, Object> parameters = MapUtils.newHashMap();
		parameters.put("notify_id", payNotice.get("notify_id"));
		
//		String httpClientTemplet.request("alipayNotify", parameters);
		return null;
	}

}
