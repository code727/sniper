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
 * Create Date : 2015-7-7
 */

package org.workin.http.register;

import java.util.List;
import java.util.Map;

import org.workin.commons.util.CollectionUtils;
import org.workin.http.HttpForm;
import org.workin.http.converter.HttpFormConverter;

/**
 * @description 默认的HTTP表单注册器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultHttpFormRegister implements ConvertableHttpFormRegister {
	
	/** 名称 -表单映射集 */
	private Map<String, HttpForm> formMap;
	
	/** 名称 - URL映射集 */
	private Map<String, String> formUrlMap;
	
	/** 表单转换器 */
	private HttpFormConverter converter;

	@Override
	public void setFormMap(Map<String, HttpForm> formMap) {
		this.formMap = formMap;
		this.formUrlMap = this.converter.convertUrlMap(formMap);
	}
	
	@Override
	public Map<String, HttpForm> getFormMap() {
		return this.formMap;
	}
	
	@Override
	public void setConverter(HttpFormConverter converter) {
		if (converter != null)
			this.converter = converter;
	}

	@Override
	public HttpForm find(String name) {
		return this.formMap != null ? this.formMap.get(name) : null;
	}

	@Override
	public String findURL(String name) {
		return this.formUrlMap != null ? this.formUrlMap.get(name) : null;
	}
	
	@Override
	public List<HttpForm> getForms() {
		return this.formMap != null ? CollectionUtils.newArrayList(
				this.formMap.values()) : null;
	}

	@Override
	public List<String> getURL() {
		return this.formUrlMap != null ? CollectionUtils.newArrayList(
				this.formUrlMap.values()) : null;
	}

}
