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
 * Create Date : 2015-6-9
 */

package org.sniper.nosql.redis.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sniper.context.DataSourceHolder;

/**
 * 多路由RedisConnectionFactory实现类
 * @author  Daniele
 * @version 1.0
 */
public class RedisMultipleConnectionFactory extends AbstractRoutingConnectionFactory {
	
	private static Logger logger = LoggerFactory.getLogger(RedisMultipleConnectionFactory.class);

	@Override
	protected Object determineCurrentLookupKey() {
		Object datasource = DataSourceHolder.getDataSource();
		if (datasource != null) 
			logger.info("Determine current datasource [{}]", datasource);
		
		return datasource;
	}

}
