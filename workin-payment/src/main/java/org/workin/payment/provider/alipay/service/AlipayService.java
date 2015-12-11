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
 * Create Date : 2015-12-11
 */

package org.workin.payment.provider.alipay.service;

import org.workin.payment.service.AbstractPaymentService;

/**
 * @description 阿里支付服务抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AlipayService<T, P> extends AbstractPaymentService<T, P> {
	
	/** APP支付所支持的签名类型组 */
	protected static final String[] APP_SIGN_TYPES = new String[] {"RSA"};
	
	/** 网银支付所支持的签名类型组 */
	protected static final String[] WEB_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};
	
	/** 手机网站支付所支持的签名类型组 */
	protected static final String[] WAP_SIGN_TYPES = new String[] {"MD5", "RSA", "DSA"};

}
