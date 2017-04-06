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
 * 自定义workin包异常类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WorkinException extends RuntimeException {
	
	private static final long serialVersionUID = -3747309828506142444L;

	/** 异常码 */
	private String code;

	public WorkinException() {
		super();
	}
	
	public WorkinException(String messgae) {
		super(messgae);
	}
	
	public WorkinException(Throwable throwable) {
		super(throwable);
	}
	
	public WorkinException(String code, String message) {
		this(message);
		this.code = code;
	}
	
	public WorkinException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public WorkinException(String code, String message, Throwable throwable) {
		this(message, throwable);
		this.code = code;
	}
	
	public WorkinException setCode(String code) {
		this.code = code;
		return this;
	}

	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(getClass().getName());
		append(result);
		return result.toString();
	}
	
	/**
	 * 追加Exception的显示结果
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param result
	 */
	protected void append(StringBuilder result) {
		String message = getLocalizedMessage();
		
		boolean codeIsNotBlank = StringUtils.isNotBlank(code);
		boolean messageIsNotBlank = StringUtils.isNotBlank(message);
		if (codeIsNotBlank || messageIsNotBlank) {
			result.append(":{");
			
			StringBuilder builder = new StringBuilder();
			if (codeIsNotBlank) 
				builder.append("code:").append(code);
			
			if (messageIsNotBlank) {
				if (builder.length() > 0)
					builder.append(",");
				
				builder.append("message:").append(message);
			}
				
			result.append(builder).append("}");
		}
	}
		
}
