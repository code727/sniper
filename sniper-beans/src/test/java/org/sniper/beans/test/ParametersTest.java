/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-11-2
 */

package org.sniper.beans.test;

import org.junit.Before;
import org.junit.Test;
import org.sniper.beans.parameter.DefaultParameters;
import org.sniper.beans.parameter.Parameters;
import org.sniper.test.junit.BaseTestCase;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ParametersTest extends BaseTestCase {
	
	private Parameters<String, Object> parameters;
	
	@Before
	public void init() {
		DefaultParameters<String, Object> defaultParameters = new DefaultParameters<String, Object>();
		defaultParameters.add("name", "dubin");
		defaultParameters.add("age", 34);
		defaultParameters.add("birthday", "1983-07-27 01:37:00");
		defaultParameters.add("vip", true);
		this.parameters = defaultParameters;
	}
	
	@Test
	public void test() {
		System.out.println(this.parameters.getString("name"));
		System.out.println(this.parameters.getLong("age"));
		System.out.println(this.parameters.getDate("birthday"));
		System.out.println(this.parameters.getBoolean("vip"));
	}

}
