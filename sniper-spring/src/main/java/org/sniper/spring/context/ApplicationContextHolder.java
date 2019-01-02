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
 * Create Date : 2015-12-21
 */

package org.sniper.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.sniper.spring.beans.CheckableInitializingBeanAdapter;

public class ApplicationContextHolder extends CheckableInitializingBeanAdapter
		implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
	}
	
	@Override
	protected void checkProperties() {
		if (applicationContext == null)
			throw new IllegalArgumentException("Property 'applicationContext' is required");
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) applicationContext.getBeansOfType(clazz);
	}
	
	public static void start() {
		if (applicationContext instanceof Lifecycle)
			((Lifecycle) applicationContext).start();
	}
	
	public static void stop() {
		if (applicationContext instanceof Lifecycle)
			((Lifecycle) applicationContext).stop();
	}

}
