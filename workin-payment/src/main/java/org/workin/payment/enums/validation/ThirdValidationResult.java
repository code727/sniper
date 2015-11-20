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

package org.workin.payment.enums.validation;

import java.util.Map;

import org.workin.commons.enums.AbstractNestableLocaleEnums;
import org.workin.commons.enums.Enums;
import org.workin.commons.util.MapUtils;

/**
 * @description 第三方验证结果枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ThirdValidationResult extends AbstractNestableLocaleEnums<String, String> {
	
	/** 验证结果映射集 */
	private static final Map<String, Enums<String, String>> VALIDATION_RESULTS_MAP;
	
	protected ThirdValidationResult(String key, Enums<String, String> value) {
		super(key, value);
	}

	/** 支付宝验证结果枚举*/
	public static final ThirdValidationResult ALIPAY_VALIDATION_INVALID = new ThirdValidationResult("invalid", ValidationResult.INVALID);
	public static final ThirdValidationResult ALIPAY_VALIDATION_FAILED = new ThirdValidationResult("false", ValidationResult.FAILED);
	public static final ThirdValidationResult ALIPAY_VALIDATION_SUCCESS = new ThirdValidationResult("true", ValidationResult.SUCCESS);

	static {
		VALIDATION_RESULTS_MAP = MapUtils.newHashMap();
		
		VALIDATION_RESULTS_MAP.put(ALIPAY_VALIDATION_INVALID.getKey(), ALIPAY_VALIDATION_INVALID.getValue());
		VALIDATION_RESULTS_MAP.put(ALIPAY_VALIDATION_FAILED.getKey(), ALIPAY_VALIDATION_FAILED.getValue());
		VALIDATION_RESULTS_MAP.put(ALIPAY_VALIDATION_SUCCESS.getKey(), ALIPAY_VALIDATION_SUCCESS.getValue());
	}
	
	/**
	 * @description 根据第三方交易验证结果码获取对应的验证结果对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static ValidationResult getValidationResult(String thirdCode) {
		return (ValidationResult) VALIDATION_RESULTS_MAP.get(thirdCode);
	}
	
	/**
	 * @description 根据第三方交易验证结果码获取本系统的验证结果码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static String getValidationResultCode(String thirdCode) {
		ValidationResult validationResult = getValidationResult(thirdCode);
		return validationResult != null ? validationResult.getKey() : ValidationResult.INVALID.getKey();
	}
	
	/**
	 * @description 根据第三方交易验证结果码获取对应的验证信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @return
	 */
	public static String getValidationMessage(String thirdCode) {
		return getValidationMessage(thirdCode, null);
	}
	
	/**
	 * @description 根据第三方交易验证结果码获取对应的参数化验证信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param thirdCode
	 * @param params
	 * @return
	 */
	public static String getValidationMessage(String thirdCode, Object[] params) {
		ValidationResult validationResult = getValidationResult(thirdCode);
		return validationResult != null ? validationResult.getMessage(params)
				: ValidationResult.INVALID.getMessage(params);
	}
}
