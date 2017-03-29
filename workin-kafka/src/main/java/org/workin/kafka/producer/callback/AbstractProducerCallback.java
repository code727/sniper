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
 * Create Date : 2017-3-29
 */

package org.workin.kafka.producer.callback;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.workin.kafka.exception.ProducerException;

/**
 * 生产者回调抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractProducerCallback implements ProducerCallback {
	
	private boolean interestedInSuccess;
	
	private boolean throwExceptionOnError;
	
	@Override
	public boolean isInterestedInSuccess() {
		return interestedInSuccess;
	}

	@Override
	public void setInterestedInSuccess(boolean interestedInSuccess) {
		this.interestedInSuccess = interestedInSuccess;
	}

	@Override
	public boolean isThrowExceptionOnError() {
		return throwExceptionOnError;
	}

	@Override
	public void setThrowExceptionOnError(boolean throwExceptionOnError) {
		this.interestedInSuccess = throwExceptionOnError;
	}
	
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if (exception != null) {
			if (interestedInSuccess)
				onSuccess(metadata);
		} else {
			if (throwExceptionOnError)
				throw new ProducerException("");
			
			onFailure(exception);
		}
	}

	protected void onSuccess(RecordMetadata metadata) {
		
	}
	
	protected void onFailure(Exception exception) {
		
	}
	
	
	
	

}
