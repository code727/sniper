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

package org.workin.payment.provider.alipay.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.StringUtils;
import org.workin.payment.domain.Order;
import org.workin.payment.provider.alipay.signature.AlipayRSASignature;
import org.workin.support.signature.AESignature;
import org.workin.support.signature.Signature;

/**
 * @description 阿里移动支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class AliAppPaymentService extends AlipayService<Map<String, Object>, Map<String, Object>> {
	
	@Override
	protected Signature<Map<String, Object>> initSignature() throws Exception {
		
		Signature<Map<String, Object>> signature = getSignature();
		if (signature == null) 
			signature = new AlipayRSASignature();
		
		/** 移动支付目前仅支持RSA加密签名,因此需要做如下两项检查  */
		if (!ArrayUtils.contains(APP_SIGN_TYPES, signature.getType())) 
			signature.setType(APP_SIGN_TYPES[0]);
		if (!(signature instanceof AESignature))
			throw new IllegalArgumentException("Signature must be instance of" + AESignature.class);
		
		String privateKey = ((AESignature<Map<String, Object>>) signature).getPrivateKey();
		String publicKey = ((AESignature<Map<String,Object>>) signature).getPublicKey();
		
		/* 检查/设置私钥 */
		if (StringUtils.isBlank(privateKey)) {
			privateKey = paymentContextParameters.getValue("alipay.app.privatekey", String.class);
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay app privatekey is required.");
			
			((AESignature<Map<String,Object>>) signature).setPrivateKey(privateKey);
		}
			
		/* 检查/设置公钥 */
		if (StringUtils.isBlank(publicKey)) {
			publicKey = paymentContextParameters.getValue("alipay.app.publickey", String.class);
			if (StringUtils.isBlank(privateKey))
				throw new IllegalArgumentException("Alipay app publickey is required.");
			
			((AESignature<Map<String,Object>>) signature).setPublicKey(publicKey); 
		}
		return signature;
	}
	
	@Override
	protected ResultModel<Map<String, Object>> createParameters(Order order, Map<String, String> parameters) throws Exception {
		Map<String, Object> paymentParameters = MapUtils.newHashMap();
		
		// 接口名称
		paymentParameters.put("service", paymentContextParameters.getValue("alipay.app.pay.service"));
		
		// 合作者身份ID 
		paymentParameters.put("partner", paymentContextParameters.getValue("alipay.app.partner"));
		
		// 卖家支付宝账号
		paymentParameters.put("seller_id", paymentContextParameters.getValue("alipay.app.seller.id"));
		
		// 通知回调地址
		paymentParameters.put("notify_url", paymentContextParameters.getValue("alipay.app.notify.url"));
		
		return null;
	}

	@Override
	public CodeMessageModel handleResponse(Map<String, String> response) throws Exception {
		return null;
	}

}
