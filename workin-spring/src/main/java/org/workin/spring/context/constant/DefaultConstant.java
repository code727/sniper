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

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.MapUtils;
import org.workin.support.puter.ErrorDulicateKeyPuter;
import org.workin.support.puter.Puter;

/**
 * @description 默认的全局应用常量配置实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultConstant implements Constant, InitializingBean {
	
	private Map<Object, Object> constantMap;
	
	private Puter puter;
			
	public Map<Object, Object> getConstantMap() {
		return constantMap;
	}

	public void setConstantMap(Map<Object, Object> constantMap) {
		this.constantMap = constantMap;
	}
	
	public Puter getPuter() {
		return puter;
	}

	public void setPuter(Puter puter) {
		this.puter = puter;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.constantMap == null)
			this.constantMap = MapUtils.newConcurrentHashMap();
		
		if (this.puter == null)
			this.puter = new ErrorDulicateKeyPuter();
	}

	@Override
	public void put(Object key, Object value) {
		AssertUtils.assertNotNull(key, "Constant key can not be null.");
		if (this.constantMap == null) {
			this.constantMap = MapUtils.newConcurrentHashMap();
			this.constantMap.put(key, value);
		} else 
			this.puter.put(this.constantMap, key, value);
	}

	@Override
	public Object get(Object key) {
		return this.get(key, Object.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(Object key, Class<V> clazz) {
		AssertUtils.assertNotNull(clazz, "Constant key can not be null.");
		return (V) this.constantMap.get(key);
	}

}
