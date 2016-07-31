/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-7-31
 */

package org.workin.templet.message.resolver;

import java.util.Locale;

/**
 * @description 本地化消息解析器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractLocaleMessageResolver extends
		AbstractMessageResolver implements LocaleMessageResolver {

	@Override
	public String getMessage(String key, Locale locale) {
		return getMessage(key, locale, key);
	}

	@Override
	public String getMessage(String key, Locale locale, String defaultMessage) {
		return getMessage(key, locale, null, defaultMessage);
	}

	@Override
	public String getMessage(String key, Locale locale, Object param) {
		return getMessage(key, locale, param, key);
	}

	@Override
	public String getMessage(String key, Locale locale, Object[] params) {
		return getMessage(key, locale, params, key);
	}
	
	@Override
	public String getMessage(String key, Object[] params, String defaultMessage) {
		return getMessage(key, null, params, defaultMessage);
	}
	
	@Override
	public String getMessage(String key, Locale locale, Object param, String defaultMessage) {
		return getMessage(key, locale, new Object[] { param }, key);
	}

}
