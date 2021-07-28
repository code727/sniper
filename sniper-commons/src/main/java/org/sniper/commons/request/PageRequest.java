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

package org.sniper.commons.request;

import java.util.Iterator;

import org.sniper.commons.util.CollectionUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 分页查询请求
 * @author  Daniele
 * @version 1.0
 */
public class PageRequest extends MultiSortRequest implements PageQuery {
	
	private static final long serialVersionUID = -3075094595070319133L;

	/** 每页条数 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	/** 当前页数 */
	private int currentPage = DEFAULT_CURRENT_PAGE;
	
	/** 开始查询的位置 */
	private long start = DEFAULT_START_POS;
	
	/** 结束查询的位置 */
	private long end = DEFAULT_START_POS;
	
	/** 是否附带查询总数 */
	private boolean attachQueryCount = true;
	
	/** 是否开启偏移量查询 */
	private boolean enableOffsetQuery;
	
	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		// 每页最少一条
		this.pageSize = NumberUtils.minLimit(pageSize, 1);
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	@Override
	public void setCurrentPage(int currentPage) {
		// 当前页数最少为1
		this.currentPage = NumberUtils.minLimit(currentPage, 1);
	}
	
	@Override
	public void setStart(long start) {
		this.start = start;
	}

	@Override
	public long getStart() {
		// 开启偏移量查询后，直接将start作为移量起始点返回
		if (isEnableOffsetQuery()) {
			return start;
		}

		// 根据当前页数和每页条数计算后返回
		return (this.currentPage -1) * this.pageSize;
	}
	
	@Override
	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public long getEnd() {
		// 开启偏移量查询后，直接将end作为移量结束点返回
		if (isEnableOffsetQuery()) {
			return end;
		}
			
		// 根据起始位置和每页条数计算后返回
		return this.getStart() + this.pageSize;
	}			

	@Override
	public boolean isAttachQueryCount() {
		return attachQueryCount;
	}

	@Override
	public void setAttachQueryCount(boolean attachQueryCount) {
		this.attachQueryCount = attachQueryCount;
	}
	
	/**
	 * 判断是否开启偏移量查询
	 * @author Daniele
	 * @return
	 */
	public boolean isEnableOffsetQuery() {
		return enableOffsetQuery;
	}

	/**
	 * 设置是否开启偏移量查询
	 * @author Daniele
	 * @param enableOffsetQuery
	 */
	public void setEnableOffsetQuery(boolean enableOffsetQuery) {
		this.enableOffsetQuery = enableOffsetQuery;
	}
	
	@Override
	public String toString() {
		return String.format("{\"currentPage\":%d,\"pageSize\":%d,\"start\":%d,\"end\":%d%s}", 
				currentPage, pageSize, getStart(), getEnd(), buildSortsMessage());
	}
	
	/**
	 * 构建排序请求集消息
	 * @author Daniele
	 * @return
	 */
	private String buildSortsMessage() {
		if (CollectionUtils.isNotEmpty(getSorts())) {
			StringBuilder builder = new StringBuilder(", ORDER BY ");
			Iterator<SortRequest> sortRequests = getSorts().iterator();
			while(sortRequests.hasNext()) {
				SortRequest sortRequest = sortRequests.next();
				builder.append(sortRequest.getProperty()).append(" ").append(sortRequest.getOrder());
				if (sortRequests.hasNext())
					builder.append(", ");
			}
			return builder.toString();
		}
		
		return StringUtils.EMPTY;
	}

}
