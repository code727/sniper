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
 * Create Date : 2015-11-23
 */

package org.workin.payment.provider.wechatpay;

import java.util.Map;

import org.workin.commons.util.MapUtils;
import org.workin.commons.util.MessageUtils;
import org.workin.payment.signature.AbstractSignature;

/**
 * @description 微信支付签名实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WechatpaySignature extends AbstractSignature {

	@Override
	public String excute(Map<String, Object> paymentParameters) {
		return MessageUtils.encrypt(MapUtils.joinQueryString(paymentParameters), getType()).toUpperCase();
	}

}
