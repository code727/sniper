/*
 * Copyright 2016 the original author or authors.
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
 * Create Date : 2016-3-1
 */

package org.workin.captcha.handler;

import java.io.Serializable;

/**
 * @description 验证码处理器接口
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public interface CaptchaHandler {
	
	/**
	 * @description 创建验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public String create(Serializable id);
	
	/**
	 * @description 更新验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public String update(Serializable id);
	
	/**
	 * @description 获取验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public String get(Serializable id);
	
	/**
	 * @description 删除验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @return
	 */
	public String detele(Serializable id);
	
	/**
	 * @description 是否按忽略大小写的方式进行验证
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isIgnoreCaseValidation();

	/**
	 * @description 设置是否按忽略大小写的方式进行验证
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param ignoreCaseValidation
	 */
	public void setIgnoreCaseValidation(boolean ignoreCaseValidation);
	
	/**
	 * @description 校验验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param captcha
	 * @return
	 */
	public boolean validation(Serializable id, String captcha);

	/**
	 * @description 当验证失败时是否删除已保存的验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @return
	 */
	public boolean isDeleteWhenValidationFailed();

	/**
	 * @description 设置当验证失败时是否删除已保存的验证码
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param deleteWhenValidationFailed
	 */
	public void setDeleteWhenValidationFailed(boolean deleteWhenValidationFailed);
	
}
