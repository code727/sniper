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
 * 业务状态常量类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public final class BizStatus extends AbstractLocaleConstant<String> {
	
	private static final long serialVersionUID = -2847353128385702823L;
		
	/** 业务执行成功 */
	public static final BizStatus SUCCESS = new BizStatus("success", "biz.status.success");
	
	/** 业务执行失败，业务逻辑之类引起的 */
	public static final BizStatus FAILED = new BizStatus("failed", "biz.status.failed");
	
	/** 异常，系统故障之类引起的 */
	public static final BizStatus EXCEPTION = new BizStatus("exception", "biz.status.exception");
	
	private static final Map<String, BizStatus> mappings;
	
	static {
		mappings = createMapping(BizStatus.class);
	}
		
	protected BizStatus(String key, String value) {
		super(key, value);
	}
	
	@Override
	public boolean matches(String key) {
		return this.key.equalsIgnoreCase(key);
	}
	
	/**
	 * 将指定的键解析成BizStatus对象
	 * @param key
	 * @return
	 */
	public static BizStatus resolve(String key) {
		return (key != null ? mappings.get(key.toLowerCase()) : null);
	}
			
}
