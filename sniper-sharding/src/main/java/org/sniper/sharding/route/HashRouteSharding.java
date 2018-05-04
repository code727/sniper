/*
 * Copyright 2018 the original author or authors.
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
 * Create Date : 2018-5-4
 */

package org.sniper.sharding.route;

import java.math.BigInteger;

import org.sniper.commons.util.NumberUtils;
import org.sniper.sharding.AbstractHashSharding;

/**
 * 哈希路由分片器实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class HashRouteSharding extends AbstractHashSharding<Route> {

	@Override
	protected Route buildShardingEntity() {
		return new Route();
	}

	@Override
	protected <P> void doSharding(P parameter, Route route) {
		String target = "";
		if (parameter == null)
			target = "0";
		else {
			BigInteger integer;
			try {
				integer = new BigInteger(parameter.toString()).abs();
			} catch (NumberFormatException e) {
				// 如果是小数或其他非数字型对象，则直接利用该对象的哈希绝对值取模
				integer = new BigInteger(String.valueOf(parameter.hashCode())).abs();
			}
			
			target = integer.mod(model).toString();
		}
		
		if (formatLength > 0)
			route.setTarget(NumberUtils.format(new BigInteger(target), formatLength));
		else
			route.setTarget(target);
	}

}
