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
 * Create Date : 2015-12-10
 */

package org.sniper.payment.provider.alipay.signature;

import java.util.Map;
import java.util.TreeMap;

import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.SecurityUtils;
import org.sniper.support.signature.SymmetricSignature;

/**
 * 阿里支付MD5签名实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AlipayMD5Signature extends SymmetricSignature<Map<String, Object>> {
	
	public AlipayMD5Signature() {
		setType(SecurityUtils.MD5_ALGORITHM_NAME);
	}

	@Override
	public String excute(Map<String, Object> parameters) {
		// 按参数名升序排列
		if (!(parameters instanceof TreeMap))
			parameters = MapUtils.newTreeMap(parameters);
		
		parameters.remove("sign_type");
		parameters.remove("sign");
		String queryString = MapUtils.joinQueryString(parameters) + getPrivateKey();
		return SecurityUtils.md5(queryString);
	}

}
