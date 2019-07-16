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
 * Create Date : 2015-12-29
 */

package org.sniper.captcha.repository;

import org.sniper.commons.util.StringUtils;

/**
 * 混编文本库实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MixedRepository extends TextRepository {
	
	private static final String[] deletedString = new String[] { "0", "o", "O", "1", "l" };
	
	public MixedRepository() {
		String content = new NumberRepository().getContent() + new LetterRepository().getContent();
		for (String ds : deletedString) {
			content = StringUtils.delete(content, ds);
		}
		
		setContent(content);
	}
		
}
