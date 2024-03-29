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
 * Create Date : 2016-7-28
 */

package org.sniper.templet.message.source;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.commons.util.MessageUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 基于{@link java.util.ResourceBundle}的消息源实现类
 * @author  Daniele
 * @version 1.0
 */
public class ResourceBundleMessageSource implements MessageSource {
	
	/** 资源的基础名称组 */
	private String[] baseNames;
		
	public ResourceBundleMessageSource() {
		this.baseNames = new String[] { MessageUtils.DEFAULT_I18N_BASENAME };
	}

	/**
	 * 设置资源的基础名称
	 * @author Daniele 
	 * @param baseName
	 */
	public void setBaseName(String baseName) {
		this.baseNames = StringUtils.splitTrim(baseName, StringUtils.COMMA);
	}
	
	/**
	 * 获取资源的基础名称组
	 * @author Daniele 
	 * @return
	 */
	public String[] getBaseNames() {
		return baseNames;
	}

	@Override
	public String getMessageByKey(String key) {
		return getMessageByKey(key, null);
	}

	@Override
	public String getMessageByKey(String key, Locale locale) {
		if (ArrayUtils.isNotEmpty(baseNames)) {
			for (String baseName : baseNames) {
				ResourceBundle resourceBundle = getResourceBundle(baseName, locale);
				if (resourceBundle != null && resourceBundle.containsKey(key)) {
					return resourceBundle.getString(key);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 根据基础名称和本地化对象获取对应的{@link java.util.ResourceBundle}对象
	 * @author Daniele 
	 * @param baseName
	 * @param locale
	 * @return
	 */
	protected ResourceBundle getResourceBundle(String baseName, Locale locale) {
		try {
			return MessageUtils.getResourceBundle(baseName, locale);
		} catch (MissingResourceException e) {
			return null;
		}
	}

}
