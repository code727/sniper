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
 * Create Date : 2017-3-16
 */

package org.sniper.commons.response;

import org.sniper.commons.enums.status.ResponseStatusEnum;

/**
 * 消息化响应对象接口
 * @author  Daniele
 * @version 1.0
 */
public interface MessagingResponse<C> extends GenericResponse<C> {
	
	/** 默认成功消息 */
	public static final String DEFAULT_SUCCESS_MESSAGE = ResponseStatusEnum.SUCCESS.getMessage();
		
	/** 默认错误消息 */
	public static final String DEFAULT_ERROR_MESSAGE = ResponseStatusEnum.ERROR.getMessage();
	
	/**
	 * 获取响应消息
	 * @author Daniele 
	 * @return
	 */
	public String getMessage();
	
	/**
	 * 设置响应消息
	 * @author Daniele 
	 * @param message
	 */
	public void setMessage(String message);
	
	/**
	 * 格式化响应消息
	 * @author Daniele
	 * @param params 格式化参数
	 * @return
	 */
	public MessagingResponse<C> format(Object... params);
	
}
