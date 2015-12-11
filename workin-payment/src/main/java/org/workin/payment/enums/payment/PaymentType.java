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
 * Create Date : 2015-11-18
 */

package org.workin.payment.enums.payment;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 支付类型枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum PaymentType {
	
	/** 阿里移动支付 */
	ALI_MOBILE_PAYMENT("AMP"),
	
	/** 阿里手机网站支付 */
	ALI_MOBILE_SITE_PAYMENT("AMSP"),
	
	/** 阿里网银支付 */
	ALI_ONLINE_BANKING_PAYMENT("AOBP"),
	
	/** 微信公共号支付 */
	WECHAT_PUBLIC_NO_PAYMENT("WPNP"),
	
	/** 微信扫码支付 */
	WECHAT_SCANNING_CODE_PAYMENT("WSCP"),
	
	/** 微信APP支付 */
	WECHAT_APP_PAYMENT("WAP");
	
	/** 订单编号前缀标识 */
	private String orderPrefix;
	
	/** 订单编号前缀标识 */
	private String orderSuffix;
	
	private PaymentType(String orderFlage) {
        this(orderFlage, true);
	}
	
	private PaymentType(String orderFlage, boolean prefix) {
        this((prefix ? orderFlage : null), (prefix ? null : orderFlage));
	}
	
	private PaymentType(String orderPrefix, String orderSuffix) {
        this.orderPrefix = StringUtils.safeString(orderPrefix);
        this.orderSuffix = StringUtils.safeString(orderSuffix);
	}
	
	/**
	 * @description 获取支付类型标识
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public int payType() {
		return this.ordinal();
	}

	/**
	 * @description 获取当前支付类型的订单号前缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOrderPrefix() {
		return this.orderPrefix;
	}
	
	/**
	 * @description 获取当前支付类型的订单号后缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String getOrderSuffix() {
		return orderSuffix;
	}

	/**
	 * @description 获取对应支付类型的订单号前缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static String getOrderPrefix(int type) {
		PaymentType[] values = PaymentType.values();
		for (PaymentType payTypes : values) {
			if (payTypes.ordinal() == type)
				return payTypes.getOrderPrefix();
		}
		return StringUtils.EMPTY_STRING;
	}
	
	/**
	 * @description 获取对应支付类型的订单号后缀
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static String getOrderSuffix(int type) {
		PaymentType[] values = PaymentType.values();
		for (PaymentType payTypes : values) {
			if (payTypes.ordinal() == type)
				return payTypes.getOrderSuffix();
		}
		return StringUtils.EMPTY_STRING;
	}
	
	/**
	 * @description 判断指定的支付类型是否有效
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static boolean isValid(int type) {
		return get(type) != null;
	}
	
	/**
	 * @description 获取指定类型的对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param type
	 * @return
	 */
	public static PaymentType get(int type) {
		return ArrayUtils.get(PaymentType.values(), type);
	}
		
}
