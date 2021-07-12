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
 * Create Date : 2017-3-10
 */

package org.sniper.kafka.exception;

/**
 * 生产者运行时异常类
 * @author  Daniele
 * @version 1.0
 */
public class ProducerException extends RuntimeException {

	private static final long serialVersionUID = 8685615532352086785L;
	
	public ProducerException() {
		super();
	}
	
	public ProducerException(String messgae) {
		super(messgae);
	}
	
	public ProducerException(Throwable throwable) {
		super(throwable);
	}
	
	public ProducerException(String message, Throwable throwable) {
		super(message,throwable);
	}

}
