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

package org.workin.payment.provider.wechatpay.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.workin.commons.model.impl.CodeMessageModel;
import org.workin.commons.model.impl.ResultModel;
import org.workin.payment.Order;
import org.workin.payment.WebPaymentRequest;
import org.workin.support.signature.Signature;

/**
 * @description 微信扫码支付服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
@Service
public class ScanningWechatpayService extends WechatpayService<WebPaymentRequest, Map<String, Object>> {
	
	@Override
	protected Signature<Map<String, Object>> initSignature() throws Exception {
		return null;
	}
	
	@Override
	protected ResultModel<WebPaymentRequest> createParameters(Order order, Map<String, String> parameters) throws Exception {
		return null;
	}

	@Override
	public CodeMessageModel handleResponse(Map<String, String> response) throws Exception {
		return null;
	}

	

}
