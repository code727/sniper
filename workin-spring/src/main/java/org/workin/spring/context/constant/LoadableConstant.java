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
 * Create Date : 2015-7-1
 */

package org.workin.spring.context.constant;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.workin.commons.util.MapUtils;

/**
 * @description 可加载的应用常量配置实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class LoadableConstant extends DefaultConstant
		implements ConstantLoader {
	
	private static Logger logger = LoggerFactory.getLogger(LoadableConstant.class);
	
	@Autowired
	private ConstantLoaderService loaderService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		logger.info("Enable application constant preloading service.");
		Map<Object, Object> loaded = loaderService.preloading();
		if (MapUtils.isNotEmpty(loaded)) {
			Iterator<Entry<Object, Object>> iterator = loaded.entrySet().iterator();
			if (iterator.hasNext()) {
				Entry<Object, Object> item = iterator.next();
				super.put(item.getKey(), item.getValue());
			}
		}
		
	}
	
	@Override
	public void load() {
		Map<Object, Object> constantMap = getConstantMap();
		logger.info("Enable application constant incremental loading service.");
		loaderService.incrementalLoading(constantMap);
	}

}
