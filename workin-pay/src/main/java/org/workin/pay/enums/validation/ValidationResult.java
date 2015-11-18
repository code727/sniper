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

package org.workin.pay.enums.validation;

import org.workin.commons.enums.AbstractLocaleEnums;

/**
 * @description 验证结果枚举类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class ValidationResult extends AbstractLocaleEnums<String> {

	protected ValidationResult(String key, String value) {
		super(key, value);
	}
	
	/** 验证无效 */
	public static final ValidationResult INVALID = new ValidationResult("0000", "msg.pay.validation.invalid");
	
	/** 验证无效 */
	public static final ValidationResult FAILED = new ValidationResult("-0001", "msg.pay.validation.failed");
	
	/** 验证成功 */
	public static final ValidationResult SUCCESS = new ValidationResult("0001", "msg.pay.validation.success");

}
