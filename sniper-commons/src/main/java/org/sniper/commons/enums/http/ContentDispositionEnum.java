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
 * Create Date : 2017-8-22
 */

package org.sniper.commons.enums.http;

import java.util.Map;

import org.sniper.commons.util.MapUtils;

/**
 * Content-Disposition消息头枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum ContentDispositionEnum {
	
	/** 内联形式 */
	INLINE("inline"),
	
	/** 附件形式 */
	ATTACHMENT("attachment"),
	
	/** 表单数据 */
	FORM_DATA("form-data");
	
	private static final Map<String, ContentDispositionEnum> mappings = MapUtils.newHashMap(3);
	
	static {
		for (ContentDispositionEnum dispositionType : values()) {
			mappings.put(dispositionType.name, dispositionType);
		}
	}
	
	/** 类型名称 */
	private String name;
	
	private ContentDispositionEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * 将指定的名称解析成DispositionType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public static ContentDispositionEnum resolve(String name) {
		return (name != null ? mappings.get(name.toLowerCase()) : null);
	}

	/**
	 * 判断指定的名称是否匹配一个DispositionType对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param name
	 * @return
	 */
	public boolean matches(String name) {
		return this.name.equalsIgnoreCase(name);
	}

}
