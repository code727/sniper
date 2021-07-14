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
 * Create Date : 2017-12-26
 */

package org.sniper.commons.exception;

/**
 * 控制器层异常
 * @author  Daniele
 * @version 1.0
 */
public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = -3379279724023769167L;
	
	public ControllerException() {
		super();
	}
	
	public ControllerException(String messgae) {
		super(messgae);
	}
	
	public ControllerException(Throwable throwable) {
		super(throwable);
	}
		
	public ControllerException(String messgae, Throwable throwable) {
		super(messgae, throwable);
	}

}
