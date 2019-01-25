/*
 * Copyright 2019 the original author or authors.
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
 * Create Date : 2019-1-25
 */

package org.sniper.nosql.redis.model;

import org.sniper.commons.util.AssertUtils;
import org.sniper.commons.util.NumberUtils;
import org.sniper.commons.util.StringUtils;

/**
 * 有序集合元组默认实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class DefaultZSetTuple<V> implements ZSetTuple<V> {

	private final Double score;
	private final V member;
	
	public DefaultZSetTuple(V member) {
		this(null, member);
	}
	
	public DefaultZSetTuple(Double score, V member) {
		AssertUtils.assertNotNull(member, "Member value must not be null");
		this.score = NumberUtils.safeDouble(score);
		this.member = member;
	}
	
	@Override
	public Double getScore() {
		return score;
	}

	@Override
	public V getMember() {
		return member;
	}
	
	@Override
	public int compareTo(Double o) {
		return score.compareTo(NumberUtils.safeDouble(o));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (this == obj)
			return true;
		
		if (obj instanceof DefaultZSetTuple) {
			DefaultZSetTuple<V> other = (DefaultZSetTuple<V>) obj;
			return member.equals(other.member) && score.equals(other.score);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		hashCode = 31 * hashCode + score.hashCode();
		hashCode = 31 * hashCode + member.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		return score + StringUtils.SPACE + member;
	}
		
}
