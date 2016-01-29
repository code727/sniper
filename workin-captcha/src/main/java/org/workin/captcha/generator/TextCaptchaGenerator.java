/*
 * Copyright 2015 the original author or authors.
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
 * Create Date : 2015-12-30
 */

package org.workin.captcha.generator;

import org.workin.captcha.repository.Repository;
import org.workin.captcha.repository.domain.MixedRepository;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.NumberUtils;

/**
 * @description 普通文本验证码生成器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TextCaptchaGenerator implements CaptchaGenerator {
	
	/** 文本库 */
	private Repository repository;
	
	/** 文本内容长度 */
	private int length = TEXT_MIN_LENGTH;
	
	public TextCaptchaGenerator() {
		setRepository(new MixedRepository());
	}

	@Override
	public void setRepository(Repository repository) {
		if (repository != null)
			this.repository = repository;
	}

	@Override
	public Repository getRepository() {
		return this.repository;
	}

	@Override
	public void setLength(int length) {
		AssertUtils.assertTrue(length >= TEXT_MIN_LENGTH, 
				"Captcha text length [" + length + "] must greater than equals " + TEXT_MIN_LENGTH);
		
		this.length = length;
	}

	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public String generate() {
		String content = repository.getContent();
		int contentLength = content.length();
		
		StringBuffer captcha = new StringBuffer();
		for (int i = 0; i < this.length; i++)
			captcha.append(content.charAt(NumberUtils.randomIn(contentLength)));
		
		return captcha.toString();
	}

}
