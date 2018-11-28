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

package org.sniper.commons.constant;

import java.util.Locale;

import org.sniper.commons.util.ClassUtils;
import org.sniper.commons.util.MessageUtils;
import org.sniper.commons.util.ObjectUtils;

/**
 * 嵌套的本地化常量抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractNestedLocaleConstant<K1, K2> extends AbstractConstant<K1, LocaleConstant<K2, String>>
		implements LocaleConstant<K1, LocaleConstant<K2, String>> {
		
	private static final long serialVersionUID = 1591864158028012713L;

	protected AbstractNestedLocaleConstant(K1 key, LocaleConstant<K2, String> value) {
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
		return this.getMessage(locale, params, this.value.getValue());
	}

	@Override
	public String getMessage(Object[] params, String defaultMessage) {
		return getMessage(null, params, defaultMessage);
	}
	
	@Override
	public String getMessage(Locale locale, Object[] params, String defaultMessage) {
		Class<?> nestedClass = this.value.getClass();
		// 从嵌套类同名的配置文件中获取消息
		String message = getClassMessage(nestedClass, locale, params);
		
		if (message == null) {
			Class<?> currentClass = this.getClass();
			// 从当前类同名的配置文件中获取消息
			message = getClassMessage(currentClass, locale, params);
			
			if (message == null) {
				// 从嵌套类所属包同名的配置文件中获取消息
				message = getPackageMessage(nestedClass, locale, params);
				if (message == null && !ObjectUtils.equals(ClassUtils.getPackageName(nestedClass),
						ClassUtils.getPackageName(currentClass))) {
					// 如果嵌套类和当前类不属于同一个包，则还需要从当前类所属包同名的配置文件中获取消息
					message = getPackageMessage(currentClass, locale, params);
				}
			}
		}
		
		return message != null ? message : defaultMessage;
	}
	
	/**
	 * 获取指定类级别资源文件内的参数化本地信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param locale
	 * @param params
	 * @return
	 */
	private String getClassMessage(Class<?> clazz, Locale locale, Object[] params) {
		return MessageUtils.getClassMessage(clazz, locale, this.value.getValue(), params, null);
	}
	
	/**
	 * 获取指定类所属包级别资源文件内的参数化本地信息
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param clazz
	 * @param locale
	 * @param params
	 * @return
	 */
	private String getPackageMessage(Class<?> clazz, Locale locale, Object[] params) {
		return MessageUtils.getPackageMessage(clazz, locale, this.value.getValue(), params, null);
	}

}
