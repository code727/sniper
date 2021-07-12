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
 * Create Date : 2015-5-13
 */

package org.sniper.persistence.datasource.selector;

import org.sniper.commons.util.NumberUtils;

/**
 * 多数据源随机选择器实现类
 * @author  Daniele
 * @version 1.0
 */
public class MultipleDataSourceRandomSelector implements MultipleDataSourceSelector {

	@Override
	public Object select(Object[] sources) {
		return sources.length != 1 ? sources[NumberUtils.randomIn(sources.length)] : sources[0];
	}
	
}
