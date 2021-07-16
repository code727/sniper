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

package org.sniper.serialization.test;

import org.junit.Test;
import org.sniper.commons.enums.logic.OrderEnum;
import org.sniper.commons.request.PageQuery;
import org.sniper.commons.request.PageRequest;
import org.sniper.commons.request.SortRequest;
import org.sniper.serialization.json.JsonSerializer;
import org.sniper.serialization.json.jackson.fasterxml.FasterxmlJacksonSerializer;
import org.sniper.test.junit.BaseTestCase;


/**
 * 分页查询单元测试类
 * @author  Daniele
 * @version 1.0
 */
public class PageTest extends BaseTestCase {
	
	private JsonSerializer jsonSerializer;
	
	public PageTest() {
//		this.jsonSerializer = new CodehausJacksonSerializer();
		this.jsonSerializer = new FasterxmlJacksonSerializer();
//		this.jsonSerializer = new JsonLibSerializer();
//		this.jsonSerializer = new FastJsonSerializer();
	}
	
//	@Test
	public void testSortRequestSerialize() {
		SortRequest request = new SortRequest("test", OrderEnum.ASC);
		String json = jsonSerializer.serializeToString(request);
		System.out.println(json);
		
		request = jsonSerializer.deserialize(json, SortRequest.class);
		System.out.println(request);
	}
	
	@Test
	public void testPageQuery() {
		PageRequest pageRequest = new PageRequest();
		pageRequest.add(new SortRequest("test1", null));
		pageRequest.add(new SortRequest("test2", OrderEnum.DESC));
//		
//		/* 常规分页传入当前页数(currentPage)和每页条数(pageSize) */
		pageRequest.setCurrentPage(2);
		pageRequest.setPageSize(20);
		System.out.println(pageRequest);
		
		String json = jsonSerializer.serializeToString(pageRequest);
		pageRequest = jsonSerializer.deserialize(json, PageRequest.class);
		
		pageRequest.setCurrentPage(1);
		System.out.println(pageRequest);
//		
//		pageRequest.setCurrentPage(5);
//		System.out.println(pageRequest);
	}
	
//	@Test
	public void testOffsetPageQuery() {
		PageRequest pager = new PageRequest();
		assertSame(pager.getCurrentPage(), PageQuery.DEFAULT_CURRENT_PAGE);
		assertSame(pager.getPageSize(), PageQuery.DEFAULT_PAGE_SIZE);
		
		int pageSize = 20;
		long minId = 9507;
		long maxId = minId + pageSize;
		
		/* 按偏移量分页查询下一页时传入每页条数(pageSize)，并将当前页的最大值(例如ID)作为下一页的起始位置(start)传入 */
		pager.setStart(maxId);
		pager.setPageSize(pageSize);
		
		assertSame(pager.getCurrentPage(), PageQuery.DEFAULT_CURRENT_PAGE);
		assertEquals(pager.getStart(), maxId);
		assertEquals(pager.getPageSize(), pageSize);
		System.out.println(pager);
		
//		/* 按偏移量分页查询上一页时传入每页条数(pageSize)，并将当前页的最小值(例如ID)作为上一页的结束位置(end)传入 */
		pager.setEnd(minId);
		pager.setPageSize(pageSize);
		System.out.println(pager);
	}
	
}
