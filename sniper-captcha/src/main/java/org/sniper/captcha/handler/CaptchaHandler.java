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

package org.sniper.captcha.handler;

import java.io.Serializable;

/**
 * 验证码处理器接口
 * @author  Daniele
 * @version 1.0
 */
public interface CaptchaHandler {
	
	/**
	 * 创建验证码
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	public String create(Serializable id);
	
	/**
	 * 更新验证码
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	public String update(Serializable id);
	
	/**
	 * 获取验证码
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	public String get(Serializable id);
	
	/**
	 * 删除验证码
	 * @author Daniele 
	 * @param id
	 * @return
	 */
	public String detele(Serializable id);
	
	/**
	 * 是否按忽略大小写的方式进行验证
	 * @author Daniele 
	 * @return
	 */
	public boolean isIgnoreCaseValidation();

	/**
	 * 设置是否按忽略大小写的方式进行验证
	 * @author Daniele 
	 * @param ignoreCaseValidation
	 */
	public void setIgnoreCaseValidation(boolean ignoreCaseValidation);
	
	/**
	 * 校验验证码
	 * @author Daniele 
	 * @param id
	 * @param captcha
	 * @return
	 */
	public boolean validation(Serializable id, String captcha);

	/**
	 * 当验证失败时是否删除已保存的验证码
	 * @author Daniele 
	 * @return
	 */
	public boolean isDeleteWhenValidationFailed();

	/**
	 * 设置当验证失败时是否删除已保存的验证码
	 * @author Daniele 
	 * @param deleteWhenValidationFailed
	 */
	public void setDeleteWhenValidationFailed(boolean deleteWhenValidationFailed);
	
}
