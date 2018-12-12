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
 * Create Date : 2015-7-22
 */

package org.sniper.test.domain;

import java.io.Serializable;

import org.sniper.test.annotation.Testable;

@Testable
public abstract class Person implements Serializable {
	
	private static final long serialVersionUID = 4679194880575434699L;
	
	@Testable
	protected String name;
	
	@Deprecated
	protected String userName = "tester";
	
	public Person() {}
	
	protected Person(String name) {
		this.name = name;
	}

	@Testable
	public String getName() {
		return name;
	}

	@Testable
	public void setName(String name) {
		this.name = name;
	}

	@Deprecated
	public String getUserName() {
		return userName;
	}

	@Deprecated
	public void setUserName(String userName) {
		this.userName = userName;
	}
		
}
