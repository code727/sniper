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

package org.sniper.commons.test.pagination;

import org.junit.Test;
import org.sniper.commons.pagination.pager.JQueryEasyUIPager;
import org.sniper.commons.pagination.pager.SimplePager;
import org.sniper.test.junit.BaseTestCase;

/**
 * 分页查询单元测试类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PaginationTest extends BaseTestCase {
	
//	@Test
	public void testSimplePager() {
		SimplePager pager = new SimplePager();
		/* 进行常规分页时，传入currentPage和pageSize，再根据begin和pageSize来分页 */
//		pager.setCurrentPage(2);
//		pager.setPageSize(10);
		System.out.println("begin:" + pager.getBegin());
		System.out.println("end:" + pager.getEnd());
		System.out.println("pageSize" + pager.getPageSize());
		
		/* 进行ID半偏移分页时，传入begin和pageSize，再根据begin和pageSize来分页 */
		pager.setBegin(15);
		pager.setPageSize(20);
		System.out.println("begin:" + pager.getBegin());
		System.out.println("end:" + pager.getEnd());
		System.out.println("pageSize" + pager.getPageSize());
		
		/* 进行ID全偏移分页时，传入begin和end或pageSize，再根据begin和end来分页 */
		pager.setBegin(15);
		pager.setEnd(100);
		System.out.println("begin:" + pager.getBegin());
		System.out.println("end:" + pager.getEnd());
		
	}
	
	@Test
	public void testJQueryEasyUIPager() {
		JQueryEasyUIPager pager = new JQueryEasyUIPager();
		/* 进行JQueryEasyUI分页时，传入page(当前页数)和rows(每页条数)，再根据begin和pageSize来分页 */
		pager.setPage(1);
		pager.setRows(20);
		
		System.out.println("page:" + pager.getPage());
		System.out.println("currentPage:" + pager.getCurrentPage());
		
		System.out.println("rows:" + pager.getRows());
		System.out.println("pageSize" + pager.getPageSize());
		
		System.out.println("begin:" + pager.getBegin());
		System.out.println("end:" + pager.getEnd());
	}

}
