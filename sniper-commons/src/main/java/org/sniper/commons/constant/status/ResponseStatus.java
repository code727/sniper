/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-11-16
 */

package org.sniper.commons.constant.status;

import java.util.Map;

import org.sniper.commons.constant.AbstractLocaleConstant;

/**
 * 响应状态常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class ResponseStatus extends AbstractLocaleConstant<Integer> {
	
	private static final long serialVersionUID = -2847353128385702823L;
	
	/** 异常，系统故障和未知错误等 */
	public static final ResponseStatus EXCEPTION = new ResponseStatus(-1, "response.status.exception");
		
	/** 成功 */
	public static final ResponseStatus SUCCESS = new ResponseStatus(0, "response.status.success");
	
	/** 失败，业务逻辑错误 */
	public static final ResponseStatus FAILED = new ResponseStatus(1, "response.status.failed");
	
	private static final Map<Integer, ResponseStatus> mappings;
	
	static {
		mappings = createMapping(ResponseStatus.class);
	}
		
	protected ResponseStatus(Integer key, String value) {
		super(key, value);
	}
		
	/**
	 * 将指定的键解析成ResponseStatus常量
	 * @param key
	 * @return
	 */
	public static ResponseStatus resolve(Integer key) {
		return mappings.get(key);
	}
				
}
