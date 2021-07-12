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
 * Create Date : 2017-3-30
 */

package org.sniper.kafka.producer.behavior;

import org.slf4j.Logger;

/**
 * 生产者行为接口
 * @author  Daniele
 * @version 1.0
 */
public interface ProducerBehavior extends ProducerLogger {
	
	/**
	 * 判断是否关注Success事件
	 * @author Daniele 
	 * @return
	 */
	public boolean isInterestedInSuccess();

	/**
	 * 设置是否关注Success事件
	 * @author Daniele 
	 * @param interestedInSuccess
	 */
	public void setInterestedInSuccess(boolean interestedInSuccess);
	
	/**
	 * 判断生产者异常后是否抛出这个异常
	 * @author Daniele 
	 * @return
	 */
	public boolean isThrowExceptionOnError();

	/**
	 * 设置生产者异常后是否抛出这个异常
	 * @author Daniele 
	 * @param throwExceptionOnError
	 */
	public void setThrowExceptionOnError(boolean throwExceptionOnError);
	
	/**
	 * 获取日志
	 * @author Daniele 
	 * @return
	 */
	public Logger getLogger();
	
}
