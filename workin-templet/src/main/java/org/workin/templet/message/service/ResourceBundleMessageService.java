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

package org.workin.templet.message.service;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.StringUtils;
import org.workin.support.context.ApplicationContextHolder;

/**
 * java.util.ResourceBundle消息解析服务实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ResourceBundleMessageService implements MessageService {
	
	/** 资源的基础名称组 */
	private String[] baseNames;

	/**
	 * 设置资源的基础名称
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName
	 */
	public void setBaseName(String baseName) {
		this.baseNames = StringUtils.splitTrim(baseName, ",");
	}
	
	/**
	 * 获取资源的基础名称组
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public String[] getBaseNames() {
		return baseNames;
	}

	/**
	 * 根据基础名称和本地化对象获取对应的java.util.ResourceBundle对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param baseName
	 * @param locale
	 * @return
	 */
	protected ResourceBundle getResourceBundle(String baseName, Locale locale) {
		if (locale == null)
			locale = Locale.getDefault();
		
		String attribute = baseName + "_" + locale;
		ResourceBundle besourceBundle = (ResourceBundle) ApplicationContextHolder.getAttribute(attribute);
		if (besourceBundle == null) {
			try {
				besourceBundle = ResourceBundle.getBundle(baseName, locale);
				ApplicationContextHolder.setAttribute(attribute, besourceBundle);
			} catch (MissingResourceException e) {}
			
		}
		
		return besourceBundle;
	}

	@Override
	public String getMessageByKey(String key) {
		return getMessageByKey(key, null);
	}

	@Override
	public String getMessageByKey(String key, Locale locale) {
		if (locale == null)
			locale = Locale.getDefault();
		
		String message = key;
		if (ArrayUtils.isNotEmpty(baseNames)) {
			ResourceBundle resourceBundle;
			for (String baseName : baseNames) {
				resourceBundle = getResourceBundle(baseName, locale);
				if (resourceBundle != null && resourceBundle.keySet().contains(key)) {
					message = resourceBundle.getString(key);
					break;
				}
			}
		}
		
		return message;
	}

}
