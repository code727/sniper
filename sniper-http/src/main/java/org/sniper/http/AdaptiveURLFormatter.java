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
 * Create Date : 2015-7-10
 */

package org.sniper.http;

import java.util.Map;
import java.util.Set;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.MapUtils;
import org.sniper.commons.util.NetUtils;
import org.sniper.commons.util.StringUtils;
import org.sniper.templet.message.formatter.AdaptiveMessageFormatter;

/**
 * 自适应URL格式化处理器实现类
 * @author  Daniele
 * @version 1.0
 */
public class AdaptiveURLFormatter extends AdaptiveMessageFormatter {
	
	@SuppressWarnings("unchecked")
	@Override
	public String format(String url, Object param) {
		StringBuffer formatedUrl = new StringBuffer(super.format(url, param));
		if (param instanceof Map) {
			Map<String, Object> inputParam = (Map<String, Object>) param;
			
			/* 求输入参数分别与查询字符串以及Action字符串中查询名的差集，
			 * 多余的参数以及对应的值则直接拼接到URL字符串后面 */
			Set<String> nameSubtract = (Set<String>) MapUtils.keySubtract(inputParam, NetUtils.getParameterMap(url));
			nameSubtract = (Set<String>) CollectionUtils.subtract(nameSubtract, NetUtils.getAddressParameterNames(
					NetUtils.getAddress(url), getPrefix(), getSuffix()));
					
			if (CollectionUtils.isNotEmpty(nameSubtract)) {
				if (url.indexOf("?") < 0)
					formatedUrl.append("?");
				else
					formatedUrl.append("&");
				
				for (String name : nameSubtract) {
					formatedUrl.append(name).append("=").append(StringUtils.toString(inputParam.get(name))).append("&");
				}
							
				formatedUrl.deleteCharAt(formatedUrl.lastIndexOf("&"));
			}
		}
		
		return formatedUrl.toString();
	}
			
}
