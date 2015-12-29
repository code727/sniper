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

package org.workin.captcha.repository.domain;

import java.util.Map;

import org.workin.captcha.repository.GroupRepository;
import org.workin.commons.util.MapUtils;

/**
 * @description 混编文本库实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class MixedRepository implements GroupRepository {
	
	/** 字母文本库 */
	private LetterRepository letterRepository;
	
	/** 数字文本库 */
	private NumberRepository numberRepository;
	
	/** 分组库 */
	private Map<String, String> group;
	
	public MixedRepository() {
		this.letterRepository = new LetterRepository();
		this.numberRepository = new NumberRepository();
		
		Map<String, String> group = MapUtils.newHashMap();
		group.put("letter", this.letterRepository.getContent());
		group.put("number", this.numberRepository.getContent());
	}

	@Override
	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	@Override
	public String getContent(String name) {
		return this.group.get(name);
	}
	
}
