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
 * Create Date : 2015-11-17
 */

package org.sniper.commons.enums;

import java.util.Locale;

import org.sniper.commons.util.MessageUtils;

/**
 * 本地化消息枚举对象抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractLocaleEnum<K> extends AbstractEnum<K, String> implements LocaleEnum<K, String> {

	private static final long serialVersionUID = -1988017355814487635L;

	protected AbstractLocaleEnum(K key, String value) {
		super(key, value);
	}

	@Override
	public String getMessage() {
		return this.getMessage(this.value);
	}

	@Override
	public String getMessage(String defaultMessage) {
		return this.getMessage(null, defaultMessage);
	}

	@Override
	public String getMessage(Object[] params) {
		return this.getMessage(params, this.value);
	}

	@Override
	public String getMessage(Object[] params, String defaultMessage) {
		/* 先从当前类同名的配置文件中获取，未获取到时再从与当前包同名的配置文件中获取 */
		String message = MessageUtils.getClassMessage(this.getClass(),
				Locale.getDefault(), this.value, params, null);
		
		return message != null ? message : MessageUtils.getPackageMessage(
				this.getClass(), Locale.getDefault(), this.value, params, defaultMessage);
	}

}
