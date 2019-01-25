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
 * Create Date : 2019-1-16
 */

package org.sniper.nosql.redis.enums;

/**
 * 有序集合聚合方式枚举
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public enum ZStoreAggregate {
	
	/** 将所有集合中某个成员的 score值之和作为结果集中该成员的score值(默认方式) */
	SUM, 
	/** 将所有集合中某个成员的最小score值作为结果集中该成员的score值 */
	MIN, 
	/** 将所有集合中某个成员的最大score值作为结果集中该成员的score值 */
	MAX;

}
