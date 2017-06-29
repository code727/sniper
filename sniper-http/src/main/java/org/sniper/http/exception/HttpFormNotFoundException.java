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
 * Create Date : 2015-7-9
 */

package org.sniper.http.exception;

import org.sniper.commons.exception.SniperException;

/**
 * "表单未找到"运行时异常
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HttpFormNotFoundException extends SniperException {

	private static final long serialVersionUID = -2895027098437321733L;
	
	public HttpFormNotFoundException() {
		super();
	}
	
	public HttpFormNotFoundException(String messgae) {
		super(messgae);
	}
	
	public HttpFormNotFoundException(Throwable throwable) {
		super(throwable);
	}
	
	public HttpFormNotFoundException(String message, Throwable throwable) {
		super(message,throwable);
	}

}
