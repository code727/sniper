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
 * Create Date : 2015-11-25
 */

package org.sniper.payment.provider.wechatpay.enums;

/**
 * 支付交易类型枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum TradeType {
	/** 公众号支付 */
	JSAPI("JSAPI"), 
	/** 原生扫码支付 */
	NATIVE("FAIL"),
	/** APP支付 */
	APP("APP"),
	/** 刷卡支付 */ 
	MICROPAY("MICROPAY");
	
	private String type;
	
	private TradeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return this.getType();
	}

}
