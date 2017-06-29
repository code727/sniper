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
 * Create Date : 2015-12-24
 */

package org.sniper.captcha.repository.domain;

import org.sniper.captcha.repository.Repository;

/**
 * 文本库实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class TextRepository implements Repository {
	
	/** 文本内容 */
	private String content;

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return this.content;
	}

}
