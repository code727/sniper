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
 * Create Date : 2016-1-5
 */

package org.sniper.security.shiro.generator;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.sniper.generator.application.UUIDGenerator;

/**
 * UUID格式的SessionID生成器实现类
 * @author  Daniele
 * @version 1.0
 */
public class UuidSessionIdGenerator extends AbstractShiroSessionIdGenerator {
	
	private UUIDGenerator generator;
	
	public UuidSessionIdGenerator() {
		this.generator = new UUIDGenerator();
	}
	
	public boolean isUnsigned() {
		return this.generator.isUnsigned();
	}

	public void setUnsigned(boolean unsigned) {
		this.generator.setUnsigned(unsigned);
	}

	public boolean isUpperCase() {
		return this.generator.isUpperCase();
	}

	public void setUpperCase(boolean upperCase) {
		this.generator.setUpperCase(upperCase);
	}

	@Override
	public Serializable generateId(Session session) {
		String sessionId = generator.generate();
		return sessionId;
	}
			
}
