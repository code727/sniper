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

package org.sniper.support.constant;

import java.util.Locale;

import org.sniper.commons.util.MessageUtils;

/**
 * 本地化常量抽象类
 * @author  Daniele
 * @version 1.0
 */
public abstract class AbstractLocaleConstant<K> extends AbstractConstant<K, String>
		implements LocaleConstant<K, String> {

	private static final long serialVersionUID = -1988017355814487635L;
	
	protected AbstractLocaleConstant(K key, String value) {
		super(key, value);
	}

	@Override
	public String getMessage() {
		return this.getMessage((Locale) null);
	}
	
	@Override
	public String getMessage(Locale locale) {
		return this.getMessage(locale, (Object[]) null);
	}
	
	@Override
	public String getMessage(String defaultMessage) {
		return this.getMessage((Locale) null, defaultMessage);
	}
	
	@Override
	public String getMessage(Locale locale, String defaultMessage) {
		return this.getMessage(locale, null, defaultMessage);
	}

	@Override
	public String getMessage(Object[] params) {
		return this.getMessage((Locale) null, params);
	}
	
	@Override
	public String getMessage(Locale locale, Object[] params) {
		return this.getMessage(locale, params, this.value);
	}

	@Override
	public String getMessage(Object[] params, String defaultMessage) {
		return getMessage(null, params, defaultMessage);
	}
	
	@Override
	public String getMessage(Locale locale, Object[] params, String defaultMessage) {
		/* 先从当前类同名的配置文件中获取，未获取到时再从与当前包同名的配置文件中获取 */
		String message = MessageUtils.getClassMessage(
				this.getClass(), locale, this.value, params, null);
		
		return message != null ? message : MessageUtils.getPackageMessage(
				this.getClass(), locale, this.value, params, defaultMessage);
	}

}
