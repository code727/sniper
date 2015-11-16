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
 * Create Date : 2015年11月16日
 */

package org.workin.pay.provider.alipay.service;

import java.util.Map;

import org.workin.commons.util.MapUtils;
import org.workin.pay.domain.Order;
import org.workin.pay.domain.PayRequest;
import org.workin.pay.service.AbstractPayService;

/**
 * @description 阿里支付服务类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AlipayService extends AbstractPayService {
	
	@Override
	public PayRequest createPayParameters(Order order) {
		Map<String, Object> payParameters = MapUtils.newHashMap();
		payParameters.put("service", contextParameter.getValue("alipay.pay.service"));
		payParameters.put("partner", contextParameter.getValue("alipay.partner"));
		payParameters.put("notify_url", contextParameter.getValue("alipay.partner"));
		
		String sign = signature.excute(payParameters);
		payParameters.put("sign", sign);
		payParameters.put("sign_type", signature.getType());
		payParameters.put("_input_charset", signature.getCharset());
		
		PayRequest request = new PayRequest();
		request.setUrl(MapUtils.joinURLParameters(payParameters));
		request.setOrderId(order.getOrderId());
		return request;
	}

}
