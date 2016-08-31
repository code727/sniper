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

/**
 * 全局的Workin包强制异常处理类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class WorkinException extends Exception {

	private static final long serialVersionUID = -4509546529648485172L;

	public WorkinException() {
		super();
	}
	
	public WorkinException(String messgae) {
		super(messgae);
	}
	
	public WorkinException(Throwable throwable) {
		super(throwable);
	}
	
	public WorkinException(String message, Throwable throwable) {
		super(message,throwable);
	}
	
}
