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
 * Create Date : 2016年8月25日
 */

package org.sniper.spring.context.test;

import java.util.Map;

import org.junit.Test;
import org.sniper.beans.parameter.handler.CoverageDulicateKeyHandler;
import org.sniper.commons.util.MapUtils;
import org.sniper.spring.context.ApplicationContextParameters;
import org.sniper.spring.context.ParametersService;
import org.sniper.test.junit.BaseTestCase;

/**
 * @description
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ApplicationContextParameterTest extends BaseTestCase {
	
	@Test
	public void test() throws Exception {
		
		ApplicationContextParameters<String, Object> parameter = new ApplicationContextParameters<String, Object>();
		parameter.setKeyHandler(new CoverageDulicateKeyHandler());
		
		/* 设置初始参数列表 */
		Map<String, Object> parameterItems = MapUtils.newHashMap();
		parameterItems.put("name", "dubin");
		parameterItems.put("age", "33");
		parameter.setItems(parameterItems);
		
		/* 预加载其余参数，例如从数据库中获取系统全局的配置参数 */
		parameter.setParameterService(new ParametersService() {
			
			@SuppressWarnings("unchecked")
			@Override
			public <K, V> Map<K, V> preloading() {
				Map<String, Object> parameters = MapUtils.newHashMap();
				parameters.put("name", "sniper");
				parameters.put("age", "30");
				return (Map<K, V>) parameters;
			}
		});
		
		parameter.afterPropertiesSet();
		System.out.println(parameter.getValue("name"));
		
	}

}
