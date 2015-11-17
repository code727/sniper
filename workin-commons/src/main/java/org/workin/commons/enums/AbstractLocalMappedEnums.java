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
 * Create Date : 2015年11月17日
 */

package org.workin.commons.enums;

import java.util.Locale;

import org.workin.commons.util.MessageUtils;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractLocalMappedEnums<K1, K2> extends
		AbstractNestableEnums<K1, K2, String> implements LocalEnums<K1> {

	protected AbstractLocalMappedEnums(K1 key, Enums<K2, String> value) {
		super(key, value);
	}

	@Override
	public String getMessage() {
		return this.getMessage(getValue().getValue());
	}

	@Override
	public String getMessage(String defaultMessage) {
		return this.getMessage(null, defaultMessage);
	}

	@Override
	public String getMessage(Object[] params) {
		return this.getMessage(params, getValue().getValue());
	}

	@Override
	public String getMessage(Object[] params, String defaultMessage) {
		/* 先从当前类同名的配置文件中获取，未获取到时再从与当前包同名的配置文件中获取 */
		String message = MessageUtils.getClassMessage(this.getClass(),
				Locale.getDefault(), getValue().getValue(), params, null);
		
		return message != null ? message : MessageUtils.getClassMessage(
				this.getClass(), Locale.getDefault(), getValue().getValue(), params, defaultMessage);
	}

}
