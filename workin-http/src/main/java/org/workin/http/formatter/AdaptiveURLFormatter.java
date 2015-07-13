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

package org.workin.http.formatter;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

import org.workin.commons.util.CollectionUtils;
import org.workin.commons.util.MapUtils;
import org.workin.commons.util.NetUtils;
import org.workin.support.message.formatter.AdaptiveMessageFormatter;

/**
 * @description 自适应URL格式化处理器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class AdaptiveURLFormatter extends AdaptiveMessageFormatter implements URLFormatter<Object> {
	
	@SuppressWarnings("unchecked")
	@Override
	public String format(String url, Object param) {
		StringBuffer formatedUrl = new StringBuffer(super.format(url, param));
		
		if (param instanceof Map) {
			Map<String, Object> inputParam = (Map<String, Object>) param;
			
			/* 求输入参数分别与查询字符串以及Action字符串中查询名的交集，
			 * 多余的参数以及对于的值则直接拼接到URL字符串后面 */
			Collection<String> names = MapUtils.keySubtract(inputParam, NetUtils.getParameterMap(url));
			names = CollectionUtils.subtract(names, NetUtils
					.getActionParameterNames(NetUtils.getActionString(url), super.getPrefix(), super.getSuffix()));
			if (CollectionUtils.isNotEmpty(names)) {
				if (url.indexOf("?") < 0)
					formatedUrl.append("?");
				else
					formatedUrl.append("&");
				
				for (String name : names) 
					formatedUrl.append(name).append("=").append(inputParam.get(name)).append("&");
				formatedUrl.deleteCharAt(formatedUrl.lastIndexOf("&"));
			}
		}
		return formatedUrl.toString();
	}
	
	public static void main(String[] args) throws MalformedURLException {
		AdaptiveURLFormatter formatter = new AdaptiveURLFormatter();
		Map<String, Object> param = MapUtils.newHashMap();
		param.put("model", "sport");
		param.put("name", "dubin");
		param.put("age", 32);
		param.put("address", "dujiangyan");
		System.out.println(formatter.format("http://163.com/{model}/yj", param));
	}

}
