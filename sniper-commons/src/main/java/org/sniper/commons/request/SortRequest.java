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

package org.sniper.commons.request;

import java.io.Serializable;

import org.sniper.commons.enums.logic.OrderEnum;
import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.ObjectUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 排序请求
 * @author  Daniele
 * @version 1.0
 */
public class SortRequest implements Serializable {
	
	private static final long serialVersionUID = -3569581291289010870L;
	
	private static final OrderEnum DEFAULT_MODE = OrderEnum.ASC;
	
	/** 需排序的属性名称 */
	private String property;

	/** 排序模式 */
	private OrderEnum order = DEFAULT_MODE;
	
	/**
	 * 默认构造函数保持为空，目的是让JSON反序列化时能找到此构造函数
	 * @author Daniele
	 */
	SortRequest() {}
	
	public SortRequest(String property, OrderEnum order) {
		setProperty(property);
		setOrder(order);
	}
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		AssertUtils.assertNotBlank(property, "Sort property must not be null or blank");
		this.property = property;
	}
	
	public OrderEnum getOrder() {
		return order;
	}

	public void setOrder(OrderEnum order) {
		this.order = (order != null ? order : DEFAULT_MODE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj instanceof SortRequest) 
			return StringUtils.equals(this.property, ((SortRequest) obj).getProperty());
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.hashCode(this.property);
	}
	
	@Override
	public String toString() {
		return String.format("{\"property\":\"%s\",\"order\":\"%s\"}", property, order);
	}
	
}
