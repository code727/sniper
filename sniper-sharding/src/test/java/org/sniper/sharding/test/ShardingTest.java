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
 * Create Date : 2017-4-19
 */

package org.sniper.sharding.test;

import org.junit.Test;
import org.sniper.sharding.HashSharding;
import org.sniper.sharding.route.Route;
import org.sniper.test.junit.BaseTestCase;

/**
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class ShardingTest extends BaseTestCase {
	
	@Test
	public void testHashSharding() {
		HashSharding sharding = new HashSharding();
		sharding.setModel(20);
		sharding.setAllowNull(true);
		sharding.setFormatLength(4);
		
		Route route = new Route("test_table_");
		sharding.sharding("dubin", route);
		System.out.println(route);
	}

}
