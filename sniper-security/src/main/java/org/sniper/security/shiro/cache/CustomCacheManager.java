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
 * Create Date : 2015-5-11
 */

package org.sniper.security.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.sniper.commons.util.AssertUtils;

/**
 * 自定义Cache管理实现类
 * @author  <a href="mailto:code727@gmail.com">杜斌</a>
 * @version 1.0
 */
public class CustomCacheManager implements CacheManager, Destroyable {
	
	protected final CacheRepository cacheRepository;
	
	public CustomCacheManager(CacheRepository cacheRepository) {
		AssertUtils.assertNotNull(cacheRepository, "Property 'cacheRepository' is required");
		this.cacheRepository = cacheRepository;
	}
	
	public CacheRepository getCacheRepository() {
		return cacheRepository;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return cacheRepository.getCache(name);
	}
	
	@Override
	public void destroy() throws Exception {
		cacheRepository.destroy();
	}

}
