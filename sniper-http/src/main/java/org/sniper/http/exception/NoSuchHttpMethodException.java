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

/**
 * "不匹配的HTTP方法"强制异常类
 * @author  Daniele
 * @version 1.0
 */
public class NoSuchHttpMethodException extends NoSuchMethodException {

	private static final long serialVersionUID = -4638747583528720969L;
	
	public NoSuchHttpMethodException() {
		super();
	}
	
	public NoSuchHttpMethodException(String messgae) {
		super(messgae);
	}
	
}
