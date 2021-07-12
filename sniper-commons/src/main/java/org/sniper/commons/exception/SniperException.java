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

package org.sniper.commons.exception;

/**
 * 自定义Sniper包异常类
 * @author  Daniele
 * @version 1.0
 */
public class SniperException extends RuntimeException {
	
	private static final long serialVersionUID = -3747309828506142444L;

	public SniperException() {
		super();
	}
	
	public SniperException(String messgae) {
		super(messgae);
	}
	
	public SniperException(Throwable throwable) {
		super(throwable);
	}
	
	public SniperException(String message, Throwable throwable) {
		super(message, throwable);
	}
			
}
