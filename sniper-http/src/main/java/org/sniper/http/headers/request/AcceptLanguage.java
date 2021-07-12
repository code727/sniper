/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017年8月15日
 */

package org.sniper.http.headers.request;

import java.util.Locale;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.http.headers.AbstractQualityFactor;

/**
 * 客户端声明的可以理解的自然语言
 * @author  Daniele
 * @version 1.0
 */
public class AcceptLanguage extends AbstractQualityFactor {
	
	private static final long serialVersionUID = 8329284651851196381L;
	
	private Locale locale;
	
	public AcceptLanguage() {
		this(Locale.getDefault());
	}
	
	public AcceptLanguage(Locale locale) {
		this(locale, MAX_DEFAULT_VALUE);
	}
	
	public AcceptLanguage(double qualityValue) {
		this(Locale.getDefault(), qualityValue);
	}
	
	public AcceptLanguage(Locale locale, double qualityValue) {
		setLocale(locale);
		setQualityValue(qualityValue);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		AssertUtils.assertNotNull(locale, "Accept locale must not be null");
		AssertUtils.assertTrue(StringUtils.isNotBlank(locale.getLanguage()) || StringUtils.isNotBlank(locale.getCountry()),
				"'language' and 'country' at least one can not be null or blank");
				
		this.locale = locale;
	}
	
	@Override
	public String toString() {
		String country = this.locale.getCountry();
		String language = this.locale.getLanguage();
		String qualityValue = super.toString();
		
		StringBuilder result = new StringBuilder();
		if (StringUtils.isNotBlank(country)) {
			result.append(country);
		}
		
		if (StringUtils.isNotBlank(language)) {
			if (result.length() > 0) {
				result.append(StringUtils.COMMA);
			}
			
			result.append(language);
		}
		
		if (qualityValue.length() > 0) {
			result.append(qualityValue);
		}
				
		return result.toString();
	}	
	
}
