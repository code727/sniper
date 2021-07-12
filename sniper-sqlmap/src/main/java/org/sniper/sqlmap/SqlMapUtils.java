/*
 * Copyright 2017 the original author or authors.
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
 * Create Date : 2017-3-21
 */

package org.sniper.sqlmap;

import org.sniper.commons.util.ArrayUtils;
import org.sniper.sqlmap.dao.SqlMapDao;
import org.sniper.sqlmap.dao.SqlMapQuery;

/**
 * SqlMap工具类
 * @author  Daniele
 * @version 1.0
 */
public class SqlMapUtils {
	
	/**
	 * 根据指定的类型获取自定义的DAO接口
	 * @author Daniele 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getCustomizeDaoInterface(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		if (ArrayUtils.isNotEmpty(interfaces)) {
			for (Class<?> inte : interfaces) {
				if (inte != SqlMapDao.class && inte != SqlMapQuery.class && inte != SqlMapOperations.class)
					return inte;
			}
		}
		return null;
	}

}
