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
 * Create Date : 2015-2-11
 */

package org.workin.persistence.util;

import org.workin.commons.util.ArrayUtils;
import org.workin.commons.util.AssertUtils;
import org.workin.commons.util.ObjectUtils;
import org.workin.commons.util.StringUtils;

/**
 * @description 持久化实体属性过滤器
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class PropertyFilter implements PersistencePropertyFilter {
	
	/** 属性名 */
	private String name;
	
	/** 属性值 */
	private Object value;
	
	/** 属性名-值之间的运算符 */
	private Operator operator;
	
	/** 当前属性与下一个属性间的关系谓词，默认为AND */
	private Predicate predicate = Predicate.AND;
	
	public PropertyFilter(String name, Object value, String operator) {
		this.setOperator(operator);
		this.name = name;
		this.value = value;
	}
	
	public PropertyFilter(String name, Object value, Operator operator) {
		this.operator = operator;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}
		
	@Override
	public Operator getOperator() {
		return this.operator;
	}
	
	@Override
	public void setOperator(String operator) {
		AssertUtils.assertTrue(StringUtils.isNotBlank(operator), "Property operator can not be null or blank.");
		Operator operatorEnum = Operator.get(operator);
		if (operatorEnum != null)
			this.setOperator(operatorEnum);
		else
			throw new IllegalArgumentException("Unknow operator \"" + operator + 
					"\", support operator types [" + Operator.ALL_OPERATOR_NAME + "].");
	}
	
	@Override
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	@Override
	public Predicate getPredicate() {
		return predicate;
	}
	
	@Override
	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}
	
	public void setPredicate(String predicate) {
		try {
			predicate = predicate.trim();
			this.predicate = Enum.valueOf(Predicate.class, predicate.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknow predicate \"" + predicate + 
					"\", support predicate types [" + ArrayUtils.join(Predicate.values(), ",") + "].");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.name);
		if (StringUtils.containsIgnoreCase(this.operator.getKey(), Operator.LIKE.getKey())) 
			builder.append(this.operator.getValue().replace("{value}",
					ObjectUtils.toSafeString(this.value)));
		else
			builder.append(this.operator.getValue()).append(this.value);
		return builder.toString();
	}
	
}
