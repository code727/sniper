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
 * Create Date : 2015-1-12
 */

package org.workin.commons.exception;

import org.workin.commons.util.StringUtils;

/**
 * 业务异常处理类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BizException extends WorkinException {
	
	private static final long serialVersionUID = -7058119064602809131L;
	
	/** 业务/模块码 */
	private String bizCode;

	public BizException() {
		super();
	}
	
	public BizException(String messgae) {
		super(messgae);
	}
	
	public BizException(Throwable throwable) {
		super(throwable);
	}
	
	public BizException(String code, String message) {
		super(code, message);
	}
	
	public BizException(String messgae, Throwable throwable) {
		super(messgae, throwable);
	}
	
	public BizException(String code, String messgae, Throwable throwable) {
		super(code, messgae, throwable);
	}

	public String getBizCode() {
		return bizCode;
	}

	public BizException setBizCode(String bizCode) {
		this.bizCode = bizCode;
		return this;
	}
	
	@Override
	protected void append(StringBuilder result) {
		String code = getCode();
		String message = getLocalizedMessage();
		boolean codeIsNotBlank = StringUtils.isNotBlank(code);
		boolean bizCodeIsNotBlank = StringUtils.isNotBlank(bizCode);
		boolean messageIsNotBlank = StringUtils.isNotBlank(message);
		
		if (codeIsNotBlank || bizCodeIsNotBlank || messageIsNotBlank) {
			result.append(":{");
			
			StringBuilder builder = new StringBuilder();
			if (codeIsNotBlank) 
				builder.append("code:").append(code);
			
			if (bizCodeIsNotBlank) {
				if (builder.length() > 0)
					builder.append(",");
				
				builder.append("bizCode:").append(bizCode);
			}
			
			if (messageIsNotBlank) {
				if (builder.length() > 0)
					builder.append(",");
				
				builder.append("message:").append(message);
			}
			
			result.append(builder).append("}");
		}
	}
	
}
