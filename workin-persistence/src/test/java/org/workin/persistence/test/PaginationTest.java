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
 * Create Date : 2015-1-14
 */

package org.workin.persistence.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.workin.commons.pagination.impl.DetailPager;
import org.workin.commons.pagination.impl.SimplePager;
import org.workin.commons.util.CollectionUtils;
import org.workin.test.junit.BaseTestCase;

/**
 * @description 分页查询类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PaginationTest extends BaseTestCase {
	
	/**
	 * @description 测试根据当前页数(currentPage)和每页条数(pageSize)的常规分页
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testGeneralPagination() {
		SimplePager pager = new SimplePager();
		
		assertEquals(pager.getPageSize(), 10);
		pager.setCurrentPage(1);
		assertEquals(pager.getBegin(), 0);
		assertEquals(pager.getEnd(), 10);
		
		pager.setCurrentPage(2);
		assertEquals(pager.getBegin(), 10);
		assertEquals(pager.getEnd(), 20);
		
	}
	
	/**
	 * @description 根据根据ID(begin)和每页条数(pageSize)的半偏移分页。
	 * 				针对全偏移(begin和end参数作为查询条件)分页方式同样适用。
	 * 				对于偏移分页，不要设置当前页数，否则分页器仍将用常规分页的方式来计算参数。
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
	@Test
	public void testHalfOffsetPagination() {
		SimplePager pager = new SimplePager();
		
		int startId = 1;
		int endId = startId + pager.getPageSize();
		pager.setBegin(startId);
		assertEquals(pager.getPageSize(), 10);
		assertEquals(pager.getBegin(), startId);
		assertEquals(pager.getEnd(), endId);
	}
	
	@Test
	public void testWorkinDetailPager() {
		List<String> data = CollectionUtils.newArrayList(); 
		for (int i = 1; i <= 100; i++)
			data.add("" + i);
		
		DetailPager<String> pager = new DetailPager<String>();
		pager.setData(data);
		assertEquals(pager.getData().size(), 100);
		assertEquals(pager.getTotal(), 100);
		pager.setTotal(99);
		assertEquals(pager.getTotal(), 100);
		pager.setTotal(111);
		assertEquals(pager.getTotal(), 111);
		
		pager = new DetailPager<String>();
		pager.setData(data);
		assertEquals(pager.getFrom(), 1);
		assertEquals(pager.getTo(), 100);
		
		pager.setData(null);
		assertEquals(pager.getFrom(), 0);
		assertEquals(pager.getTo(), 0);
		
		ArrayList<String> list = CollectionUtils.newArrayList();
		list.add("1");
		pager.setData(list);
		assertEquals(pager.getFrom(), 1);
		assertEquals(pager.getTo(), 1);
		
		list.add("2");
		pager.setData(list);
		assertEquals(pager.getFrom(), 1);
		assertEquals(pager.getTo(), 2);
		
	}
	
}
