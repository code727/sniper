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
 * Create Date : 2015-2-12
 */

package org.sniper.persistence.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sniper.persistence.util.Operator;
import org.sniper.persistence.util.PersistencePropertyFilter;
import org.sniper.persistence.util.PersistencePropertyFilterChain;
import org.sniper.persistence.util.PersistenceUtils;
import org.sniper.persistence.util.Predicate;
import org.sniper.persistence.util.PropertyFilter;
import org.sniper.persistence.util.PropertyFilterChain;
import org.sniper.test.junit.BaseTestCase;

/**
 * 持久化属性过滤器单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class PersistencePropertyFilterTest extends BaseTestCase {
	
	@Test
	public void testFilterChain() {
		PersistencePropertyFilterChain chain = new PropertyFilterChain();
		chain.add("g1", new PropertyFilter("address", "cd", Operator.LIKE));
		chain.add("g1", new PropertyFilter("name", "sniper", Operator.EQ));
		
		PropertyFilter age = new PropertyFilter("age", 30, Operator.GE);
		age.setPredicate(Predicate.OR);
		chain.add("g2", age);
		chain.add("g2", new PropertyFilter("test", "t", Operator.RLIKE));
		chain.setPredicate("g2", Predicate.OR);
		
		System.out.println(chain.toQueryString());
		System.out.println(PersistenceUtils.buildQueryStringByFilterChain(true, PersistenceUtils.class, chain)); 
		
		List<PersistencePropertyFilter> fileterList = new ArrayList<PersistencePropertyFilter>();
		fileterList.add(age);
		System.out.println(PersistenceUtils.buildQueryStringByFilterList(true, PersistenceUtils.class, fileterList)); 
		
	}

}
