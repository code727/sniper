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
 * Create Date : 2016-7-28
 */

package org.sniper.commons.test;

import org.junit.Test;
import org.sniper.commons.request.PageableQuery;
import org.sniper.commons.request.PagingRequest;
import org.sniper.test.junit.BaseTestCase;


/**
 * 分页查询单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PaginationTest extends BaseTestCase {
	
	/**
	 * 常规分页查询测试
	 * @author <a href="mailto:code727@gmail.com">杜斌</a>
	 */
//	@Test
	public void testSimplePager() {
		PagingRequest pager = new PagingRequest();
		assertSame(pager.getCurrentPage(), PageableQuery.DEFAULT_CURRENT_PAGE);
		assertSame(pager.getPageSize(), PageableQuery.DEFAULT_PAGE_SIZE);
		
		/* 常规分页传入当前页数(currentPage)和每页条数(pageSize) */
		int currentPage = 2;
		int pageSize = 20;
		pager.setCurrentPage(currentPage);
		pager.setPageSize(pageSize);
		System.out.println(pager);
	}
	
	@Test
	public void testOffsetPager() {
		PagingRequest pager = new PagingRequest();
		assertSame(pager.getCurrentPage(), PageableQuery.DEFAULT_CURRENT_PAGE);
		assertSame(pager.getPageSize(), PageableQuery.DEFAULT_PAGE_SIZE);
		
		int pageSize = 20;
		long minId = 9507;
		long maxId = minId + pageSize;
		
		/* 按偏移量分页查询下一页时传入每页条数(pageSize)，并将当前页的最大值(例如ID)作为下一页的起始位置(start)传入 */
		pager.setStart(maxId);
		pager.setPageSize(pageSize);
		
		assertSame(pager.getCurrentPage(), PageableQuery.DEFAULT_CURRENT_PAGE);
		assertEquals(pager.getStart(), maxId);
		assertEquals(pager.getPageSize(), pageSize);
		System.out.println(pager);
		
//		/* 按偏移量分页查询上一页时传入每页条数(pageSize)，并将当前页的最小值(例如ID)作为上一页的结束位置(end)传入 */
		pager.setEnd(minId);
		pager.setPageSize(pageSize);
		System.out.println(pager);
	}
	
}
