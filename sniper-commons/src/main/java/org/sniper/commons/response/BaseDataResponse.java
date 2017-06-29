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

/**
 * 基本的数据响应实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class BaseDataResponse<T> extends AbstractDataResponse<T> {
	
	public BaseDataResponse() {
		super();
	}
		
	public BaseDataResponse(T data) {
		super(data);
	}
	
	public BaseDataResponse(DataResponse<T> response) {
		super(response);
	}
		
	public BaseDataResponse(String code, T data) {
		super(code, data);
	}
	
	/**
	 * 创建具备默认成功状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> success(T data) {
		return success(DEFAULT_SUCCESS_STATUS, data);
	}
	
	/**
	 * 创建具备自定义成功状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> success(String code, T data) {
		return new BaseDataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认失败状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> failed(T data) {
		return failed(DEFAULT_FAILED_STATUS, data);
	}
	
	/**
	 * 创建具备自定义失败状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> failed(String code, T data) {
		return new BaseDataResponse<T>(code, data);
	}
	
	/**
	 * 创建具备默认异常状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> exception(T data) {
		return exception(DEFAULT_EXCEPTION_STATUS, data);
	}
	
	/**
	 * 创建具备自定义异常状态码的数据响应对象
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param code
	 * @param data
	 * @return
	 */
	public static <T> BaseDataResponse<T> exception(String code, T data) {
		return new BaseDataResponse<T>(code, data);
	}
	
}
