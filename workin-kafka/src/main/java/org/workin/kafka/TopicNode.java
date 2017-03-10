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
 * Create Date : 2017-3-7
 */

package org.workin.kafka;

import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Topic节点
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TopicNode extends Topic {
	
	/** 当前Topic实例所使用的消息转换器 */
	private MessageConverter messageConverter;
	
	/** 当前Topic实例中注册的生产回调事件 */
	private ListenableFutureCallback<?> futureCallback;
	
	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}
	
	public <T> void setFutureCallback(ListenableFutureCallback<T> futureCallback) {
		this.futureCallback = futureCallback;
	}

	@SuppressWarnings("unchecked")
	public <T> ListenableFutureCallback<T> getFutureCallback() {
		return (ListenableFutureCallback<T>) futureCallback;
	}
	
}
