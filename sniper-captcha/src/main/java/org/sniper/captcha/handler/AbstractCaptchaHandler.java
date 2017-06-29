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

import org.springframework.beans.factory.InitializingBean;
import org.sniper.captcha.generator.CaptchaGenerator;
import org.sniper.commons.util.StringUtils;

/**
 * 验证码处理器抽象类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public abstract class AbstractCaptchaHandler implements CaptchaHandler, InitializingBean {
	
	/** 验证码生成器 */
	private CaptchaGenerator captchaGenerator;
	
	/** 是否按忽略大小写的方式进行验证 */
	private boolean ignoreCaseValidation;
	
	/** 当验证失败时是否删除已保存的验证码 */
	private boolean deleteWhenValidationFailed;
	
	public AbstractCaptchaHandler() {
		setIgnoreCaseValidation(true);
		setDeleteWhenValidationFailed(true);
	}
	
	public CaptchaGenerator getCaptchaGenerator() {
		return captchaGenerator;
	}

	public void setCaptchaGenerator(CaptchaGenerator captchaGenerator) {
		this.captchaGenerator = captchaGenerator;
	}
	
	public boolean isIgnoreCaseValidation() {
		return ignoreCaseValidation;
	}

	public void setIgnoreCaseValidation(boolean ignoreCaseValidation) {
		this.ignoreCaseValidation = ignoreCaseValidation;
	}

	public boolean isDeleteWhenValidationFailed() {
		return deleteWhenValidationFailed;
	}

	public void setDeleteWhenValidationFailed(boolean deleteWhenValidationFailed) {
		this.deleteWhenValidationFailed = deleteWhenValidationFailed;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.captchaGenerator == null)
			throw new IllegalArgumentException("Property 'captchaGenerator' is required.");
	}
	
	public String create(Serializable id) {
		String text = this.captchaGenerator.generate();
		doCreate(id, text);
		return text;
	}

	@Override
	public String update(Serializable id) {
		String text = this.captchaGenerator.generate();
		doUpdate(id, text);
		return text;
	}
	
	@Override
	public boolean validation(Serializable id, String captcha) {
		String text = this.get(id);
		boolean success = isIgnoreCaseValidation() ? StringUtils
				.equalsIgnoreCase(text, captcha) : StringUtils.equals(text, captcha);
		
		/* 有如下两种情况会将存储的验证码自动删除:
		 * 1)验证成功;
		 * 2)验证不成功,并且设置了当验证失败时自动删除的条件
		 */
		if (success || isDeleteWhenValidationFailed())
			this.detele(id);
		
		return success;
	}
	
	/**
	 * 执行验证码文本新增操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param text
	 */
	protected abstract void doCreate(Serializable id, String text);
	
	/**
	 * 执行验证码文本更新操作
	 * @author <a href="mailto:code727@gmail.com">杜斌</a> 
	 * @param id
	 * @param text
	 */
	protected abstract void doUpdate(Serializable id, String text);

}
