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
 * Create Date : 2015年12月21日
 */

package org.workin.payment.factory;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.StringUtils;
import org.workin.payment.Payment;
import org.workin.support.bean.BeanUtils;

/**
 * @description 支付对象工厂默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultPaymentFactory implements PaymentFactory, InitializingBean {
	
	/** 支付对象的实现类型 */
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = StringUtils.trim(type);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isBlank(type))
			throw new IllegalArgumentException("Payment type must not be null or blank.");
	}

	@Override
	public Payment create() {
		try {
			return BeanUtils.create(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
